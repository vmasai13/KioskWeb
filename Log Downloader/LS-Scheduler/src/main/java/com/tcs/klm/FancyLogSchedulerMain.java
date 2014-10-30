package com.tcs.klm;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tcs.klm.fancylog.thread.DownloadAnalysisThread;
import com.tcs.klm.fancylog.utils.FancySharedInfo;

public class FancyLogSchedulerMain {
    public static void main(String[] args) {
//        FancySharedInfo.getInstance().setLastTaskSuccessful(true);
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        DownloadAnalysisThread dt = new DownloadAnalysisThread();
        String fileName = "C:/data/download/test/webkiosk_technical.log";
        String outputFileName = "C:/data/download/test/technical.log";
//        dt.analizeFileContent(fileName,outputFileName, "technical","7YRBWW");
        fileName = "C:/data/download/test/webkiosk_soap.log";
        outputFileName = "C:/data/download/test/soap.log";
        dt.analizeFileContent(fileName, outputFileName, "soap", "5cjbmg");
        System.out.println("Completed !!!");
//        LogAnalyzerTask logAnalyzeTask = new LogAnalyzerTask();
//        logAnalyzeTask.performTask(Calendar.getInstance());
        
    }

}
