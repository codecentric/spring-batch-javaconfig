package de.codecentric.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.batch.ValidationProcessor;
import de.codecentric.batch.configuration.parent.CommonJobConfigurationForInheritance;
import de.codecentric.batch.domain.Partner;
import de.codecentric.batch.domain.UnknownGenderException;
import de.codecentric.batch.listener.LogSkipListener;

/**
 * @author Tobias Flohre
 */
@Configuration
public class InheritedConfigurationJobConfiguration extends CommonJobConfigurationForInheritance{
	
	@Bean
	public Job inheritedConfigurationJob(){
		return customJobBuilders().get("inheritedConfigurationJob")
				.start(step())
				.build();
	}
	
	@Bean
	public Step step(){
		return customStepBuilders().get("step")
				.faultTolerant()
				.skipLimit(10)
				.skip(UnknownGenderException.class)
				.listener(logSkipListener())
				.build();
	}

	@Override
	@Bean
	public ItemProcessor<Partner, Partner> processor() {
		return new ValidationProcessor();
	}
	
	@Override
	@Bean
	public CompletionPolicy completionPolicy() {
		return new SimpleCompletionPolicy(3);
	}

	@Bean
	public LogSkipListener logSkipListener(){
		return new LogSkipListener();
	}
	
}
