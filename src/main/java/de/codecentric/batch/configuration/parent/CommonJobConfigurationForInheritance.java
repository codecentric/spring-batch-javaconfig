package de.codecentric.batch.configuration.parent;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import de.codecentric.batch.configuration.InfrastructureConfiguration;
import de.codecentric.batch.domain.Partner;
import de.codecentric.batch.listener.LogProcessListener;
import de.codecentric.batch.listener.ProtocolListener;

/**
 * @author Tobias Flohre
 */
public abstract class CommonJobConfigurationForInheritance {
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	private InfrastructureConfiguration infrastructureConfiguration;
	
	protected CustomJobBuilderFactory customJobBuilders(){
		return new CustomJobBuilderFactory(jobRepository, protocolListener());
	}
	
	protected CustomStepBuilderFactory<Partner,Partner> customStepBuilders(){
		return new CustomStepBuilderFactory<Partner,Partner>(
				jobRepository,
				transactionManager,
				completionPolicy(),
				reader(),
				processor(),
				writer(),
				logProcessListener());
	}
	
	@Bean
	public CompletionPolicy completionPolicy(){
		return new SimpleCompletionPolicy(1);
	}
	
	public abstract ItemProcessor<Partner,Partner> processor();
	
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
		lineTokenizer.setNames(new String[]{"name","email","gender"});
		lineTokenizer.setIncludedFields(new int[]{0,2,3});
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

	@Bean
	public ProtocolListener protocolListener(){
		return new ProtocolListener();
	}
	
	@Bean
	public LogProcessListener logProcessListener(){
		return new LogProcessListener();
	}

}
