package de.codecentric.batch;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.codecentric.batch.configuration.FlatfileToDbSkipJobConfiguration;
import de.codecentric.batch.configuration.StandaloneInfrastructureConfiguration;

/**
 * @author Tobias Flohre
 */
@ContextConfiguration(classes={StandaloneInfrastructureConfiguration.class, FlatfileToDbSkipJobConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class FlatfileToDbSkipJobTests {
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setup(){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void testLaunchJob() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addString("pathToFile", "partner-import.csv").toJobParameters();
		jobLauncher.run(job, jobParameters);
		assertThat(jdbcTemplate.queryForObject("select count(*) from partner",Integer.class),is(4));
	}
	
}
