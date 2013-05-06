package de.codecentric.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

import de.codecentric.batch.domain.Partner;


/**
 * Dummy {@link ItemProcessor} which only logs data it receives.
 */
public class LogItemProcessor implements ItemProcessor<Partner,Partner> {

	private static final Log log = LogFactory.getLog(LogItemProcessor.class);
	
	public Partner process(Partner item) throws Exception {
		log.info(item);
		return item;
	}

}
