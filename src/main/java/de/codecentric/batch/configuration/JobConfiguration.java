package de.codecentric.batch.configuration;

import org.springframework.context.annotation.Configuration;

import de.codecentric.batch.configuration.parent.ParentJobConfiguration;

@Configuration
public class JobConfiguration extends ParentJobConfiguration{
	
//	@Bean
//	public Job job(){
//		return customJobBuilderFactory().get("job").start(step()).build();
//	}
//	
//	@Bean
//	public Step step(){
//		return stepBuilderFactory.get("step").chunk(1).reader(reader()).writer(writer()).listener(chunkListener()).build();
//	}
//	
//	@Bean
//	public DummyItemReader reader(){
//		return new DummyItemReader();
//	}
//	
//	@Bean
//	public LogItemProcessor processor(){
//		return new LogItemProcessor();
//	}
//	
//	@Bean
//	public LogItemWriter writer(){
//		return new LogItemWriter();
//	}
//	
//	@Bean
//	public ChunkListener chunkListener(){
//		return new ChunkListenerSupport();
//	}

}
