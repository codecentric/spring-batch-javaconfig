package de.codecentric.batch.configuration.parent;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.batch.listener.ProtocolListener;

@Configuration
public class ParentJobConfiguration {
	
	@Autowired
	protected StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Bean
	public CustomJobBuilderFactory customJobBuilderFactory(){
		List<JobExecutionListener> listeners = new ArrayList<JobExecutionListener>();
		listeners.add(protocolListener());
		return new CustomJobBuilderFactory(jobRepository, listeners);
	}
	
	@Bean
	public ProtocolListener protocolListener(){
		return new ProtocolListener();
	}
	

}
