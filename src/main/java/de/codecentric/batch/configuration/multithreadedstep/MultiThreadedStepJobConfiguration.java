package de.codecentric.batch.configuration.multithreadedstep;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.jms.JmsItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.batch.LogItemProcessor;
import de.codecentric.batch.LogItemWriter;
import de.codecentric.batch.listener.ProtocolListener;

/**
 * @author Tobias Flohre
 */
@Configuration
public class MultiThreadedStepJobConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilders;
	
	@Autowired
	private StepBuilderFactory stepBuilders;
	
	@Autowired
	private InfrastructureConfiguration infrastructureConfiguration;
	
	@Bean
	public Job multiThreadedStepJob(){
		return jobBuilders.get("multiThreadedStepJob")
				.listener(protocolListener())
				.start(step())
				.build();
	}
	
	@Bean
	public Step step(){
		return stepBuilders.get("step")
				.<String,String>chunk(1)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.taskExecutor(infrastructureConfiguration.taskExecutor())
				.throttleLimit(4)
				.build();
	}
	
	@Bean
	public JmsItemReader<String> reader(){
		JmsItemReader<String> itemReader = new JmsItemReader<String>();
		itemReader.setJmsTemplate(infrastructureConfiguration.jmsTemplate());
		return itemReader;
	}
	
	@Bean
	public ItemProcessor<String,String> processor(){
		return new LogItemProcessor<String>();
	}
	
	@Bean
	public ItemWriter<String> writer(){
		return new LogItemWriter<String>();
	}
	
	@Bean
	public ProtocolListener protocolListener(){
		return new ProtocolListener();
	}
	
}
