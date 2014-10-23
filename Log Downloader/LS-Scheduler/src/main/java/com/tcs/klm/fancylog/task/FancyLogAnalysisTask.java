package com.tcs.klm.fancylog.task;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.tcs.klm.fancylog.analysis.LogAnalyzer;
import com.tcs.klm.fancylog.thread.AnalysisThread;
import com.tcs.klm.fancylog.utils.FancySharedInfo;

//import org.apache.commons.lang.stringutils;

@Component(value = "fancyLogAnalysisTask")
public class FancyLogAnalysisTask {

    private static final Logger APPLICATION_LOGGER = LoggerFactory.getLogger(FancyLogAnalysisTask.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Map<String, LogAnalyzer> logAnalyzerMap;

    private static final String COLLECTION_SETTINGS = "settings";
    private String COLLECTION_TRANSACTION;
    private String COLLECTION_LOGS;

    // private static Map<String, String> lstTmpsessionIdUPR = new HashMap<String, String>();
    // private static Map<String, String> lstTmpsessionMap = new HashMap<String, String>();
    // private static Map<String, List<String>> map = new HashMap<String, List<String>>();

    public void performTask() throws IOException {
        APPLICATION_LOGGER.info("FancyLogAnalysisTask {}", System.currentTimeMillis());
        FancySharedInfo.getInstance().setAnalysisInProgress(true);
        DBCollection settingsCollection = mongoTemplate.getCollection(COLLECTION_SETTINGS);
        DBCursor settingsCursor = settingsCollection.find();
        Calendar calendar = Calendar.getInstance();
        while (settingsCursor.hasNext()) {
            DBObject settings = settingsCursor.next();
            // String applicationName = (String) settings.get("applicationName");
            COLLECTION_TRANSACTION = "transactions";
            COLLECTION_LOGS = "logs";
            if (!mongoTemplate.collectionExists(COLLECTION_LOGS)) {
                mongoTemplate.createCollection(COLLECTION_LOGS);
            }
            if (!mongoTemplate.collectionExists(COLLECTION_TRANSACTION)) {
                mongoTemplate.createCollection(COLLECTION_TRANSACTION);
            }
            String sessionIDPossition = (String) settings.get("sessionIdPosition");
            String gzFileLocation = (String) settings.get("downloadLocation");
            String[] names = StringUtils.split(gzFileLocation, "/");
            String tempFileLocation = gzFileLocation.replace(names[names.length - 1], "temp");
            File[] files = getListOfFiles(gzFileLocation);
            if (files != null) {
                (new File(tempFileLocation)).mkdirs();
                try {
                    Runnable task;
                    List<Thread> threads = new ArrayList<Thread>();
                    for (File file : files) {
                        task = new AnalysisThread(file, sessionIDPossition, logAnalyzerMap, mongoTemplate);
                        Thread thread = new Thread(task);
                        thread.start();
                        threads.add(thread);
                    }
                    for (Thread thread : threads) {
                        thread.join();
                    }

                }
                catch (Exception e) {
                    APPLICATION_LOGGER.error("", e);
                }

            }
            Object value = settings.get("noOfDays");
            if (value != null) {
                int noOfDays = Integer.valueOf(value.toString());
                calendar.add(Calendar.HOUR_OF_DAY, -noOfDays * 24);
                Date today = calendar.getTime();
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
                String date = formatter.format(today);
                DBCollection dbCollectionLog = mongoTemplate.getCollection(COLLECTION_LOGS);
                BasicDBObject searchQuery = new BasicDBObject();
                searchQuery.put("date", date);
                dbCollectionLog.remove(searchQuery);

                BasicDBObject regexQuery = new BasicDBObject();
                regexQuery.put("date", new BasicDBObject("$regex", date + ".*").append("$options", "i"));

                DBCollection dbCollectionTransaction = mongoTemplate.getCollection(COLLECTION_TRANSACTION);
                dbCollectionTransaction.remove(regexQuery);
            }

        }
        FancySharedInfo.getInstance().setAnalysisInProgress(false);
        FancySharedInfo.getInstance().setLastTaskSuccessful(true);
        APPLICATION_LOGGER.info("FancyLogAnalysisTask end {}", System.currentTimeMillis());
    }

    public static boolean deleteDirectory(File directory) {
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

    private static File[] getListOfFiles(String directoryPath) {
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }

    // private String getCollectionName() {
    // Calendar calendar = Calendar.getInstance();
    // String collectionName = calendar.get(Calendar.YEAR) + StringUtils.leftPad("" + (calendar.get(Calendar.MONTH) + 1), 2, "0") + StringUtils.leftPad("" + calendar.get(Calendar.DATE), 2, "0");
    // return collectionName;
    // }
}
