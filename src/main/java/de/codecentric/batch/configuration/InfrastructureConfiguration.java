package de.codecentric.batch.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;

/**
 * @author Tobias Flohre
 */
public interface InfrastructureConfiguration {

	@Bean
	public abstract DataSource dataSource();

}