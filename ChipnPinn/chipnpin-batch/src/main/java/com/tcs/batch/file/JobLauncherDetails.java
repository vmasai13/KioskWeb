package com.tcs.batch.file;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.JobExecutionContext;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobLauncherDetails extends QuartzJobBean {
	 
	  static final String JOB_NAME = "importLogs";
	 
	  private JobLocator jobLocator;
	 
	  private JobLauncher jobLauncher;
	 
	  public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	  }
	 
	  public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	  }
	 
	  @SuppressWarnings("unchecked")
	  protected void executeInternal(JobExecutionContext context) {
	 
		Map<String, Object> jobDataMap = context.getMergedJobDataMap();
	 
		String jobName = JOB_NAME;
	 
	 
	
			try {
				JobParameters jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
				JobExecution execution = jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
				System.out.println("Exit Status : " + execution.getStatus());
				//execution.stop();
				
			} catch (JobExecutionAlreadyRunningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JobRestartException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JobInstanceAlreadyCompleteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JobParametersInvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchJobException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	  }
	 
	 

}
