package com.tcs.klm.fancylog.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.tcs.klm.fancylog.task.LogAnalyzerTask;

public class FancySharedInfo {
    private static final Logger APPLICATION_LOGGER = LoggerFactory.getLogger(LogAnalyzerTask.class);

    private boolean isLastTaskSuccessful;
    private boolean isDownloadInProgress;
    private boolean isAnalysisInProgress;
    private Calendar calendar = null;
    private List<String> faildHyperLinks = null;

    private static FancySharedInfo fancySharedInfo;

    private FancySharedInfo() {
        isLastTaskSuccessful = true;
        isDownloadInProgress = false;
        isAnalysisInProgress = false;
    }

    public boolean isDownloadInProgress() {
        return isDownloadInProgress;
    }

    public void setDownloadInProgress(boolean isDownloadInProgress) {
        this.isDownloadInProgress = isDownloadInProgress;
    }

    public boolean isAnalysisInProgress() {
        return isAnalysisInProgress;
    }

    public void setAnalysisInProgress(boolean isAnalysisInProgress) {
        this.isAnalysisInProgress = isAnalysisInProgress;
    }

    public static FancySharedInfo getInstance() {
        if (fancySharedInfo == null) {
            fancySharedInfo = new FancySharedInfo();
        }
        return fancySharedInfo;
    }

    public boolean isLastTaskSuccessful() {
        return isLastTaskSuccessful;
    }

    public void setLastTaskSuccessful(boolean isLastTaskSuccessful) {
        this.isLastTaskSuccessful = isLastTaskSuccessful;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void incrementCalenderByOneHr() {
        calendar.add(Calendar.HOUR_OF_DAY, 1);
    }

    public HttpClient getAuthenticatedHttpClient(String strLogonURL, String strLogonUserId, String strLogonPassword) {
        HttpClient httpClient = new HttpClient();
        int code = 0;
        if (strLogonURL != null && strLogonUserId != null && strLogonPassword != null) {
            PostMethod postMethod = new PostMethod(strLogonURL);
            postMethod.setParameter("username", strLogonUserId);
            postMethod.setParameter("password", strLogonPassword);
            postMethod.setParameter("login-form-type", "pwd");
            try {
                code = httpClient.executeMethod(postMethod);
                APPLICATION_LOGGER.info("Login Http Status {} ", code);
            }
            catch (HttpException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            APPLICATION_LOGGER.error("invalid logon configurations found");
        }
        if (code != 200) {
            APPLICATION_LOGGER.error("Unable to login to server, Http Status Code = {}", code);
            httpClient = null;
        }
        return httpClient;
    }

    public String getDateFormat(Calendar calendar) {
        Date time = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH");
        String dateformat = dateFormat.format(time);
        return dateformat;
    }
    
    public String getDateFromData(String lineText) {
        String strs[] = lineText.split(" ");
        return strs[0];
    }

    public String getDay(Calendar calendar) {
        Date time = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateformat = dateFormat.format(time);
        return dateformat;
    }

    public String getServiceName(String xmlPayload) {
        String serviceName = null;
        int bodyIndex = xmlPayload.indexOf("Body>") + 5;
        String serviceNamePartOne = xmlPayload.substring(bodyIndex, xmlPayload.indexOf(" ", bodyIndex));
        String serviceNamePartTwo = null;
        if (!serviceNamePartOne.contains(">")) {
            serviceNamePartTwo = serviceNamePartOne.substring(serviceNamePartOne.indexOf(":") + 1);
            serviceName = serviceNamePartTwo;
        }
        else {
            serviceName = serviceNamePartOne;
        }
        if (serviceName != null && serviceName.contains("<")) {
            serviceName = serviceName.substring(serviceName.indexOf("<") + 1);
        }
        if (serviceName != null && serviceName.contains("Request")) {
            serviceName = serviceName.substring(0, serviceName.indexOf("Request"));
        }
        else if (serviceName != null && serviceName.contains("Response")) {
            serviceName = serviceName.substring(0, serviceName.indexOf("Response"));
        }
        else if (serviceName != null && serviceName.contains("_IN")) {
            serviceName = serviceName.substring(0, serviceName.indexOf("_IN"));
        }
        else if (serviceName != null && serviceName.contains("_OUT")) {
            serviceName = serviceName.substring(0, serviceName.indexOf("_OUT"));
        }
        return serviceName;
    }

    /*public String getActualSessionIdentifier(String sessionID) {
        int counter = sessionID.length()-1;
        char lastIndexValue = sessionID.charAt(counter);
        boolean gotSessionId = false;
        while (!gotSessionId) {
        	if (Character.isAlphabetic(lastIndexValue)) {
        		gotSessionId = true;
        		return sessionID;
        	}
        	sessionID = sessionID.substring(0, counter);
        	counter = counter - 1;
        	lastIndexValue = sessionID.charAt(counter);
        }
        return sessionID;
    }*/
    
    public String getActualSessionIdentifier(String sessionID) {
    	String strs[] = sessionID.split(" ");
        return strs[0]+"e";
    }
    
    public String getFullSessionIdentifier(String lineText, String sessionIDPossition) {
        String strs[] = lineText.split(" ");
        if (strs[Integer.valueOf(sessionIDPossition)+1].startsWith("e")) {
        	return strs[Integer.valueOf(sessionIDPossition)] + " " +strs[Integer.valueOf(sessionIDPossition)+1];
        } else {
        	return null;
        }
    }
    
    // Getting the integer position in the second session identifier
    public int getSessionIdentifierCounter(String sessionID) {
    	int sessionIdentifierValue = 0;
    	String strs[] = sessionID.split(" ");
    	String secondIdentifier = strs[1];
    	if (Utils.isStringNotEmpty(secondIdentifier)) {
    		int charIndex = secondIdentifier.indexOf("s");
    		sessionIdentifierValue = Integer.parseInt(secondIdentifier.substring(1, charIndex));
    	}
    	return sessionIdentifierValue;
    }
    
    public String getKioskId(String lineText, String sessionIDPossition) {
    	String kioskId = new String();
        String strs[] = lineText.split(" ");
        kioskId = strs[Integer.valueOf(sessionIDPossition)+1];
        return kioskId;
    }

    public String getDate(String line) {
        String dateString = null;
        Calendar calendar = Calendar.getInstance();
        if (line.startsWith(calendar.get(Calendar.YEAR) + "")) {
            String strs[] = line.split(" ");
            dateString = strs[0] + " " + strs[1];// line.substring(24, 47);
        }
        return dateString;
    }

    public List<String> getFaildHyperLinks() {
        return faildHyperLinks;
    }

    public void setFaildHyperLinks(String faildHyperLink) {
        if (faildHyperLinks == null) {
            faildHyperLinks = new ArrayList<String>();
        }
        faildHyperLinks.add(faildHyperLink);
    }

    public void clearFaildHyperLinks() {
        faildHyperLinks = null;
    }
}
