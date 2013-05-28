package de.codecentric.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;


/**
 * Dummy {@link ItemProcessor} which only logs data it receives.
 * @author Tobias Flohre
 */
public class LogItemProcessor<T> implements ItemProcessor<T,T> {

	private static final Log log = LogFactory.getLog(LogItemProcessor.class);
	
	public T process(T item) throws Exception {
		log.info(item);
		return item;
	}

}
