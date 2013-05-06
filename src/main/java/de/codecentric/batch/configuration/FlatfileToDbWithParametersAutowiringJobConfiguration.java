package de.codecentric.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;

import de.codecentric.batch.LogItemProcessor;
import de.codecentric.batch.domain.Partner;
import de.codecentric.batch.listener.LogProcessListener;
import de.codecentric.batch.listener.ProtocolListener;

@Configuration
public class FlatfileToDbWithParametersAutowiringJobConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private InfrastructureConfiguration infrastructureConfiguration;
	
	@Bean
	public Job job(Step step){
		return jobBuilderFactory.get("job")
				.listener(protocolListener())
				.start(step)
				.build();
	}
	
	@Bean
	public Step step(ItemReader<Partner> reader){
		return stepBuilderFactory.get("step")
				.<Partner,Partner>chunk(1)
				.reader(reader)
				.processor(processor())
				.writer(writer())
				.listener(logProcessListener())
				.build();
	}
	
	@Bean
	//@StepScope
	@Scope(value="step", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public FlatFileItemReader<Partner> reader(@Value("#{jobParameters[pathToFile]}") String pathToFile){
		FlatFileItemReader<Partner> itemReader = new FlatFileItemReader<Partner>();
		itemReader.setLineMapper(lineMapper());
		itemReader.setResource(new ClassPathResource(pathToFile));
		return itemReader;
	}
	
	@Bean
	public LineMapper<Partner> lineMapper(){
		DefaultLineMapper<Partner> lineMapper = new DefaultLineMapper<Partner>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[]{"name","email"});
		lineTokenizer.setIncludedFields(new int[]{0,2});
		BeanWrapperFieldSetMapper<Partner> fieldSetMapper = new BeanWrapperFieldSetMapper<Partner>();
		fieldSetMapper.setTargetType(Partner.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}
	
	@Bean
	public ItemProcessor<Partner,Partner> processor(){
		return new LogItemProcessor();
	}
	
	@Bean
	public ItemWriter<Partner> writer(){
		JdbcBatchItemWriter<Partner> itemWriter = new JdbcBatchItemWriter<Partner>();
		itemWriter.setSql("INSERT INTO PARTNER (NAME, EMAIL) VALUES (:name,:email)");
		itemWriter.setDataSource(infrastructureConfiguration.dataSource());
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Partner>());
		return itemWriter;
	}
	
	@Bean
	public ProtocolListener protocolListener(){
		return new ProtocolListener();
	}
	
	@Bean
	public LogProcessListener logProcessListener(){
		return new LogProcessListener();
	}
	
}
