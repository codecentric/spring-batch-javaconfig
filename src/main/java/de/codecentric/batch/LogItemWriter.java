package de.codecentric.batch;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;


/**
 * Dummy {@link ItemWriter} which only logs data it receives.
 * @author Tobias Flohre
 */
public class LogItemWriter<T> implements ItemWriter<T> {

	private static final Log log = LogFactory.getLog(LogItemWriter.class);
	
	/**
	 * @see ItemWriter#write(java.util.List)
	 */
	public void write(List<? extends T> data) throws Exception {
		log.info(data);
	}

}
