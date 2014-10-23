package com.tcs.batch.file;
import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ChipNPinBatchJob extends QuartzJobBean {
	
	private ChinNPinBatchTask batchTask;
    private static Logger CHIPNPIN_BATCH = LoggerFactory.getLogger(ChipNPinBatchJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
	
		System.out.println("hihihih");
		try {
			CHIPNPIN_BATCH.info("Batch started at", (Calendar.getInstance().getTime()));

			batchTask.performJob();
			CHIPNPIN_BATCH.info("Batch ended at", (Calendar.getInstance().getTime()));

			
		} catch (Exception e){
			e.printStackTrace();
			CHIPNPIN_BATCH.error("Technical exception occured ", e);
		}
		
	}

}
