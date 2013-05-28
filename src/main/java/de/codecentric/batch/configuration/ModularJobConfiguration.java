package de.codecentric.batch.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tobias Flohre
 */
@Configuration
@EnableBatchProcessing(modular=true)
public class ModularJobConfiguration {
	
	@Bean
	public ApplicationContextFactory someJobs() {
		return new GenericApplicationContextFactory(FlatfileToDbJobConfiguration.class);
	}

	@Bean
	public ApplicationContextFactory someMoreJobs() {
		return new GenericApplicationContextFactory(FlatfileToDbWithParametersJobConfiguration.class);
	}

}
