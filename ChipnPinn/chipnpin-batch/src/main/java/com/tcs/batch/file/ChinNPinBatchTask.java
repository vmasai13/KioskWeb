package com.tcs.batch.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ChinNPinBatchTask {

    private static Logger CHIPNPIN_BATCH = LoggerFactory.getLogger(ChinNPinBatchTask.class);

	/**
	 * @param args
	 */
	public void performJob() {
		
			ApplicationContext context =
				    new FileSystemXmlApplicationContext("classpath:batch-context.xml");
		 
			JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
			Job job = (Job) context.getBean("importLogs");
		 
			try {
				CHIPNPIN_BATCH.info("Batch about to start......");
				JobExecution execution = jobLauncher.run(job, new JobParameters());
				CHIPNPIN_BATCH.info("Exit Status :", (execution.getStatus()));
			//	System.out.println("Exit Status : " + execution.getStatus());
		 
			} catch (Exception e) {
				e.printStackTrace();
			}
		 
			System.out.println("Done");

	}

}
