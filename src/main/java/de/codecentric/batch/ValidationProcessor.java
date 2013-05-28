package de.codecentric.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import de.codecentric.batch.domain.Partner;
import de.codecentric.batch.domain.UnknownGenderException;


/**
 * {@link ItemProcessor} which validates data.
 * @author Tobias Flohre
 */
public class ValidationProcessor implements ItemProcessor<Partner,Partner> {

	private static final Log log = LogFactory.getLog(ValidationProcessor.class);
	
	public Partner process(Partner partner) throws Exception {
		log.info(partner);
		if (!("m".equals(partner.getGender()) || ("w".equals(partner.getGender())))){
			throw new UnknownGenderException("Gender "+partner.getGender()+" is unknown!");
		}
		return partner;
	}

}
