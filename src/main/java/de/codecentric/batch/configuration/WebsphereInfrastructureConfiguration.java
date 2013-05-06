package de.codecentric.batch.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class WebsphereInfrastructureConfiguration implements BatchConfigurer, InfrastructureConfiguration {
	
	@Bean
	public DataSource dataSource(){
//		try {
//			InitialContext initialContext = new InitialContext();
//			return (DataSource) initialContext.lookup("java:comp/env/jdbc/MyDataSource");
//		} catch (NamingException e) {
//			throw new RuntimeException("JNDI lookup failed.",e);
//		}
		EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
		return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-hsqldb.sql")
				.addScript("classpath:schema-partner.sql").setType(EmbeddedDatabaseType.HSQL).build();
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
