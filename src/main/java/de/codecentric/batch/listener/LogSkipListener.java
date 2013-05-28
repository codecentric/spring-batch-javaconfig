package de.codecentric.batch.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.SkipListener;

import de.codecentric.batch.domain.Partner;

/**
 * @author Tobias Flohre
 */
public class LogSkipListener implements SkipListener<Partner, Partner> {

	private static final Log LOGGER = LogFactory.getLog(LogSkipListener.class);

	public void onSkipInProcess(Partner partner, Throwable throwable) {
		LOGGER.info("Partner was skipped in process: "+partner+".",throwable);
	}

	public void onSkipInRead(Throwable arg0) {
	}

	public void onSkipInWrite(Partner arg0, Throwable arg1) {
	}

}
