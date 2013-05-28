package de.codecentric.batch.multithreadedstep;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.codecentric.batch.configuration.multithreadedstep.MultiThreadedStepJobConfiguration;
import de.codecentric.batch.configuration.multithreadedstep.StandaloneInfrastructureConfiguration;

/**
 * @author Tobias Flohre
 */
@ContextConfiguration(classes={StandaloneInfrastructureConfiguration.class, MultiThreadedStepJobConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class MultithreadedStepJobTests {
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Test
	public void testLaunchJob() throws Exception {
		for (int i = 0; i<20;i++){
			jmsTemplate.convertAndSend("Nachricht Nummer "+i+".");
		}
		JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
		jobLauncher.run(job, jobParameters);
		assertThat(jobRepository.getLastJobExecution("multiThreadedStepJob", jobParameters).getExitStatus(),is(ExitStatus.COMPLETED));
	}
	
}
