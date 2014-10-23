package com.klm.chipnpin.chipnpinweb.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.klm.chipnpin.chipnpinweb.model.ChipNPinTreand;
import com.klm.chipnpin.chipnpinweb.model.ReportModel;
import com.klm.chipnpin.chipnpinweb.services.RetrieveReportDetails;

@Controller
public class ReportController {

	public static String kioskId_Properties = "C:/Users/X075668/Desktop/Desktop/CK/NGK Reporting/cassandrabatchici/src/main/resources/kioskids.properties";
	@Autowired
	RetrieveReportDetails retrieveReportDetails;
	
	@RequestMapping("/home")
	public String mainReport() {
		
		return "dashboard";
	}
	
	/*@RequestMapping("/amsKiosks")
	public ModelAndView kioskAMS_IBM_Statistics() throws FileNotFoundException, IOException {
		ModelAndView model = new ModelAndView ();
		Properties prop = new Properties();
		prop.load(new FileInputStream("D:\\chip n pin\\cassandrabatchici\\cassandrabatchici\\src\\main\\resources\\kioskids.properties"));
		
		return model;
		
	}*/
	@RequestMapping("/StatisticsAMSorCDG")
	public ModelAndView StatisticsAMSorCDG(@RequestParam("station") String station) throws FileNotFoundException, IOException {
		ModelAndView model = new ModelAndView ();
		Properties prop = new Properties();
		String[] result = null;
		List<String> kioskIdsList = new ArrayList<String>();
		prop.load(new FileInputStream(kioskId_Properties));
		String kioskIdsNGKAMS = prop.getProperty("kioskIdsNGKAMS");
		String kioskIdsNGKCDG = prop.getProperty("kioskIdsNGKCDG");
		if ("AMS".equals(station)) {
			result = kioskIdsNGKAMS.split(",");
		} 
		if ("CDG".equals(station)) {
			result = kioskIdsNGKCDG.split(",");
		}
		for (int x = 0; x < result.length; x++) {
			kioskIdsList.add(result[x]);
		}
		
		List<ReportModel> reportModel = retrieveReportDetails.getReportModelForNGKKiosks(kioskIdsList);
		model = filterResponseStatus(reportModel, model);
		model.setViewName("result");
		return model;		
	}
	
	@RequestMapping("/compareNGK")
	public ModelAndView compareNGKAndBLSCDG() throws FileNotFoundException, IOException {
		ModelAndView model = new ModelAndView ();
		Map<String, List<Float>> responseMap = new HashMap<String, List<Float>>();
		List<String> kioskIdsListNGK = new ArrayList<String>();
		List<String> kioskIdsListCDG = new ArrayList<String>();
		Properties prop = new Properties();
		prop.load(new FileInputStream(kioskId_Properties));
		String kioskIdsNGK = prop.getProperty("kioskIdsNGKSeries0");
		String[] resultNGK = kioskIdsNGK.split(",");
		for (int x = 0; x < resultNGK.length; x++) {
			kioskIdsListNGK.add(resultNGK[x]);
		}
		String kioskIdsCDG = prop.getProperty("kioskcdgIds");
		String[] resultCDG = kioskIdsCDG.split(",");
		for (int x = 0; x < resultCDG.length; x++) {
			kioskIdsListCDG.add(resultCDG[x]);
		}
		
		List<ReportModel> reportModelNGK = retrieveReportDetails.getReportModelForNGKKiosks(kioskIdsListNGK);
		List<ReportModel> reportModelCDG = retrieveReportDetails.getReportModelForNGKKiosks(kioskIdsListCDG);
		Set<String> datesNGK = new HashSet<String>();
		Set<String> datesCDG = new HashSet<String>();
		
		Iterator<ReportModel> reportsNGK = reportModelNGK.iterator();
		 while (reportsNGK.hasNext()) {
			 	String dateInString = "";
	        	ReportModel report = (ReportModel) reportsNGK.next();
	        	dateInString = report.getDate();
	        	report.setDate(dateInString.replace('-', ','));
	        	datesNGK.add(report.getDate());
	        }
		Iterator<ReportModel> reportsCDG = reportModelCDG.iterator();
		 while (reportsCDG.hasNext()) {
			 String dateInString = "";
	        	ReportModel report = (ReportModel) reportsCDG.next();
	        	dateInString = report.getDate();
	        	report.setDate(dateInString.replace('-', ','));
	        	datesCDG.add(report.getDate());
	        }
		 
		 Iterator<String> dateItrNGK = datesNGK.iterator();
	        while (dateItrNGK.hasNext()) {
	        	String date = (String) dateItrNGK.next();
	        	List<Float> counters = new ArrayList<Float>();
            	float countSuccessNGK = 0;
            	float totalAttempsNGK = 0;
            	for(ReportModel report : reportModelNGK) //use for-each loop
	        	{
            		if (date.equals(report.getDate())) {
            			totalAttempsNGK++;
            		}
            		 if (date.equals(report.getDate()) && report.getResponseCode().equals("0000")) {
            			 countSuccessNGK++;
		      	    	}
            		  
	        	}
            	
	           	float totalAttempsCDG = 0;
            	float countSuccessCDG = 0;
            	for(ReportModel report : reportModelCDG) //use for-each loop
	        	{
            		if (date.equals(report.getDate())) {
            			totalAttempsCDG++;
            		}
            		 if (date.equals(report.getDate()) && report.getResponseCode().equals("0000")) {
            			 countSuccessCDG++;
		      	    	}
            		  
	        	}
            	if (totalAttempsNGK > 0.0) {
            		counters.add((countSuccessNGK/totalAttempsNGK)*100);
            	} else {
             		counters.add((float) 0.0);
             	}
            	if (totalAttempsCDG > 0.0) {
            		counters.add((countSuccessCDG/totalAttempsCDG)*100);
            	}  else {
            		counters.add((float) 0.0);
            	}
            	date = date.substring(0, 5)+" "+String.valueOf((Integer.parseInt(date.substring(6, 7))-1))+" "+date.substring(7,10);
            	responseMap.put(date, counters);
            	//System.out.println("date:"+new Date(date).toString());
	        }
	        
	        model.addObject("jsonObject", responseMap);
			model.setViewName("compare"); 
	     return model;
	}
	
	
	
	@RequestMapping("/kioskIdsNGKSeries0")
	public ModelAndView statisticsOFNGKKiosk() throws FileNotFoundException, IOException {
		List<String> kioskIdsList = new ArrayList<String>();
		Properties props = new Properties();
		props.load(new FileInputStream(kioskId_Properties));
		String kioskIds = props.getProperty("kioskIdsNGKSeries0");
		String[] result = kioskIds.split(",");
		for (int x = 0; x < result.length; x++) {
			kioskIdsList.add(result[x]);
		}
		ModelAndView model = new ModelAndView ();
		List<ReportModel> reportModel = retrieveReportDetails.getReportModelForNGKKiosks(kioskIdsList);
		model = filterResponseStatus(reportModel, model);
		model.setViewName("result");
		return model;
	}
	
	@RequestMapping("/kioskIdsNGKSeries1")
	public ModelAndView kioskIdsNGKSeries1() throws FileNotFoundException, IOException {
		List<String> kioskIdsList = new ArrayList<String>();
		Properties props = new Properties();
		props.load(new FileInputStream(kioskId_Properties));
		String kioskIds = props.getProperty("kioskIdsNGKSeries1");
		String[] result = kioskIds.split(",");
		for (int x = 0; x < result.length; x++) {
			kioskIdsList.add(result[x]);
		}
		ModelAndView model = new ModelAndView ();
		List<ReportModel> reportModel = retrieveReportDetails.getReportModelForNGKKiosks(kioskIdsList);
		model = filterResponseStatus(reportModel, model);
		model.setViewName("result");
		return model;
	}
	
	@RequestMapping("/kioskIdsNGKSeries1_AMS")
	public ModelAndView kioskIdsNGKSeries1_AMS() throws FileNotFoundException, IOException {
		List<String> kioskIdsList = new ArrayList<String>();
		Properties props = new Properties();
		props.load(new FileInputStream(kioskId_Properties));
		String kioskIds = props.getProperty("kioskIdsNGKSeries1_AMS");
		String[] result = kioskIds.split(",");
		for (int x = 0; x < result.length; x++) {
			kioskIdsList.add(result[x]);
		}
		ModelAndView model = new ModelAndView ();
		List<ReportModel> reportModel = retrieveReportDetails.getReportModelForNGKKiosks(kioskIdsList);
		model = filterResponseStatus(reportModel, model);
		model.setViewName("result");
		return model;
	}
	
	@RequestMapping("/kioskIdsNGKSeries1_CDG")
	public ModelAndView kioskIdsNGKSeries1_CDG() throws FileNotFoundException, IOException {
		List<String> kioskIdsList = new ArrayList<String>();
		Properties props = new Properties();
		props.load(new FileInputStream(kioskId_Properties));
		String kioskIds = props.getProperty("kioskIdsNGKSeries1_CDG");
		String[] result = kioskIds.split(",");
		for (int x = 0; x < result.length; x++) {
			kioskIdsList.add(result[x]);
		}
		ModelAndView model = new ModelAndView ();
		List<ReportModel> reportModel = retrieveReportDetails.getReportModelForNGKKiosks(kioskIdsList);
		model = filterResponseStatus(reportModel, model);
		model.setViewName("result");
		return model;
	}
	
	@RequestMapping("/perKiosk")
	public ModelAndView perKiosk(@RequestParam("name") String kioskId) {

		ModelAndView model = new ModelAndView ();
		List<ReportModel> reportModel = retrieveReportDetails.getReportModelperKiosk(kioskId);
		model = filterResponseStatus(reportModel, model);
		model.setViewName("result");
		return model;
	
	}
	
	@RequestMapping("/result")
	public ModelAndView result() throws Exception {
		ModelAndView model = new ModelAndView ();
		List<ReportModel> reportModel = retrieveReportDetails.getReportModelForAllKiosk();
		model = filterResponseStatus(reportModel, model);
		model.setViewName("result");
		return model;
	}
	
	@RequestMapping("/errorCodes")
	public ModelAndView errorCodes() throws Exception {
		List<String> kioskIdsList = new ArrayList<String>();
		Properties props = new Properties();
		props.load(new FileInputStream(kioskId_Properties));
		String kioskIds = props.getProperty("kioskIdsNGKSeries0");
		String[] result = kioskIds.split(",");
		for (int x = 0; x < result.length; x++) {
			kioskIdsList.add(result[x]);
		}
		ModelAndView model = new ModelAndView ();
		List<ReportModel> reportModel = retrieveReportDetails.getReportModelForNGKKiosks(kioskIdsList);
		model = filterErrorCodes(reportModel, model);
		model.setViewName("erroCodeView");
		return model;
	}
	
private ModelAndView filterErrorCodes(List<ReportModel> reportModel,
			ModelAndView model) {
	String dateInString = "";
	Set<String> dates = new HashSet<String>();
	Iterator<ReportModel> reports = reportModel.iterator();
    while (reports.hasNext()) {
    	ReportModel report = (ReportModel) reports.next();
    	dateInString = report.getDate();
    	report.setDate(dateInString.replace('-', ','));
    	dates.add(report.getDate());
    }
   	Map<String, List<Integer>> responseMap = new HashMap<String, List<Integer>>();
   	Iterator<String> dateItr = dates.iterator();
    while (dateItr.hasNext()) {
        	List<Integer> counters = new ArrayList<Integer>();
     		int errorCode0004 = 0;
     		int errorCode0014 = 0;
     		int errorCode0017 = 0;
     		int errorCode0018 = 0;
        	 String date = (String) dateItr.next();
        	 
        	 for(ReportModel report : reportModel) {
            		 
		      		if (date.equals(report.getDate()) && report.getResponseError().equals("0004")) {
		      			errorCode0004++;
		      	   	}
		      		if (date.equals(report.getDate()) && report.getResponseError().equals("0014")) {
		      			errorCode0014++;
		  	    	}
		      		if (date.equals(report.getDate()) && (report.getResponseError().equals("0017"))) {
		      			errorCode0017++;
		  	    	}
		      		if (date.equals(report.getDate()) && (report.getResponseError().equals("0018") )) {
		      			errorCode0018++;
	  	    		}
	        	} 
        	 counters.add(errorCode0004); 
        	 counters.add(errorCode0014); 
        	 counters.add(errorCode0017); 
        	 counters.add(errorCode0018);
        	 date = date.substring(0, 5)+" "+String.valueOf((Integer.parseInt(date.substring(6, 7))-1))+" "+date.substring(7,10);
        	 responseMap.put(date, counters);
        	 
         }
	model.addObject("jsonObject", responseMap);
	
	return model;
	}

private ModelAndView filterResponseStatus(List<ReportModel> reportModel, ModelAndView model) {
		
		String dateInString = "";
		Set<String> dates = new HashSet<String>();
		Iterator<ReportModel> reports = reportModel.iterator();
        while (reports.hasNext()) {
        	ReportModel report = (ReportModel) reports.next();
        	dateInString = report.getDate();
        	report.setDate(dateInString.replace('-', ','));
        	dates.add(report.getDate());
        }
       	Map<String, List<Integer>> responseMap = new HashMap<String, List<Integer>>();
       	Iterator<String> dateItr = dates.iterator();
        while (dateItr.hasNext()) {
            	 List<Integer> counters = new ArrayList<Integer>();
            	int countSuccess = 0;
         		int errorCount0311 = 0;
         		int errorCount0310 = 0;
         		int errorCount0110 = 0;
         		int errorCount0100 = 0;
         		int totalAttempts = 0;
            	 String date = (String) dateItr.next();
            	 
            	 for(ReportModel report : reportModel) {
			      		if (date.equals(report.getDate()) && report.getResponseCode().equals("0000")) {
			      	    		countSuccess++;
			      	   	}
			      		if (date.equals(report.getDate()) && report.getResponseCode().equals("0311")) {
			      				errorCount0311++;
			  	    	}
			      		if (date.equals(report.getDate()) && (report.getResponseCode().equals("0310") || report.getResponseCode().equals("null"))) {
			      				errorCount0310++;
			  	    	}
			      		if (date.equals(report.getDate()) && (report.getResponseCode().equals("0100") )) {
//			      			System.out.println("kioskid 100" + report.getKioskId()+ "date : "+ report.getDate());
		      				errorCount0100++;
		  	    		}
			      		if (date.equals(report.getDate()) && (report.getResponseCode().equals("0110") )) {
//			      			System.out.println("kioskid 110" + report.getKioskId()+ "date : "+ report.getDate());
		      				errorCount0110++;
		  	    		}
			      		if (date.equals(report.getDate())) {
			      			totalAttempts++;
			      		}
		        	} 
            	 counters.add(countSuccess); 
            	 counters.add(errorCount0311); 
            	 counters.add(errorCount0310); 
            	 counters.add(errorCount0100);
            	 counters.add(errorCount0110);
            	 counters.add(totalAttempts); 
            	 date = date.substring(0, 5)+" "+String.valueOf((Integer.parseInt(date.substring(6, 7))-1))+" "+date.substring(7,10);
            	 responseMap.put(date, counters);
            	 
             }
		model.addObject("jsonObject", responseMap);
		
		return model;
		
	}
	/*@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView hi(@RequestParam("date") String date) throws Exception {
		ModelAndView model = new ModelAndView ();
		List<ReportModel> reportModel = retrieveReportDetails.getReportModelForTheDate(date);	

		return model;
	}*/
	/*@RequestMapping(value = "/input", method = RequestMethod.GET)
	public String redirectToApp(@RequestParam("date") String date, 
			Model model) throws Exception {
		List<ReportModel> reportModel = retrieveReportDetails.getReportModelForTheDate(date);	
		return "dashboard";
	}*/
}
