package com.klm.chipnpin.chipnpinweb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.ServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository.ReportDetails;
import com.klm.chipnpin.chipnpinweb.model.ReportModel;
import com.klm.chipnpin.chipnpinweb.transformer.ServiceDetailsFromDBToDomain;

@Component
public class RetrieveReportDetails {

	@Autowired
	ReportDetails reportDetails;
	
	@Autowired
	ServiceDetailsFromDBToDomain doamin;
	
	public List<ReportModel> getReportModelForAllKiosk() throws Exception{
		List<ServiceDetails> serviceDetailsFromDB = reportDetails.findServiceDetailsForAllKiosk();
		return doamin.convertToDomain(serviceDetailsFromDB);
	}

	public List<ReportModel> getReportModelperKiosk(String kioskId) {
		List<ServiceDetails> serviceDetailsFromDB = reportDetails.findServiceDetailsPerKiosk(kioskId);
		return doamin.convertToDomain(serviceDetailsFromDB);
	}

	public List<ReportModel> getReportModelForNGKKiosks(
			List<String> kioskIdsList) {
		List<ServiceDetails> serviceDetailsFromDB = reportDetails.findServiceDetailsNGKKiosks(kioskIdsList);
		return doamin.convertToDomain(serviceDetailsFromDB);
	}

}
