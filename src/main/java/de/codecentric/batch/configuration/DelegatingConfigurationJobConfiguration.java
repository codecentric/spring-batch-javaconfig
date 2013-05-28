package de.codecentric.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import de.codecentric.batch.configuration.parent.CommonJobConfigurationForDelegation;
import de.codecentric.batch.domain.Partner;

/**
 * @author Tobias Flohre
 */
@Configuration
@Import(CommonJobConfigurationForDelegation.class)
public class DelegatingConfigurationJobConfiguration{
	
	@Autowired
	private InfrastructureConfiguration infrastructureConfiguration;
	
	@Autowired
	private CommonJobConfigurationForDelegation commonJobConfiguration;
	
	@Autowired
	private StepBuilderFactory stepBuilders;
	
	@Bean
	public Job delegatingConfigurationJob(){
		return commonJobConfiguration.customJobBuilders()
				.get("delegatingConfigurationJob")
				.start(step())
				.build();
	}
	
	@Bean
	public Step step(){
		return stepBuilders.get("step")
				.<Partner,Partner>chunk(1)
				.reader(reader())
				.writer(writer())
				.build();
	}
	
	@Bean
	public FlatFileItemReader<Partner> reader(){
		FlatFileItemReader<Partner> itemReader = new FlatFileItemReader<Partner>();
		itemReader.setLineMapper(lineMapper());
		itemReader.setResource(new ClassPathResource("partner-import.csv"));
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
	public ItemWriter<Partner> writer(){
		JdbcBatchItemWriter<Partner> itemWriter = new JdbcBatchItemWriter<Partner>();
		itemWriter.setSql("INSERT INTO PARTNER (NAME, EMAIL) VALUES (:name,:email)");
		itemWriter.setDataSource(infrastructureConfiguration.dataSource());
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Partner>());
		return itemWriter;
	}

}
