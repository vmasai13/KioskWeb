package com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository;

import java.util.List;

import org.springframework.stereotype.Component;

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
}
