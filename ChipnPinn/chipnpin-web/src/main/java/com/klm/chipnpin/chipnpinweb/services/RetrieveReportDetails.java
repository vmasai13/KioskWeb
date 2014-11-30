package com.klm.chipnpin.chipnpinweb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.BillLogs;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.CreditcardServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.DvoLogs;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.KACServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.ServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository.ReportDetails;
import com.klm.chipnpin.chipnpinweb.model.CreditCardReportModel;
import com.klm.chipnpin.chipnpinweb.model.KacReportModel;
import com.klm.chipnpin.chipnpinweb.model.ReportModel;
import com.klm.chipnpin.chipnpinweb.transformer.ServiceDetailsFromDBToDomain;
import com.klm.chipnpin.chipnpinweb.util.ConfigurableResources;

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

    public List<KacReportModel> getKacReportModelForAllKiosk() {
        List<KACServiceDetails> serviceDetailsFromDB = reportDetails.findKacDetailsForAllKiosk();
        return doamin.convertToKacReportDomain(serviceDetailsFromDB);
    }

    public List<KacReportModel> getKacReportModelForSelectiveDate(String kacName, String from, String to, String kioskId) {
        List<KACServiceDetails> serviceDetailsFromDB = reportDetails.findKacDetailsForSelectiveDate(kacName, from, to, kioskId);
        return doamin.convertToKacReportDomain(serviceDetailsFromDB);
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
