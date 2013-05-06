package de.codecentric.batch.configuration.parent;

import java.util.List;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;

public class CustomJobBuilderFactory extends JobBuilderFactory {

	private List<JobExecutionListener> listeners;
	
	public CustomJobBuilderFactory(JobRepository jobRepository, List<JobExecutionListener> listeners) {
		super(jobRepository);
		this.listeners = listeners;
	}

	@Override
	public JobBuilder get(String name) {
		JobBuilder jobBuilder = super.get(name);
		for (JobExecutionListener jobExecutionListener: listeners){
			jobBuilder = jobBuilder.listener(jobExecutionListener);
		}
		return jobBuilder;
	}
	
	

}
