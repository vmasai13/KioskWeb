package com.tcs.klm.fancylog.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tcs.klm.fancylog.utils.FancySharedInfo;
import com.tcs.klm.fancylog.utils.LogConstant;

public class ProcessTechnical {
	
	public void processWebkioskTechnical(String fileName, String outputFile,String sessionIDPossition, String identify) throws IOException {
    	String sessionID = null;
    	List<String> sessionIdList = new ArrayList<String>();
    	File file = new File(fileName);
    	BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFile)));
    	BufferedReader br = new BufferedReader(new FileReader(file));
        boolean isExceptionFound = false;
        String currentLine = null;
        while ((currentLine = br.readLine()) != null) {
        	if(currentLine.contains("SReRYXAtNrpHAldZ0Fum1A9 e34s")) {
        		System.out.println(currentLine);
        	}
        	if (currentLine.startsWith(LogConstant.ERROR)) {
        		isExceptionFound = false;
        		boolean isSessionIdAlreadyFound = false;
        		if(currentLine.contains(LogConstant.technical_delimiter_1+identify+LogConstant.technical_delimiter_2)) {
            		Iterator<String> iterator = sessionIdList.iterator();
        			while(iterator.hasNext()) {
        				bw.append(currentLine).append("\n");
//        				bw.newLine();
        				isSessionIdAlreadyFound = true;
        				iterator.next();
        			}
        			if (!isSessionIdAlreadyFound) {
        				sessionID = FancySharedInfo.getInstance().getSessionID(currentLine, sessionIDPossition);
        				bw.append(currentLine);
        				bw.newLine();
        				sessionIdList.add(sessionID);
        			}
        		} else if (!currentLine.contains("[")){
        			// Trying to identify the start of the exception
        			sessionID = FancySharedInfo.getInstance().getSessionID(currentLine, sessionIDPossition);
        			Iterator<String> iterator = sessionIdList.iterator();
        			while(iterator.hasNext()) {
        				if (iterator.next().equals(sessionID)) {
        					isSessionIdAlreadyFound = true;
        				}
        			}
        			if (isSessionIdAlreadyFound) {
        				bw.append(currentLine);
        				bw.newLine();
        			}
        		}
            } else if (currentLine.contains("Exception")){
            	isExceptionFound = true;
            	bw.append(currentLine);
            	bw.newLine();
            } else if(isExceptionFound) {
            	bw.append(currentLine);
            	bw.newLine();
            }
        }
        br.close();
        bw.flush();
        bw.close();
    }
}
