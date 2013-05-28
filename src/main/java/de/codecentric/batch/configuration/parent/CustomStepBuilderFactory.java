package de.codecentric.batch.configuration.parent;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.StepListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Tobias Flohre
 */
public class CustomStepBuilderFactory<I,O> {
	
	private JobRepository jobRepository;
	private PlatformTransactionManager transactionManager;
	private CompletionPolicy completionPolicy;
	private ItemReader<I> itemReader;
	private ItemProcessor<I,O> itemProcessor;
	private ItemWriter<O> itemWriter;
	private StepListener[] stepListeners;

	public CustomStepBuilderFactory(JobRepository jobRepository, PlatformTransactionManager transactionManager, 
			CompletionPolicy completionPolicy, ItemReader<I> itemReader, ItemProcessor<I,O> itemProcessor, 
			ItemWriter<O> itemWriter, StepListener... stepListeners) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
		this.completionPolicy = completionPolicy;
		this.itemReader = itemReader;
		this.itemProcessor = itemProcessor;
		this.itemWriter = itemWriter;
		this.stepListeners = stepListeners;
	}

	public SimpleStepBuilder<I,O> get(String name) {
		SimpleStepBuilder<I,O> builder = new StepBuilder(name)
			.repository(jobRepository)
			.transactionManager(transactionManager)
			.<I,O>chunk(completionPolicy)
			.reader(itemReader)
			.processor(itemProcessor)
			.writer(itemWriter);
		for (StepListener listener: stepListeners){
			if (listener instanceof ChunkListener){
				ChunkListener chunkListener = (ChunkListener) listener;
				builder.listener(chunkListener);
			}
			if (listener instanceof StepExecutionListener){
				StepExecutionListener stepExecutionListener = (StepExecutionListener) listener;
				builder.listener(stepExecutionListener);
			}
			if (listener instanceof ItemReadListener<?>){
				@SuppressWarnings("unchecked")
				ItemReadListener<I> itemReadListener = (ItemReadListener<I>) listener;
				builder.listener(itemReadListener);
			}
			if (listener instanceof ItemProcessListener<?,?>){
				@SuppressWarnings("unchecked")
				ItemProcessListener<I,O> itemProcessListener = (ItemProcessListener<I,O>) listener;
				builder.listener(itemProcessListener);
			}
			if (listener instanceof ItemWriteListener<?>){
				@SuppressWarnings("unchecked")
				ItemWriteListener<O> itemWriteListener = (ItemWriteListener<O>) listener;
				builder.listener(itemWriteListener);
			}
		}
		return builder;
	}

}
