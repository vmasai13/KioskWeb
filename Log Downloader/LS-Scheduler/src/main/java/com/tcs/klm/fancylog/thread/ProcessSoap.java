package com.tcs.klm.fancylog.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.tcs.klm.fancylog.analysis.LogAnalyzer;
import com.tcs.klm.fancylog.domain.LogKey;
import com.tcs.klm.fancylog.utils.FancySharedInfo;
import com.tcs.klm.fancylog.utils.Utils;

public class ProcessSoap {
	
	private static Map<String, StringBuffer> listTempLogs = new HashMap<String, StringBuffer>();
	private static Map<String, String> listWholeSession = new HashMap<String, String>();
    private static Map<String, LogKey> listTmpKeys = new HashMap<String, LogKey>();
	
	public void processSoapLogs(String fileName, String outputFile,String sessionIDPossition, String identify) throws IOException {
		String year = Calendar.getInstance().get(Calendar.YEAR)+"";
    	String fullSessionID = null;
    	String actualSessionID = null;
    	Map<String, Integer> listActualSessionId = new HashMap<String, Integer>();
    	String date = null;
    	int sessionIDCounter = 0;
    	File file = new File(fileName);
    	BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFile)));
    	BufferedReader br = new BufferedReader(new FileReader(file));
        String currentLine = null;
        while ((currentLine = br.readLine()) != null) {
//        	if(currentLine.contains("M_9PIc33S8ADDlEqseYoOFP e10s")) {
//        		System.out.println(currentLine);
//        	}
        	if (currentLine.startsWith(year)) {
        		date = FancySharedInfo.getInstance().getDateFromData(currentLine);
        		fullSessionID = FancySharedInfo.getInstance().getFullSessionIdentifier(currentLine, sessionIDPossition);
        		if(Utils.isStringNotEmpty(fullSessionID)) {
        			actualSessionID = FancySharedInfo.getInstance().getActualSessionIdentifier(fullSessionID);
        			sessionIDCounter = FancySharedInfo.getInstance().getSessionIdentifierCounter(fullSessionID);
        			
        			if (!listTempLogs.containsKey(fullSessionID)) {
            			StringBuffer sbfTemp = new StringBuffer();
            			sbfTemp.append(fileName).append("\n");
            			sbfTemp.append(currentLine).append("\n");
            			listTempLogs.put(fullSessionID, sbfTemp);
            			listActualSessionId.put(actualSessionID,sessionIDCounter);
            		} else if(listTempLogs.containsKey(fullSessionID)) {
            			listTempLogs.get(fullSessionID).append(currentLine).append("\n");
            		} else {
            			System.out.println("Check this case !!!:"+currentLine);
            		}
        			
        		}
            } else {
            	System.out.println("Line not parsed.. Need to check it !!!:");
            }
        }
        br.close();
        bw.flush();
        bw.close();
	}
	
	/*private void processLastLineToDB(String lineText, String sessionIDPossition, String year, String fileName) throws IOException {
        if (lineText.startsWith(year) && lineText.endsWith("Envelope>")) {
            String sessionID = null;
            String serviceName = null;
            String date = null;
            if (lineText.contains(".PROVIDER_REQUEST")) {
                sessionID = FancySharedInfo.getInstance().getFullSessionIdentifier(lineText, sessionIDPossition);
                serviceName = FancySharedInfo.getInstance().getServiceName(lineText);
                date = FancySharedInfo.getInstance().getDate(lineText);
                LogAnalyzer logAnalyzer = logAnalyzerMap.get(serviceName);
                if (logAnalyzer != null) {
                    List<LogKey> logKeys = logAnalyzer.getLogKeyFromRequest(lineText, mongoTemplate);
                    if (logKeys != null && !logKeys.isEmpty()) {
                        StringBuffer sbfTemp = new StringBuffer();
                        sbfTemp.append(fileName).append("\n");
                        sbfTemp.append(lineText).append("\n");
                        for (LogKey logKey : logKeys) {
                            logKey.setSessionID(sessionID);
                            logKey.setDate(date);
                            Calendar cal = Calendar.getInstance();
                            cal.add(Calendar.DATE, Integer.valueOf(noOfDays));
                            Date expireDate = cal.getTime();
                            logKey.setExpireAt(expireDate);
                        }
                        lstTempLogs.put(sessionID, sbfTemp);
                        lstTmpKeys.put(sessionID, logKeys);
                    }
                }
            }
            else if (lineText.contains(".PROVIDER_RESPONSE")) {
                sessionID = FancySharedInfo.getInstance().getFullSessionIdentifier(lineText, sessionIDPossition);
                if (lstTmpKeys.containsKey(sessionID)) {
                    lstTempLogs.get(sessionID).append(lineText).append("\n");
                    serviceName = FancySharedInfo.getInstance().getServiceName(lineText);
                    LogAnalyzer logAnalyzer = logAnalyzerMap.get(serviceName);
                    if (logAnalyzer != null) {
                        LogKey responseLogKey = logAnalyzer.getLogKeyFromResponse(lineText, mongoTemplate, offerMap);
                        if (responseLogKey != null) {
                            List<LogKey> logKeys = lstTmpKeys.get(sessionID);
                            for (LogKey logKey : logKeys) {
                                logKey.setErrorCode(responseLogKey.getErrorCode());
                                logKey.setErrorDescription(responseLogKey.getErrorDescription());
                            }
                        }
                    }
                    StringBuffer log = lstTempLogs.get(sessionID);
                    String strLog = log.toString();
                    String compressedLog = Utils.compress(strLog);

                    DBCollection dbCollectionLog = mongoTemplate.getCollection(COLLECTION_LOGS);

                    DBObject dBObjectLog = new BasicDBObject();
                    dBObjectLog.put("log", compressedLog);
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, Integer.valueOf(noOfDays));
                    Date expireDate = cal.getTime();
                    dBObjectLog.put("expireAt", expireDate);
                    dbCollectionLog.insert(dBObjectLog);
                    String logID = dBObjectLog.get("_id").toString();

                    List<LogKey> logKeys = lstTmpKeys.get(sessionID);
                    for (LogKey key : logKeys) {
                        key.setLogID(logID);
                        mongoTemplate.insert(key, COLLECTION_TRANSACTION);
                    }

                    lstTmpKeys.remove(sessionID);
                    lstTempLogs.remove(sessionID);

                }
                else {
                    lstTmpKeys.remove(sessionID);
                    lstTempLogs.remove(sessionID);
                }
            }
            else {
                sessionID = FancySharedInfo.getInstance().getFullSessionIdentifier(lineText, sessionIDPossition);
                if (lstTmpKeys.containsKey(sessionID)) {
                    lstTempLogs.get(sessionID).append(lineText).append("\n");
                }
            }
        }
    }*/
}
