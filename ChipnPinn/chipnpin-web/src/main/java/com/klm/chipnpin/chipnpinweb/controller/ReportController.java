package com.klm.chipnpin.chipnpinweb.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.klm.chipnpin.chipnpinweb.model.CreditCardReportModel;
import com.klm.chipnpin.chipnpinweb.model.ReportModel;
import com.klm.chipnpin.chipnpinweb.services.RetrieveReportDetails;
import com.klm.chipnpin.chipnpinweb.util.ChipnPinResources;
import com.klm.chipnpin.chipnpinweb.util.ChipnpinConstant;

@Controller
public class ReportController {
	
    @Autowired
    RetrieveReportDetails retrieveReportDetails;
    // For getting the resources from property file
    @Autowired
    ChipnPinResources chipnPinResources;
    
    @RequestMapping("/home")
    public String mainReport() {
        return "kioskMetricsHome";
    }

    /*
     * @RequestMapping("/amsKiosks") public ModelAndView kioskAMS_IBM_Statistics() throws FileNotFoundException, IOException { ModelAndView model = new ModelAndView (); Properties prop = new Properties(); prop.load(new
     * FileInputStream("D:\\chip n pin\\cassandrabatchici\\cassandrabatchici\\src\\main\\resources\\kioskids.properties"));
     * 
     * return model;
     * 
     * }
     */
    /*private ModelAndView filterKacResponseStatus(List<KacReportModel> reportModel, ModelAndView model) {
        Date dateInString;
        Set<Date> dates = new HashSet<Date>();
        Iterator<KacReportModel> reports = reportModel.iterator();
        while (reports.hasNext()) {
            KacReportModel report = (KacReportModel) reports.next();
            dateInString = report.getReportModel().getDate();
            report.setDate(dateInString);
            dates.add(report.getReportModel().getDate());
        }
        Map<String, List<Integer>> responseMap = new HashMap<String, List<Integer>>();
        Iterator<Date> dateItr = dates.iterator();
        while (dateItr.hasNext()) {
            List<Integer> counters = new ArrayList<Integer>();
            int KacCount = 0;
            Date date = (Date) dateItr.next();
            for (KacReportModel report : reportModel) {
                if (date.equals(report.getReportModel().getDate())) {
                    KacCount++;
                }
            }
            counters.add(KacCount);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String dateToGraph = calendar.get(Calendar.YEAR) + ", " + calendar.get(Calendar.MONTH) + ", " + calendar.get(Calendar.DAY_OF_MONTH);
            responseMap.put(dateToGraph, counters);
        }
        model.addObject("jsonObject", responseMap);
        return model;
    }*/

    /**
     * Controller for Credit card customized search
     * @param kioskId
     * @param fromDate
     * @param toDate
     * @return
     * @throws Exception
     */
    @RequestMapping("/CreditCardSwipeResult")
    public ModelAndView creditCardSwipeResult(@RequestParam("kioskId")
    String kioskId, @RequestParam("fromPayment")
    String fromDate, @RequestParam("toPayment")
    String toDate) throws Exception {
        ModelAndView model = new ModelAndView();
     // Setting the configurable date, if "From" (or) "To" Date is empty
    	if(fromDate.isEmpty()) {
    		fromDate = chipnPinResources.getDefaultFromDate();
    	}
    	if (toDate.isEmpty()) {
    		toDate = chipnPinResources.getDefaultToDate();
    	}
        List<CreditCardReportModel> reportModel = retrieveReportDetails.getCreditCardReportModelForAllKiosk(kioskId.trim(), fromDate, toDate);
        model.addObject("jsonObject", reportModel);
        model.setViewName("CreditCardSwipeResult");
        return model;
    }
    
    /**
     * Controller for Credit card overview
     * @return
     * @throws Exception
     */
    @RequestMapping("/CreditCardSwipeOverview")
    public ModelAndView CreditCardSwipeOverview() throws Exception {
        ModelAndView model = new ModelAndView();
        // Setting the configurable date, if "From" (or) "To" Date is empty
		String fromDate = chipnPinResources.getDefaultFromDate();
		String toDate = chipnPinResources.getDefaultToDate();
        List<CreditCardReportModel> reportModel = retrieveReportDetails.getCreditCardReportModelForAllKiosk("", fromDate, toDate);
        createCreditCardResultModel(reportModel, model, fromDate);
        // reference - kacResultTable
        model.setViewName("creditCardResultOverview");
        return model;
    }

    /*
     * @RequestMapping("/swipeResult") public @ResponseBody List<CreditCardOutputModel> swipeResult() throws Exception { List<CreditCardOutputModel> outputModel = new ArrayList<CreditCardOutputModel>();
     * List<CreditCardReportModel> reportModel = retrieveReportDetails.getCreditCArdReportModelForAllKiosk(); System.out.println("hihih"); return createCreditCardModel(reportModel, outputModel); }
     * 
     * private List<CreditCardOutputModel> createCreditCardModel(List<CreditCardReportModel> reportModel, List<CreditCardOutputModel> outputModelList) { Set<String> dates = new HashSet<String>();
     * Iterator<CreditCardReportModel> reports = reportModel.iterator(); while (reports.hasNext()) { CreditCardReportModel report = (CreditCardReportModel) reports.next(); //dates.add(report.getDate());
     * } Iterator<String> dateItr = dates.iterator(); while (dateItr.hasNext()) { CreditCardOutputModel model = null; int countSuccess = 0; String date = (String) dateItr.next(); for(CreditCardReportModel report :
     * reportModel) { model = new CreditCardOutputModel();
     * 
     * if (date.equals(report.getDate()) && report.getPaymentStatusCode().equals("100")) { countSuccess++; } model.setDate(date); model.setSuccessCount(countSuccess);
     * 
     * } outputModelList.add(model);
     * 
     * } return outputModelList; }
     */
    /*@RequestMapping("/StatisticsAMSorCDG")
    public ModelAndView StatisticsAMSorCDG(@RequestParam("station")
    String station) throws FileNotFoundException, IOException {
        ModelAndView model = new ModelAndView();
        Properties prop = new Properties();
        String[] result = null;
        List<String> kioskIdsList = new ArrayList<String>();
        prop.load(new FileInputStream("C:/develop/code/Reporting/cassandrabatchici/src/main/resources/kioskids.properties"));
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
    }*/

    @RequestMapping("/compareNGK")
    public ModelAndView compareNGKAndBLSCDG() throws FileNotFoundException, IOException {
        ModelAndView model = new ModelAndView();
        /*
         * Map<String, List<Float>> responseMap = new HashMap<String, List<Float>>(); List<String> kioskIdsListNGK = new ArrayList<String>(); List<String> kioskIdsListCDG = new ArrayList<String>(); Properties prop = new
         * Properties(); prop.load(new FileInputStream("D:\\chip n pin\\cassandrabatchici\\cassandrabatchici\\src\\main\\resources\\kioskids.properties")); String kioskIdsNGK = prop.getProperty("kioskIds"); String[]
         * resultNGK = kioskIdsNGK.split(","); for (int x = 0; x < resultNGK.length; x++) { kioskIdsListNGK.add(resultNGK[x]); } String kioskIdsCDG = prop.getProperty("kioskcdgIds"); String[] resultCDG =
         * kioskIdsCDG.split(","); for (int x = 0; x < resultCDG.length; x++) { kioskIdsListCDG.add(resultCDG[x]); }
         * 
         * List<ReportModel> reportModelNGK = retrieveReportDetails.getReportModelForNGKKiosks(kioskIdsListNGK); List<ReportModel> reportModelCDG = retrieveReportDetails.getReportModelForNGKKiosks(kioskIdsListCDG);
         * Set<String> datesNGK = new HashSet<String>(); Set<String> datesCDG = new HashSet<String>();
         * 
         * Iterator<ReportModel> reportsNGK = reportModelNGK.iterator(); while (reportsNGK.hasNext()) { String dateInString = ""; ReportModel report = (ReportModel) reportsNGK.next(); dateInString = report.getDate();
         * report.setDate(dateInString.replace('-', ',')); datesNGK.add(report.getDate()); } Iterator<ReportModel> reportsCDG = reportModelCDG.iterator(); while (reportsCDG.hasNext()) { String dateInString = "";
         * ReportModel report = (ReportModel) reportsCDG.next(); dateInString = report.getDate(); report.setDate(dateInString.replace('-', ',')); datesCDG.add(report.getDate()); }
         * 
         * Iterator<String> dateItrNGK = datesNGK.iterator(); while (dateItrNGK.hasNext()) { String date = (String) dateItrNGK.next(); List<Float> counters = new ArrayList<Float>(); float countSuccessNGK = 0; float
         * totalAttempsNGK = 0; for(ReportModel report : reportModelNGK) //use for-each loop { if (date.equals(report.getDate())) { totalAttempsNGK++; } if (date.equals(report.getDate()) &&
         * report.getResponseCode().equals("0000")) { countSuccessNGK++; }
         * 
         * }
         * 
         * float totalAttempsCDG = 0; float countSuccessCDG = 0; for(ReportModel report : reportModelCDG) //use for-each loop { if (date.equals(report.getDate())) { totalAttempsCDG++; } if (date.equals(report.getDate())
         * && report.getResponseCode().equals("0000")) { countSuccessCDG++; }
         * 
         * } if (totalAttempsNGK > 0.0) { counters.add((countSuccessNGK/totalAttempsNGK)*100); } else { counters.add((float) 0.0); } if (totalAttempsCDG > 0.0) { counters.add((countSuccessCDG/totalAttempsCDG)*100); }
         * else { counters.add((float) 0.0); } date = date.substring(0, 5)+" "+String.valueOf((Integer.parseInt(date.substring(6, 7))-1))+" "+date.substring(7,10); responseMap.put(date, counters);
         * //System.out.println("date:"+new Date(date).toString()); }
         * 
         * model.addObject("jsonObject", responseMap);
         */
        model.setViewName("compare");
        return model;
    }

/*    @RequestMapping("/result")
    public ModelAndView result() throws Exception {
        ModelAndView model = new ModelAndView();
        List<ReportModel> reportModel = retrieveReportDetails.getReportModelForAllKiosk();
        model = filterResponseStatus(reportModel, model);
        model.setViewName("result");
        return model;
    }*/

    @RequestMapping("/errorCodes")
    public ModelAndView errorCodes() throws Exception {
        List<String> kioskIdsList = new ArrayList<String>();
        Properties props = new Properties();
        props.load(new FileInputStream("C:/develop/code/Reporting/cassandrabatchici/src/main/resources/kioskids.properties"));
        String kioskIds = props.getProperty("kioskIdsNGKAMS");
        String[] result = kioskIds.split(",");
        for (int x = 0; x < result.length; x++) {
            kioskIdsList.add(result[x]);
        }
        ModelAndView model = new ModelAndView();
        List<ReportModel> reportModel = retrieveReportDetails.getReportModelForNGKKiosks(kioskIdsList);
        model = filterErrorCodes(reportModel, model);
        model.setViewName("erroCodeView");
        return model;
    }

    private ModelAndView filterErrorCodes(List<ReportModel> reportModel, ModelAndView model) {
        Date dateInString;
        Set<Date> dates = new HashSet<Date>();
        Iterator<ReportModel> reports = reportModel.iterator();
        while (reports.hasNext()) {
            ReportModel report = (ReportModel) reports.next();
            dateInString = report.getDate();
            report.setDate(dateInString);
            dates.add(report.getDate());
        }
        Map<String, List<Integer>> responseMap = new HashMap<String, List<Integer>>();
        Iterator<Date> dateItr = dates.iterator();
        while (dateItr.hasNext()) {
            List<Integer> counters = new ArrayList<Integer>();
            int errorCode0004 = 0;
            int errorCode0014 = 0;
            int errorCode0017 = 0;
            int errorCode0018 = 0;
            Date date = (Date) dateItr.next();
            for (ReportModel report : reportModel) {
                if (date.equals(report.getDate()) && report.getResponseError().equals("0004")) {
                    errorCode0004++;
                }
                if (date.equals(report.getDate()) && report.getResponseError().equals("0014")) {
                    errorCode0014++;
                }
                if (date.equals(report.getDate()) && (report.getResponseError().equals("0017"))) {
                    errorCode0017++;
                }
                if (date.equals(report.getDate()) && (report.getResponseError().equals("0018"))) {
                    errorCode0018++;
                }
            }
            counters.add(errorCode0004);
            counters.add(errorCode0014);
            counters.add(errorCode0017);
            counters.add(errorCode0018);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String dateToGraph = calendar.get(Calendar.YEAR) + ", " + calendar.get(Calendar.MONTH) + ", " + calendar.get(Calendar.DAY_OF_MONTH);
            responseMap.put(dateToGraph, counters);
        }
        model.addObject("jsonObject", responseMap);
        return model;
    }

    private ModelAndView createCreditCardResultModel(List<CreditCardReportModel> reportModel, ModelAndView model, String fromDate) {
        Map responseMap = new HashMap();
    	List<Integer> counters = new ArrayList<Integer>();
    	int countSold = 0;
    	int countFailed = 0;
    	int countRejected = 0;
    	int countRejectedLastAttempt = 0;
    	int countUnknown = 0;
    	
    	for(CreditCardReportModel report : reportModel) {
    		if(report.getPaymentStatusName() != null) {
    			if (report.getPaymentStatusName().equals(ChipnpinConstant.SOLD)) {
    				countSold++;
        		} else if (report.getPaymentStatusName().equals(ChipnpinConstant.FAILED)) {
        			countFailed++;
        		} else if (report.getPaymentStatusName().equals(ChipnpinConstant.REJECTED)) {
        			countRejected++;
        		} else if (report.getPaymentStatusName().equals(ChipnpinConstant.REJECTED_LAST_ATTEMPT)) {
        			countRejectedLastAttempt++;
        		} else {
        			countUnknown++;
        		}
    		}
    	} 
//    	responseMap.put("Date", fromDate);
    	responseMap.put(ChipnpinConstant.SOLD, countSold);
    	responseMap.put(ChipnpinConstant.FAILED, countFailed);
    	responseMap.put(ChipnpinConstant.REJECTED, countRejected);
    	responseMap.put(ChipnpinConstant.REJECTED_LAST_ATTEMPT, countRejectedLastAttempt);
    	responseMap.put(ChipnpinConstant.UNKNOWN, countUnknown);
    	
    	/*    	Calendar calendar = Calendar.getInstance(); //07/08/2014
        calendar.setTime(ChipnpinCommonUtil.stringToDate(fromDate));
    	String dateToGraph = calendar.get(Calendar.YEAR) + ", " + calendar.get(Calendar.MONTH) + ", " + calendar.get(Calendar.DAY_OF_MONTH);
		responseMap.put(dateToGraph, counters);
		
		Calendar calendar1 = Calendar.getInstance(); //07/08/2014
        calendar1.setTime(ChipnpinCommonUtil.stringToDate("07/09/2014"));
    	String dateToGraph1 = calendar1.get(Calendar.YEAR) + ", " + calendar1.get(Calendar.MONTH) + ", " + calendar1.get(Calendar.DAY_OF_MONTH);
		counters.add(countSuccess+1); 	 
    	counters.add(countFailed+1);
    	responseMap.put(dateToGraph1, counters);
*/    	
        model.addObject("jsonObject", responseMap);
    	return model;
    }

    /*
     * @RequestMapping(value = "/add", method = RequestMethod.GET) public ModelAndView hi(@RequestParam("date") String date) throws Exception { ModelAndView model = new ModelAndView (); List<ReportModel> reportModel =
     * retrieveReportDetails.getReportModelForTheDate(date);
     * 
     * return model; }
     */
    /*
     * @RequestMapping(value = "/input", method = RequestMethod.GET) public String redirectToApp(@RequestParam("date") String date, Model model) throws Exception { List<ReportModel> reportModel =
     * retrieveReportDetails.getReportModelForTheDate(date); return "dashboard"; }
     */
}
