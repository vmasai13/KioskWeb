package com.klm.chipnpin.chipnpinpersistance.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.klm.chipnpin.chipnpinpersistance.domain.ServiceDetails;


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
	
	public List<ServiceDetails> findAll() {
		List<ServiceDetails> serviceDetails = mongoTemplate
				.findAll(ServiceDetails.class);
		return Collections.unmodifiableList(serviceDetails);
	}
	
	public ServiceDetails find(String serviceName) {
		Query query = new Query(Criteria.where("serviceName").is(serviceName));
		return mongoTemplate.findOne(query, ServiceDetails.class);
	}
	
	public List<ServiceDetails> findServiceDetailsForDate(String date) {
		Query query = new Query(Criteria.where("date").is(date));
		return mongoTemplate.find(query, ServiceDetails.class);
	
	}
}
