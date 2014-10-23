package com.tcs.fancylog;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FancyLogMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        String springConfig = "applicationContext.xml";
       // FancySharedInfo.getInstance().setLastTaskSuccessful(true);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
        context.registerShutdownHook();
    }

}
