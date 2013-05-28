package de.codecentric.batch.configuration.parent;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;

/**
 * @author Tobias Flohre
 */
public class CustomJobBuilderFactory extends JobBuilderFactory {

	private JobExecutionListener[] listeners;
	
	public CustomJobBuilderFactory(JobRepository jobRepository, JobExecutionListener... listeners) {
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
