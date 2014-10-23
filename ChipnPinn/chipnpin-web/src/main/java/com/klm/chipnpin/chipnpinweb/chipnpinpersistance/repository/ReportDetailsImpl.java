package com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.ServiceDetails;


@Repository
@Component
public class ReportDetailsImpl implements ReportDetails{

	@Autowired
	MongoTemplate mongoTemplate;
	
	public void add(ServiceDetails serviceDetails) {
		mongoTemplate.insert(serviceDetails);
	}
	
	public void save(ServiceDetails serviceDetails) {
		mongoTemplate.save(serviceDetails);
	}
	
	public void remove(ServiceDetails serviceDetails) {
		// TODO Auto-generated method stub

	}
	
	public List<ServiceDetails> findServiceDetailsForDate(String date) {
		Query query = new Query(Criteria.where("date").is(date));
		return mongoTemplate.find(query, ServiceDetails.class);
	}
	
	public ServiceDetails find(String serviceName) {
		Query query = new Query(Criteria.where("serviceName").is(serviceName));
		return mongoTemplate.findOne(query, ServiceDetails.class);
	}

	@Override
	public List<ServiceDetails> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServiceDetails> findServiceDetailsForAllKiosk() {
		List<ServiceDetails> serviceDetails = mongoTemplate.findAll(ServiceDetails.class);
		return Collections.unmodifiableList(serviceDetails);
	}

	@Override
	public List<ServiceDetails> findServiceDetailsPerKiosk(String kioskId) {
		Query query = new Query(Criteria.where("kioskId").is(kioskId));
		return mongoTemplate.find(query, ServiceDetails.class);
	}

	@Override
	public List<ServiceDetails> findServiceDetailsNGKKiosks(
			List<String> kioskIdsList) {
		List<ServiceDetails> serviceDetails;
		Query query = new Query(Criteria.where("kioskId").in(kioskIdsList));
		serviceDetails = mongoTemplate.find(query, ServiceDetails.class);
		return mongoTemplate.find(query, ServiceDetails.class);
	}
	
	
	/*public List<ServiceDetails> findServiceDetailsForDate(String date) {
		Query query = new Query(Criteria.where("date").is(date));
		return mongoTemplate.find(query, ServiceDetails.class);
	
	}*/
}
