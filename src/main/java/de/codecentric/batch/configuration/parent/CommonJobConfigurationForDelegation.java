package de.codecentric.batch.configuration.parent;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.batch.listener.ProtocolListener;

/**
 * @author Tobias Flohre
 */
@Configuration
public class CommonJobConfigurationForDelegation {
	
	@Autowired
	private JobRepository jobRepository;
	
	@Bean
	public CustomJobBuilderFactory customJobBuilders(){
		return new CustomJobBuilderFactory(jobRepository, protocolListener());
	}
	
	@Bean
	public ProtocolListener protocolListener(){
		return new ProtocolListener();
	}
	

}
