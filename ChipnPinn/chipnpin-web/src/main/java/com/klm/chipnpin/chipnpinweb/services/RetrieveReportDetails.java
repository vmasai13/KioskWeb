package com.klm.chipnpin.chipnpinweb.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.BillLogs;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.CreditcardServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.DvoLogs;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.KACServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.ServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository.ReportDetails;
import com.klm.chipnpin.chipnpinweb.model.CreditCardReportModel;
import com.klm.chipnpin.chipnpinweb.model.KacReportDisplayModel;
import com.klm.chipnpin.chipnpinweb.model.ReportModel;
import com.klm.chipnpin.chipnpinweb.transformer.ServiceDetailsFromDBToDomain;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Component
public class RetrieveReportDetails {

    @Autowired
    ReportDetails reportDetails;
    
    @Autowired
    ServiceDetailsFromDBToDomain doamin;

    public List<ReportModel> getReportModelForAllKiosk() throws Exception {
        List<ServiceDetails> serviceDetailsFromDB = reportDetails.findServiceDetailsForAllKiosk();
        return doamin.convertToDomain(serviceDetailsFromDB);
    }

    public List<ReportModel> getReportModelperKiosk(String kioskId) {
        List<ServiceDetails> serviceDetailsFromDB = reportDetails.findServiceDetailsPerKiosk(kioskId);
        return doamin.convertToDomain(serviceDetailsFromDB);
    }

    public List<ReportModel> getReportModelForNGKKiosks(List<String> kioskIdsList) {
        List<ServiceDetails> serviceDetailsFromDB = reportDetails.findServiceDetailsNGKKiosks(kioskIdsList);
        return doamin.convertToDomain(serviceDetailsFromDB);
    }

    /**
     * @param kioskId
     * @param fromDate
     * @param toDate
     * @return serviceDetailsFromDB
     * @throws Exception
     */
    public List<CreditCardReportModel> getCreditCardReportModelForAllKiosk(String kioskId, String fromDate, String toDate) throws Exception {
    	List<CreditcardServiceDetails> serviceDetailsFromDB = reportDetails.findCreditCardServiceDetails(kioskId, fromDate, toDate);
        return doamin.convertToCreditCardDomain(serviceDetailsFromDB);
    }

    
    public List<KacReportDisplayModel> getKacReportModelForCustom(String kacNumber, String from, String to, String kioskId) {
    	List<KacReportDisplayModel> reports = new ArrayList<KacReportDisplayModel>();
    	Map<String, KacReportDisplayModel> KacReportDisplayMap = new TreeMap<String, KacReportDisplayModel>();
    	String customSearchCriteria;
    	if(!from.isEmpty() && !to.isEmpty()) {
    		customSearchCriteria = " (From:" + from + ")-(To:" + to+")";
    	} else {
    		customSearchCriteria = "Please enter From and To date";
    	}
    	if(!kacNumber.isEmpty() && !kioskId.isEmpty()){
    		customSearchCriteria = customSearchCriteria + " for the KAC: " + kacNumber + " and kioskId: "+kioskId;
    	} else if(!kioskId.isEmpty()){
    		customSearchCriteria = customSearchCriteria + " for the kiosk: " + kioskId;
    	} else if(!kacNumber.isEmpty()) {
    		customSearchCriteria = customSearchCriteria + " for the KAC: " + kacNumber;
    	}
    	
    	DBCursor cursor = reportDetails.findKacDetailsForCustom(kacNumber, from, to, kioskId);
    	boolean isKacFound = false;
    	while (null != cursor && cursor.hasNext()) {
    		DBObject dbObject = cursor.next();
    		String dateStr = ((BasicDBObject) dbObject.get("serviceDetails")).get("dateStr").toString();
    		String dateAndHour = dateStr.substring(0, dateStr.indexOf(":"));
            KacReportDisplayModel kacReportDisplayModel = KacReportDisplayMap.get(dateAndHour);
            if (kacReportDisplayModel == null) {
            	kacReportDisplayModel = new KacReportDisplayModel();
            	kacReportDisplayModel.setDate(dateAndHour+":00");
            	kacReportDisplayModel.setCustomSearchedCriteria(customSearchCriteria);
            	KacReportDisplayMap.put(dateAndHour, kacReportDisplayModel);
            }
            String value = (String) dbObject.get("kacNumber");
            addKacNumber(value, kacReportDisplayModel);
            isKacFound = true;
    	}
    	if(!isKacFound) {
    		KacReportDisplayModel kacReportDisplayModel = new KacReportDisplayModel();
    		kacReportDisplayModel.setCustomSearchedCriteria(customSearchCriteria);
    		KacReportDisplayMap.put("00:00", kacReportDisplayModel);
    	}
    	reports = new ArrayList<KacReportDisplayModel>(KacReportDisplayMap.values());
    	return reports;
    }
    
    
    public List<KACServiceDetails> getKacReportModelForDataCustom(String kacNumber, String from, String to, String pnrEtkt) {
    	List<KacReportDisplayModel> reports = new ArrayList<KacReportDisplayModel>();
    	Map<String, KacReportDisplayModel> KacReportDisplayMap = new TreeMap<String, KacReportDisplayModel>();
    	List<KACServiceDetails> kacServiceDetails = reportDetails.findKacDetailsForDataCustom(kacNumber, from, to, pnrEtkt);
    	return kacServiceDetails;
    }
    
    /**
     * Method to get details for all kiosks as a whole
     * @return
     * @throws ParseException
     */
    public List<KacReportDisplayModel> getKacReportModelForAllKiosk() throws ParseException {
    	List<KacReportDisplayModel> reports = new ArrayList<KacReportDisplayModel>();
    	Map<String, KacReportDisplayModel> KacReportDisplayMap = new TreeMap<String, KacReportDisplayModel>();
    	DBCursor cursor = reportDetails.findKacDetailsForAllKiosk();
    	while (cursor.hasNext()) {
    		DBObject dbObject = cursor.next();
    		String dateStr = ((BasicDBObject) dbObject.get("serviceDetails")).get("dateStr").toString();
    		String dateAndHour = dateStr.substring(0, dateStr.indexOf(":"));
            KacReportDisplayModel kacReportDisplayModel = KacReportDisplayMap.get(dateAndHour);
            if (kacReportDisplayModel == null) {
            	kacReportDisplayModel = new KacReportDisplayModel();
            	kacReportDisplayModel.setDate(dateAndHour+":00");
            	KacReportDisplayMap.put(dateAndHour, kacReportDisplayModel);
            }
            Integer count = null;
            String value = (String) dbObject.get("kacNumber");
            addKacNumber(value, kacReportDisplayModel);
    	}
    	reports = new ArrayList<KacReportDisplayModel>(KacReportDisplayMap.values());
    	return reports;
    }
    
    private void addKacNumber(String value, KacReportDisplayModel kacReportDisplayModel) {
    	kacReportDisplayModel.setCustomTotalKacCount(1);
    	if (value != null && value.equalsIgnoreCase("2100")) {
    		kacReportDisplayModel.setKac_2100(1);
    	} else if(value != null && value.equalsIgnoreCase("2304")) {
    		kacReportDisplayModel.setKac_2304(1);
    	} else if(value != null && value.equalsIgnoreCase("2235")) {
    		kacReportDisplayModel.setKac_2235(1);
    	} else if(value != null && value.equalsIgnoreCase("2303")) {
    		kacReportDisplayModel.setKac_2303(1);
    	} else if(value != null && value.equalsIgnoreCase("2101")) {
    		kacReportDisplayModel.setKac_2101(1);
    	} else if(value != null && value.equalsIgnoreCase("8409")) {
    		kacReportDisplayModel.setKac_8409(1);
    	} else if(value != null && value.equalsIgnoreCase("2230")) {
    		kacReportDisplayModel.setKac_2230(1);
    	} else if(value != null && value.equalsIgnoreCase("2117")) {
    		kacReportDisplayModel.setKac_2117(1);
    	} else if(value != null && value.equalsIgnoreCase("2217")) {
    		kacReportDisplayModel.setKac_2217(1);
    	} else if(value != null && value.equalsIgnoreCase("2111")) {
    		kacReportDisplayModel.setKac_2111(1);
    	} else if(value != null && value.equalsIgnoreCase("2407")) {
    		kacReportDisplayModel.setKac_2407(1);
    	} else if(value != null && value.equalsIgnoreCase("2309")) {
    		kacReportDisplayModel.setKac_2309(1);
    	} else if(value != null && value.equalsIgnoreCase("8000")) {
    		kacReportDisplayModel.setKac_8000(1);
    	} else if(value != null && value.equalsIgnoreCase("8003")) {
    		kacReportDisplayModel.setKac_8003(1);
    	} else if(value != null && value.equalsIgnoreCase("2203")) {
    		kacReportDisplayModel.setKac_2203(1);
    	} else if(value != null && value.equalsIgnoreCase("8411")) {
    		kacReportDisplayModel.setKac_8411(1);
    	} else if(value != null && value.equalsIgnoreCase("5101")) {
    		kacReportDisplayModel.setKac_5101(1);
    	} else if(value != null && value.equalsIgnoreCase("8414")) {
    		kacReportDisplayModel.setKac_8414(1);
    	} else if(value != null && value.equalsIgnoreCase("8001")) {
    		kacReportDisplayModel.setKac_8001(1);
    	} else if(value != null && value.equalsIgnoreCase("2229")) {
    		kacReportDisplayModel.setKac_2229(1);
    	} else if(value != null && value.equalsIgnoreCase("2215")) {
    		kacReportDisplayModel.setKac_2215(1);
    	} else if(value != null && value.equalsIgnoreCase("3301")) {
    		kacReportDisplayModel.setKac_3301(1);
    	} else if(value != null && value.equalsIgnoreCase("2254")) {
    		kacReportDisplayModel.setKac_2254(1);
    	} else if(value != null && value.equalsIgnoreCase("5000")) {
    		kacReportDisplayModel.setKac_5000(1);
    	} else if(value != null && value.equalsIgnoreCase("8406")) {
    		kacReportDisplayModel.setKac_8406(1);
    	} else if(value != null && value.equalsIgnoreCase("5402")) {
    		kacReportDisplayModel.setKac_5402(1);
    	} else if(value != null && value.equalsIgnoreCase("5404")) {
    		kacReportDisplayModel.setKac_5404(1);
    	} else if(value != null && value.equalsIgnoreCase("4502")) {
    		kacReportDisplayModel.setKac_4502(1);
    	} else if(value != null && value.equalsIgnoreCase("5403")) {
    		kacReportDisplayModel.setKac_5403(1);
    	} else if(value != null && value.equalsIgnoreCase("2212")) {
    		kacReportDisplayModel.setKac_2212(1);
    	} else if(value != null && value.equalsIgnoreCase("2221")) {
    		kacReportDisplayModel.setKac_2221(1);
    	}

    }

    public List<BillLogs> getReportModelForBillLogs() {
    	List<BillLogs> serviceDetailsFromDB = reportDetails.getReportModelForBillLogs();
    	return serviceDetailsFromDB;
    }
    
    public List<DvoLogs> getReportModelForDvoLogs() {
    	List<DvoLogs> serviceDetailsFromDB = reportDetails.getReportModelForDvoLogs();
    	return serviceDetailsFromDB;
    }

}
