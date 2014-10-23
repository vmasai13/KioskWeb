package com.klm.chipnpin.chipnpinweb.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.ServiceDetails;
import com.klm.chipnpin.chipnpinweb.model.ReportModel;

@Component
public class ServiceDetailsFromDBToDomain {

	public List<ReportModel> convertToDomain(List<ServiceDetails> serviceDetailsFromDB) {
		List<ReportModel> serviceDetailsDomainList = new ArrayList<ReportModel>();
		for (ServiceDetails serDetailsToConvert : serviceDetailsFromDB) {
			ReportModel  details = new ReportModel();
			details.setDate(serDetailsToConvert.getDate());
			details.setKey(serDetailsToConvert.getKey());
			details.setKioskId(serDetailsToConvert.getKioskId());
			details.setResponseCode(serDetailsToConvert.getResponseCode());
			details.setResponseError(serDetailsToConvert.getResponseError());
			serviceDetailsDomainList.add(details);
		}
		return serviceDetailsDomainList;
	}

}
