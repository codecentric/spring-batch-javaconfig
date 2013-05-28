package de.codecentric.batch.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ItemProcessListener;

/**
 * @author Tobias Flohre
 */
public class LogProcessListener implements ItemProcessListener<Object, Object> {

	private static final Log log = LogFactory.getLog(LogProcessListener.class);
	
	public void afterProcess(Object item, Object result) {
		log.info("Input to Processor: "+item.toString());
		log.info("Output of Processor: "+result.toString());
	}

	public void beforeProcess(Object item) {
	}

	public void onProcessError(Object item, Exception e) {
	}

}
