package com.klm.chipnpin.chipnpinpersistance.repository;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.klm.chipnpin.chipnpinpersistance.domain.ServiceDetails;

@Component
public interface ReportDetails {

	void add(ServiceDetails serviceDetails);
	void save(ServiceDetails serviceDetails);
	void remove(ServiceDetails serviceDetails);
	List<ServiceDetails> findAll();
	ServiceDetails find(String serviceName);
	List<ServiceDetails> findServiceDetailsForDate(String date);
}
