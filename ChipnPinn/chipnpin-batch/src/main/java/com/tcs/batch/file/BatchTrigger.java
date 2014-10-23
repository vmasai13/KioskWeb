package com.tcs.batch.file;

import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchTrigger {

	/**
	 * @param args
	 * @throws SchedulerException 
	 */
	public static void main(String[] args) throws SchedulerException {
		 
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:batch-context.xml");
		context.registerShutdownHook();
	}
}
