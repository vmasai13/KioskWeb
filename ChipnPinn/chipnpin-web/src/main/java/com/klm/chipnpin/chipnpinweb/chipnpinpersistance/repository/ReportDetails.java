package com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.klm.chipnpin.chipnpinpersistance.domain.LogBean;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.BillLogs;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.CreditcardServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.DvoLogs;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.KACServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.ServiceDetails;
import com.mongodb.DBCursor;

@Component
public interface ReportDetails {

	void add(ServiceDetails serviceDetails);
	void save(ServiceDetails serviceDetails);
	void remove(ServiceDetails serviceDetails);
	List<ServiceDetails> findAll();
	ServiceDetails find(String serviceName);
	List<ServiceDetails> findServiceDetailsForDate(String date);
	List<ServiceDetails> findServiceDetailsForAllKiosk();
	List<ServiceDetails> findServiceDetailsPerKiosk(String kioskId);
	List<ServiceDetails> findServiceDetailsNGKKiosks(List<String> kioskIdsList);
	// Getting the details for KAC Overview
	DBCursor findKacDetailsForAllKiosk();
	// Getting the details for KAC Custom
	DBCursor findKacDetailsForCustom(String kacNumber, String from, String to, String kioskId);
	// Getting the details for KAC Data
	List<KACServiceDetails> findKacDetailsForDataCustom(String kacNumber, String from, String to, String pnrEtkt);
	
	// For Creditcard
	List<CreditcardServiceDetails> findCreditCardServiceDetails(String kioskId, String fromDate, String toDate);
	// For BillLogs
	List<BillLogs> getReportModelForBillLogs();
	// For DvoLogs
	List<DvoLogs> getReportModelForDvoLogs();
	// Getting the details for performance check
	DBCursor getPrefCheckDetails();
}
