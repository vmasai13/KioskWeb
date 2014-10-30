package com.tcs.klm.fancylog.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.tcs.klm.fancylog.utils.FancySharedInfo;

public class ProcessSoap {
	
	public void processSoapLogs(String fileName, String outputFile,String sessionIDPossition, String identify) throws IOException {
		String year = Calendar.getInstance().get(Calendar.YEAR)+"";
    	String sessionID = null;
    	List<String> sessionIdList = new ArrayList<String>();
    	File file = new File(fileName);
    	BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFile)));
    	BufferedReader br = new BufferedReader(new FileReader(file));
        String currentLine = null;
        while ((currentLine = br.readLine()) != null) {
        	if(currentLine.contains("27lIbqis1Yevy3-aF_2-YOA e32s")) {
        		System.out.println(currentLine);
        	}
        	if (currentLine.startsWith(year)) {
        		boolean isSessionIdAlreadyFound = false;
        		if(currentLine.contains(identify)) {
            		Iterator<String> iterator = sessionIdList.iterator();
        			while(iterator.hasNext()) {
        				bw.append(currentLine);
        				bw.newLine();
        				isSessionIdAlreadyFound = true;
        				iterator.next();
        			}
        			if (!isSessionIdAlreadyFound) {
        				sessionID = FancySharedInfo.getInstance().getSessionID(currentLine, sessionIDPossition);
        				bw.append(currentLine);
        				bw.newLine();
        				sessionIdList.add(sessionID);
        			}
        		}
            } else {
            	System.out.println("Line not parsed.. Need to check it !!!");
            }
        }
        br.close();
        bw.flush();
        bw.close();
    
	}

}
