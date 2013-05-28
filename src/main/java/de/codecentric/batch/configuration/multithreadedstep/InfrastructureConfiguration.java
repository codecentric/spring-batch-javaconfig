package de.codecentric.batch.configuration.multithreadedstep;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author Tobias Flohre
 */
public interface InfrastructureConfiguration {

	@Bean
	public abstract DataSource dataSource();
	
	@Bean
	public abstract TaskExecutor taskExecutor();
	
	@Bean
	public abstract ConnectionFactory connectionFactory();
	
	@Bean
	public abstract Queue queue();

	@Bean
	public abstract JmsTemplate jmsTemplate();

}