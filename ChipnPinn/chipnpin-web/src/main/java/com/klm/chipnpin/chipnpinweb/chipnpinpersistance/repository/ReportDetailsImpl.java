package com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.CreditcardServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.KACServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.ServiceDetails;
import com.klm.chipnpin.chipnpinweb.util.ChipnpinCommonUtil;
import com.mongodb.BasicDBObject;


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

	@Override
	public List<KACServiceDetails> findKacDetailsForAllKiosk() {
		List<KACServiceDetails> serviceDetails = mongoTemplate.findAll(KACServiceDetails.class);
		return Collections.unmodifiableList(serviceDetails);
	}

	@Override
	public List<KACServiceDetails> findKacDetailsForSelectiveDate(String kacName, String from, String to, String kioskId) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		List<KACServiceDetails> kacServicedetails = null;
		Query query = null;
		Date dateFrom = null;
		Date dateTo = null;
		try {
			dateFrom = formatter.parse(from);
			dateTo = formatter.parse(to);
			System.out.println("dateFrom: "+dateFrom);
			System.out.println("dateTo: "+dateTo);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != dateFrom && null != dateTo) {
			if (!kacName.isEmpty() && !kioskId.isEmpty()) {
				query = new Query(Criteria.where("serviceDetails.date").gte(dateFrom).lte(dateTo).and("kacNumber").is(kacName).and("serviceDetails.kioskId").regex(kioskId));
			} 
			if (!kioskId.isEmpty() && kacName.isEmpty()) {
				query = new Query(Criteria.where("serviceDetails.date").gte(dateFrom).lte(dateTo).and("serviceDetails.kioskId").regex(kioskId));
			}
			if (kioskId.isEmpty() && !kacName.isEmpty()) {
				query = new Query(Criteria.where("serviceDetails.date").gte(dateFrom).lte(dateTo).and("kacNumber").is(kacName));
			}
			
		}
		if(null != query)
			kacServicedetails = mongoTemplate.find(query, KACServiceDetails.class);;
		return kacServicedetails;
	}
	
	/* 
	 * Getting credit card details
	 * (non-Javadoc)
	 * @see com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository.ReportDetails#findServiceDetailsForAllKiosk(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<CreditcardServiceDetails> findCreditCardServiceDetails(String kioskId, String fromDate, String toDate) {
		Date dateFrom = ChipnpinCommonUtil.stringToDate(fromDate);
		Date dateTo = ChipnpinCommonUtil.stringToDate(toDate);
		Query query = null;
		List<CreditcardServiceDetails> serviceDetails = null;
		
		if (null != dateFrom && null != dateTo) {
			if (!kioskId.isEmpty()) {
				// Regex for fetching data's - case-insensitive fetch
				query = new Query(Criteria.where("date").gte(dateFrom).lte(dateTo).and("kioskId").regex(kioskId,"i"));	
			} else {
				query = new Query(Criteria.where("date").gte(dateFrom).lte(dateTo));
			}
		}
		if(null != query)
			serviceDetails = mongoTemplate.find(query, CreditcardServiceDetails.class);
		return Collections.unmodifiableList(serviceDetails);
	}
}
