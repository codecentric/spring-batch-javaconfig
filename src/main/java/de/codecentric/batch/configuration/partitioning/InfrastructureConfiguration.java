package de.codecentric.batch.configuration.partitioning;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;

/**
 * @author Tobias Flohre
 */
public interface InfrastructureConfiguration {

	@Bean
	public abstract DataSource dataSource();
	
	@Bean
	public abstract TaskExecutor taskExecutor();

}