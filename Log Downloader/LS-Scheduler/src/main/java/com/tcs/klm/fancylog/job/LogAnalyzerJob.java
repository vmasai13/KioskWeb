package com.tcs.klm.fancylog.job;

import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tcs.klm.fancylog.task.LogAnalyzerTask;
import com.tcs.klm.fancylog.utils.FancySharedInfo;

public class LogAnalyzerJob extends QuartzJobBean implements StatefulJob {

    private static final Logger APPLICATION_LOGGER = LoggerFactory.getLogger(LogAnalyzerJob.class);

    @Autowired
    private LogAnalyzerTask logAnalyzerTask;

    public void setLogAnalyzerTask(LogAnalyzerTask logAnalyzerTask) {
        this.logAnalyzerTask = logAnalyzerTask;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        APPLICATION_LOGGER.info("LogAnalyzerJob started");
        Calendar calendar = null;
        if (FancySharedInfo.getInstance().getCalendar() == null) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, -2);
            FancySharedInfo.getInstance().setCalendar(calendar);
            logAnalyzerTask.performTask(calendar);
        }
        else {
            calendar = FancySharedInfo.getInstance().getCalendar();
            logAnalyzerTask.performTask(calendar);
        }
        APPLICATION_LOGGER.info("LogAnalyzerJob end");
    }
}
