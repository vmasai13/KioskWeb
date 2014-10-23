package com.tcs.fancylog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.zip.GZIPInputStream;

@Component
public class LogDownloadTask {

	 @Autowired
	    private Properties environmentProperties;

	    private static Logger FANCY_LOG_DOWNLOADER = LoggerFactory.getLogger("fancylog-downloader");
	    
	    private static final String INPUT_GZIP_FILE = "D:/fancylog/fancylog-download/oldlogs/<virtualmachine>data_waslogs_gs_webkiosk<appinstance>-webkiosk_technical.log.<date_time>.gz";
	    
	    private static final String OUTPUT_FILE = "D:/fancylog/fancylog-download/logsextracted/<virtualmachine>data_waslogs_gs_webkiosk<appinstance><date_time>.txt";
	    private String[] vmArray;
	    public void performTask() {
	        // Start
	        // Read Config
	        FancySharedInfo.getInstance().setDownloadInProgress(true);
	        FANCY_LOG_DOWNLOADER.info("Download starts");
	        vmArray = environmentProperties.getProperty("nodes").split(";");
	        String instance = environmentProperties.getProperty("appinstance");
	        String templateDownloadURL = environmentProperties.getProperty("templatedownloadurl");
	        String templateLoginURL = environmentProperties.getProperty("templateLoginURL");
	        String loginUserId = environmentProperties.getProperty("loginuser");
	        String loginPwd = environmentProperties.getProperty("loginpwd");
	        String environment = environmentProperties.getProperty("environment");
	        String serverNumberArray[] = environmentProperties.getProperty("serverNumbers").split(";");
	        if (vmArray.length > 0 && instance != null && templateDownloadURL != null && templateLoginURL != null && loginPwd != null && loginUserId != null) {
	            // Prepare Plan
	            try {
	                File[] lstFile = getListOfFiles(environmentProperties.getProperty("downloadlocation"));
	                if (null != lstFile) {
		                for (File fileToDelete : lstFile) {
		                    fileToDelete.delete();
		                }
		             File[] extractedFile = getListOfFiles(environmentProperties.getProperty("extractedLocation"));
		             if (null != extractedFile) {
			                for (File fileToDelete : extractedFile) {
			                    fileToDelete.delete();
			                }
		             	}       
	                }
	                FANCY_LOG_DOWNLOADER.info("Removed files from download directory (if any)");
	                String lastHourStringPattern = getLogDate();
	                // Execute Plan
	                HttpClient httpClient = new DefaultHttpClient();
	                CookieStore cookieStore = new BasicCookieStore();
	                HttpContext httpContext = new BasicHttpContext();
	                httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	                HttpPost httppost = new HttpPost(templateLoginURL);
	                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	                nvps.add(new BasicNameValuePair("username", loginUserId));
	                nvps.add(new BasicNameValuePair("password", loginPwd));
	                nvps.add(new BasicNameValuePair("login-form-type", "pwd"));
	                httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	                HttpResponse response = httpClient.execute(httppost, httpContext);
	                EntityUtils.consume(response.getEntity());
	                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
	                    FANCY_LOG_DOWNLOADER.info("Into Sr");
	                else {
	                    FANCY_LOG_DOWNLOADER.error("failed into Sr");
	                    FancySharedInfo.getInstance().setLastTaskSuccessful(false);
	                    return;
	                }
	                for (int vmIndex = 0; vmIndex < 4; vmIndex++) {
	                    String dowloadURLVmTemplate = templateDownloadURL.replace("<virtualmachine>", vmArray[vmIndex]);
	                    dowloadURLVmTemplate = dowloadURLVmTemplate.replace("<environment>", environment);
	                    dowloadURLVmTemplate = dowloadURLVmTemplate.replace("<instance>", instance);
	                    dowloadURLVmTemplate = dowloadURLVmTemplate.replace("<date_time>", lastHourStringPattern);
	                    downloadAndSaveLogFile(dowloadURLVmTemplate, httpClient, httpContext, vmArray[vmIndex]);
	                    /*for (int serverIndex = 0; serverIndex < serverNumberArray.length; serverIndex++) {
	                        String doanloadURL = dowloadURLVmTemplate.replace("<servernumber>", serverNumberArray[serverIndex]);
	                        downloadAndSaveLogFile(doanloadURL, httpClient, httpContext);
	                    }*/
	                }
	                FancySharedInfo.getInstance().setLastTaskSuccessful(true);
	            } catch (UnsupportedEncodingException e) {
	                FANCY_LOG_DOWNLOADER.error("Technical Exception faced, ", e);
	                FancySharedInfo.getInstance().setLastTaskSuccessful(false);
	            } catch (ClientProtocolException e) {
	                FANCY_LOG_DOWNLOADER.error("Technical Exception faced", e);
	                FancySharedInfo.getInstance().setLastTaskSuccessful(false);
	            } catch (IOException e) {
	                FANCY_LOG_DOWNLOADER.error("Technical Exception faced", e);
	                FancySharedInfo.getInstance().setLastTaskSuccessful(false);
	            } catch (Exception e) {
	                FANCY_LOG_DOWNLOADER.error("Exception occured", e);
	                FancySharedInfo.getInstance().setLastTaskSuccessful(false);
	            }
	        } else {
	            FANCY_LOG_DOWNLOADER.info("Insuffecient configuration found");
	            FancySharedInfo.getInstance().setLastTaskSuccessful(false);
	        }
	        FANCY_LOG_DOWNLOADER.info("Download process completed.");
	        extractGZFile();
	        FancySharedInfo.getInstance().setDownloadInProgress(false);
	    }
	    
	    private void extractGZFile() {
	    	 byte[] buffer = new byte[1024];
	    	 String fileName = "";
	    	 String fileout = "";
	    	 
	         try{
	        	 for (int vmIndex = 0; vmIndex < 4; vmIndex++) {
	        		 fileName = INPUT_GZIP_FILE;
	        		 fileName = fileName.replace("<virtualmachine>", vmArray[vmIndex]);
	        		 fileName = fileName.replace("<date_time>", getLogDate());
	        		 String instance = environmentProperties.getProperty("appinstance");
	        		 fileName = fileName.replace("<appinstance>", instance);
	        		 System.out.println("filename"+fileName);
		        	 GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(fileName));
		        	 fileout = OUTPUT_FILE;
		        	 fileout = fileout.replace("<virtualmachine>", vmArray[vmIndex]);
		        	 fileout = fileout.replace("<date_time>", getLogDate());
		        	 fileout = fileout.replace("<appinstance>", instance);
		        	 FileOutputStream out = 
		                new FileOutputStream(fileout);
		     
		            int len;
		            while ((len = gzis.read(buffer)) > 0) {
		            	out.write(buffer, 0, len);
		            }
		     
		            gzis.close();
		        	out.close();
		     
		        	System.out.println("Done");
	        	 }
	     
	        }catch(IOException ex){
	           ex.printStackTrace();   
	        }
	    }

	    private void downloadAndSaveLogFile(String downloadURL, HttpClient httpClient, HttpContext httpContext, String node) throws ClientProtocolException, IOException {
	        httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	        String diskFileName = environmentProperties.getProperty("downloadlocation")+node + downloadURL.substring(downloadURL.indexOf("filename=/oldlogs/") + "filename=/oldlogs/".length(), downloadURL.indexOf("&app=") - "&bus=gs".length());
	        HttpGet httpGet = new HttpGet(downloadURL);
	        System.out.println("download url...: "+ downloadURL);
	        InputStream inputStream = null;
	        HttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
	        inputStream = httpResponse.getEntity().getContent();
	        File diskFile = new File(diskFileName);
	        saveFileContent(inputStream, diskFile);
	    }

	    private void saveFileContent(InputStream isGz, File targetGzipFile2) throws IOException {
	        OutputStream out = null;
	        FANCY_LOG_DOWNLOADER.info("Downloading file... {}", targetGzipFile2.getName());
	        try {
	            out = new FileOutputStream(targetGzipFile2);
	            byte[] buf = new byte[1024];
	            int len;
	            while ((len = isGz.read(buf)) != -1) {
	                out.write(buf, 0, len);
	            }
	            out.close();
	            if (null != isGz) {
	                isGz.close();
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	            throw ex;
	        } finally {
	            // Close the file and stream
	            if (isGz != null) {
	                try {
	                    isGz.close();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	            if (out != null) {
	                try {
	                    out.close();
	                } catch (IOException ex) {
	                }
	            }
	        }
	    }

	    private String getLastHourDatePattern() {
	        Calendar calendar = Calendar.getInstance();
	        StringBuffer sbf = new StringBuffer();
	        calendar.add(Calendar.HOUR_OF_DAY, -Integer.parseInt(environmentProperties.getProperty("PreviousHourIndexIncrementor")));
	        sbf.append(calendar.get(Calendar.YEAR)).append("-").append(StringUtils.leftPad("" + (calendar.get(Calendar.MONTH) + 1), 2, "0")).append("-").append(StringUtils.leftPad("" + calendar.get(Calendar.DATE), 2, "0")).append("-")
	                .append(StringUtils.leftPad("" + calendar.get(Calendar.HOUR_OF_DAY), 2, "0"));
	        return sbf.toString();
	    }
	    
	    private String getLogDate() {
	    	Date date1 = Calendar.getInstance().getTime();
			String month = String.valueOf(date1.getMonth()+1);
			String day = String.valueOf(date1.getDate());
			String year = String.valueOf(Calendar.getInstance().getWeekYear());
	    	String date = year;
			if(month.length()>1) {
				date= date+month;
			} else {
				date= date+"0"+month;
			}
			
			if(day.length()>1) {
				date= date+day;
			} else {
				date= date+"0"+day;
			}
	    	return date;
	    }

	    private static File[] getListOfFiles(String directoryPath) {
	        File folder = new File(directoryPath);
	        File[] listOfFiles = folder.listFiles();
	        return listOfFiles;
	    }

}
