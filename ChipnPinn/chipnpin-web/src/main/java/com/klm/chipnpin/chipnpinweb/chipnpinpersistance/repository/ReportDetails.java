package com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.BillLogs;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.CreditcardServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.DvoLogs;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.KACServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.ServiceDetails;

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
	List<KACServiceDetails> findKacDetailsForAllKiosk();
	List<KACServiceDetails> findKacDetailsForSelectiveDate(String kacName, String from, String to, String kioskId);
	
	// For Creditcard
	List<CreditcardServiceDetails> findCreditCardServiceDetails(String kioskId, String fromDate, String toDate);
	// For BillLogs
	List<BillLogs> getReportModelForBillLogs();
	// For DvoLogs
	List<DvoLogs> getReportModelForDvoLogs();
}
