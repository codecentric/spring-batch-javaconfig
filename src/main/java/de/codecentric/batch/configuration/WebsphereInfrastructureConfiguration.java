package de.codecentric.batch.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * I know this is a fake - but I didn't want to install a Websphere for a little test. 
 * 
 * @author Tobias Flohre
 */
@Configuration
@EnableBatchProcessing
@PropertySource("classpath:batch.properties")
public class WebsphereInfrastructureConfiguration implements BatchConfigurer, InfrastructureConfiguration {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource(){
//		try {
//			InitialContext initialContext = new InitialContext();
//			return (DataSource) initialContext.lookup(env.getProperty("datasource.jndi"));
//		} catch (NamingException e) {
//			throw new RuntimeException("JNDI lookup failed.",e);
//		}
		EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
		return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-hsqldb.sql")
				.addScript("classpath:org/springframework/batch/core/schema-hsqldb.sql")
				.addScript(env.getProperty("db.script.partner")).setType(EmbeddedDatabaseType.HSQL).build();
	}

	public JobRepository getJobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(dataSource());
		factory.setTransactionManager(getTransactionManager());
		factory.afterPropertiesSet();
		return  (JobRepository) factory.getObject();
	}

	public PlatformTransactionManager getTransactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
		//return new WebSphereUowTransactionManager();
	}

	public JobLauncher getJobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(getJobRepository());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}

}
