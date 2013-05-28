package de.codecentric.batch.listener;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;

/**
 * @author Tobias Flohre
 */
public class ProtocolListener implements JobExecutionListener {

	private static final Log LOGGER = LogFactory.getLog(ProtocolListener.class);

	public void afterJob(JobExecution jobExecution) {
		StringBuilder protocol = new StringBuilder();
		protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
		protocol.append("Protocol for " + jobExecution.getJobInstance().getJobName() + " \n");
		protocol.append("  Started     : "+ jobExecution.getStartTime()+"\n");
		protocol.append("  Finished    : "+ jobExecution.getEndTime()+"\n");
		protocol.append("  Exit-Code   : "+ jobExecution.getExitStatus().getExitCode()+"\n");
		protocol.append("  Exit-Descr. : "+ jobExecution.getExitStatus().getExitDescription()+"\n");
		protocol.append("  Status      : "+ jobExecution.getStatus()+"\n");
		protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");

		protocol.append("Job-Parameter: \n");		
		JobParameters jp = jobExecution.getJobParameters();
		for (Iterator<Entry<String,JobParameter>> iter = jp.getParameters().entrySet().iterator(); iter.hasNext();) {
			Entry<String,JobParameter> entry = iter.next();
			protocol.append("  "+entry.getKey()+"="+entry.getValue()+"\n");
		}
		protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");		
		
		for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
			protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
			protocol.append("Step " + stepExecution.getStepName() + " \n");
			protocol.append("WriteCount: " + stepExecution.getWriteCount() + "\n");
			protocol.append("Commits: " + stepExecution.getCommitCount() + "\n");
			protocol.append("SkipCount: " + stepExecution.getSkipCount() + "\n");
			protocol.append("Rollbacks: " + stepExecution.getRollbackCount() + "\n");
			protocol.append("Filter: " + stepExecution.getFilterCount() + "\n");					
			protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
		}
		LOGGER.info(protocol.toString());
	}

	public void beforeJob(JobExecution arg0) {
		// nothing to do
	}

}
