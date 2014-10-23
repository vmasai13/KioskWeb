package com.tcs.fancylog;

import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class FancyLogDownloadJob extends QuartzJobBean {

	private LogDownloadTask fancyLogDownloadTask;
    private static Logger FANCY_LOG_DOWNLOADER = LoggerFactory.getLogger("fancylog-downloader");

    public void setFancyLogDownloadTask(LogDownloadTask fancyLogDownloadTask) {
        this.fancyLogDownloadTask = fancyLogDownloadTask;
    }
    
	  @Override
	    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
	        try {
	            if (FancySharedInfo.getInstance().isLastTaskSuccessful()) {
	                if (!FancySharedInfo.getInstance().isAnalysisInProgress()) {
	                    Calendar beforeTask = Calendar.getInstance();
	                    fancyLogDownloadTask.performTask();
	                    FANCY_LOG_DOWNLOADER.info("Latency {} minutes", (Calendar.getInstance().getTimeInMillis() - beforeTask.getTimeInMillis())/(1000.0 * 60.0));
	                } else {
	                    FANCY_LOG_DOWNLOADER.error("Not performing download job due to previous analysis job is in progress");
	                }
	            } else {
	                FANCY_LOG_DOWNLOADER.error("Not performing download job due to last execution failure");
	            }
	        } catch (Exception e) {
	            FancySharedInfo.getInstance().setLastTaskSuccessful(false);
	            e.printStackTrace();
	            FANCY_LOG_DOWNLOADER.error("Exception occured", e);
	        }
	    }
}
