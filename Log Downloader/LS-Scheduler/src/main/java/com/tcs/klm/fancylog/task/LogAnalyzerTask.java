package com.tcs.klm.fancylog.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.tcs.klm.fancylog.analysis.LogAnalyzer;
import com.tcs.klm.fancylog.thread.DownloadAnalysisThread;
import com.tcs.klm.fancylog.utils.FancySharedInfo;

@Component
public class LogAnalyzerTask {

	@Autowired
	private Properties fancyLogProps;
    private static final Logger APPLICATION_LOGGER = LoggerFactory.getLogger(LogAnalyzerTask.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Map<String, LogAnalyzer> logAnalyzerMap;

    public static final String COLLECTION_NAME = "settings";

    public void performTask(Calendar calendar) {
        
    	if (fancyLogProps != null) {
        	try {
				String applicationName = (String) fancyLogProps.get("applicationName");
				String fancyLogURLPattern = (String) fancyLogProps.get("fancyLogURLPattern");
				String host = (String) fancyLogProps.get("host");
				String nodeList = (String) fancyLogProps.get("nodeList");
				String[] nodes = nodeList.split(",");
				String instance = (String) fancyLogProps.get("instance");
				String[] exceptionFileNames = ((String)fancyLogProps.get("exceptionFileNames")).split(","); 
				String logInURL = (String) fancyLogProps.get("loginUrl");
				String userName = (String) fancyLogProps.get("username");
				String passWord = (String) fancyLogProps.get("password");
				String fileName = (String) fancyLogProps.get("fileName");
				String sessionIDPossition = (String) fancyLogProps.get("sessionIdPosition");
				String downloadLocation = (String) fancyLogProps.get("downloadLocation");
				String[] validFilesForDownload = ((String) fancyLogProps.get("validFilesForDownload")).split(",");
				String noOfDays = (String) fancyLogProps.get("noOfDays");
				List<String> lstHyeperLink = new ArrayList<String>();
				APPLICATION_LOGGER.info("trying to loggin Fancylog main page");
				HttpClient httpClient = FancySharedInfo.getInstance().getAuthenticatedHttpClient(logInURL, userName, passWord);

				if (httpClient != null) {
				    String date1 = FancySharedInfo.getInstance().getDateFormat(calendar);
				    // Configured the download date in property file.
				    String[] dates = ((String) fancyLogProps.getProperty("downloadDate")).split(",");
				    for(String date: dates) {
				    	String logFileURL = null;
				        fancyLogURLPattern = fancyLogURLPattern.replace("<host>", host);
				        fancyLogURLPattern = fancyLogURLPattern.replace("<instance>", instance);
				        fancyLogURLPattern = fancyLogURLPattern.replace("<applicationName>", applicationName);
				        for (String node : nodes) {
				            String fancyLogURL = fancyLogURLPattern.replace("<node>", node);
				            String responseStream = getFancyLogMainPage(httpClient, fancyLogURL);
				            if (responseStream != null) {
				                Pattern regexPattern = Pattern.compile("<a\\s[^>]*href\\s*=\\s*\\\"([^\"]*)\"[^>]*>(.*?)</a>");
				                Matcher matcher = regexPattern.matcher(responseStream);
				                while (matcher.find()) {
				                    logFileURL = matcher.group(1);
				                    if (isValid(logFileURL, fileName, date, validFilesForDownload)) {
				                    	lstHyeperLink.add(logFileURL);
				                    }
				                }
				            }
				        }
				        if (!lstHyeperLink.isEmpty()) {
				            starFileDownloadAndAnalysis(logInURL, userName, passWord, lstHyeperLink, sessionIDPossition, downloadLocation, noOfDays);
				        }
				        if (FancySharedInfo.getInstance().getFaildHyperLinks() != null) {
				            APPLICATION_LOGGER.info("retrying failed log files");
//                        starFileDownloadAndAnalysis(logInURL, userName, passWord, FancySharedInfo.getInstance().getFaildHyperLinks(), sessionIDPossition, downloadLocation, noOfDays);
				            FancySharedInfo.getInstance().clearFaildHyperLinks();
				        }
				    }
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Exception :"+e);
			}
        }
    }

    private void starFileDownloadAndAnalysis(String logInURL, String userName, String passWord, List<String> lstHyeperLink, String sessionIDPossition, String downloadLocation, String noOfDays) {
        APPLICATION_LOGGER.info("Download analysis Started... ");
        try {
            Runnable task;
            List<Thread> threads = new ArrayList<Thread>();
            for (String hyperLink : lstHyeperLink) {
                task = new DownloadAnalysisThread(logInURL, userName, passWord, hyperLink, sessionIDPossition, downloadLocation, logAnalyzerMap, mongoTemplate, noOfDays);
                Thread thread = new Thread(task);
                thread.start();
                threads.add(thread);
            }
            for (Thread thread : threads) {
                thread.join(60 * 60 * 1000);
            }
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        APPLICATION_LOGGER.info("Download analysis Completed...   ");
        System.out.println("Download analysis Completed...   ");
    }

    private boolean isValid(String logFileURL, String fileName, String date, String[] validFiles) {
        boolean flag = false;
        flag = (logFileURL.contains(".gz") || logFileURL.contains(".zip")) && logFileURL.contains("action=redir") && logFileURL.contains("oldlogs") && logFileURL.contains(fileName) && logFileURL.contains(date);
        // Checking to filter the required files
        if (flag) {
        	for (String validFile : validFiles) {
            	flag = logFileURL.contains(validFile);
            	if (flag) {
            		break;
            	}
            }
        }
        return flag;
    }

    private String getFancyLogMainPage(HttpClient httpClient, String strFancyLogMainURL) {
        String responseString = null;
        GetMethod getMethod = new GetMethod(strFancyLogMainURL);
        int code = 0;
        try {
            code = httpClient.executeMethod(getMethod);
            responseString = getMethod.getResponseBodyAsString();
        }
        catch (HttpException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (code != 200) {
            APPLICATION_LOGGER.error("unable to access fancy log main page");
            return null;
        }
        else {
            return responseString;
        }
    }

    private boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    }
                    else {
                        files[i].delete();
                    }
                }
            }
        }
        return (directory.delete());
    }
}
