package de.codecentric.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;

public class CheckItemCountItemStream implements ItemStream {

	private static final Log log = LogFactory.getLog(CheckItemCountItemStream.class);

	public void open(ExecutionContext executionContext)
			throws ItemStreamException {
		// TODO Auto-generated method stub

	}

	public void update(ExecutionContext executionContext)
			throws ItemStreamException {
		log.info("ItemCount: "+executionContext.get("FlatFileItemReader.read.count"));
	}

	public void close() throws ItemStreamException {
		// TODO Auto-generated method stub

	}

}
