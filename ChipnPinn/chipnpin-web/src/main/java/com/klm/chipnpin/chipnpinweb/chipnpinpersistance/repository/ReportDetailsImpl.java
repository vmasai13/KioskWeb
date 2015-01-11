package com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.BillLogs;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.CreditcardServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.DvoLogs;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.KACServiceDetails;
import com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain.ServiceDetails;
import com.klm.chipnpin.chipnpinweb.util.ChipnpinCommonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;


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

	/* (non-Javadoc)
	 * Getting the details for KAC Overview
	 * @see com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository.ReportDetails#findKacDetailsForAllKiosk()
	 */
	@Override
	public DBCursor findKacDetailsForAllKiosk() {
		DBCollection kacServiceDetailsCollection = mongoTemplate.getCollection("kACServiceDetails");
		DBCursor cursor = kacServiceDetailsCollection.find();
		return cursor;
	}
	
	/* (non-Javadoc)
	 * Getting the details for KAC Custom
	 * @see com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository.ReportDetails#findKacDetailsForCustom(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DBCursor findKacDetailsForCustom(String kacNumber, String from, String to, String kioskId) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		DBCursor cursor = null;
		Date dateFrom = null;
		Date dateTo = null;
		try {
			dateFrom = formatter.parse(from);
			dateTo = formatter.parse(to);
			dateTo = new Date(dateTo.getTime() + (1000 * 60 * 60 * 24));
			System.out.println("dateFrom: "+dateFrom);
			System.out.println("dateTo: "+dateTo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		BasicDBObject queryObject = new BasicDBObject();
		
		if (null != dateFrom && null != dateTo) {
			queryObject.put("serviceDetails.date", new BasicDBObject("$gt", dateFrom).append("$lt", dateTo));
			if (!kacNumber.isEmpty() && !kioskId.isEmpty()) {
//				query = new Query(Criteria.where("serviceDetails.date").gte(dateFrom).lte(dateTo).and("kacNumber").is(kacNumber).and("serviceDetails.kioskId").regex(kioskId));
				queryObject.put("serviceDetails.kioskId", kioskId);
				queryObject.put("kacNumber", kacNumber);
			} 
			if (!kioskId.isEmpty() && kacNumber.isEmpty()) {
//				query = new Query(Criteria.where("serviceDetails.date").gte(dateFrom).lte(dateTo).and("serviceDetails.kioskId").regex(kioskId));
				queryObject.put("serviceDetails.kioskId", kioskId);
			}
			if (kioskId.isEmpty() && !kacNumber.isEmpty()) {
				queryObject.put("kacNumber", kacNumber);
//				query = new Query(Criteria.where("serviceDetails.date").gte(dateFrom).lte(dateTo).and("kacNumber").is(kacNumber));
			}
		}
		if(queryObject.size() > 0) {
//			kacServicedetails = mongoTemplate.find(query, KACServiceDetails.class); // old query
			DBCollection kacServiceDetailsCollection = mongoTemplate.getCollection("kACServiceDetails");
			cursor = (DBCursor) kacServiceDetailsCollection.find(queryObject);
		}
		return cursor;
	}
	
	
	/* (non-Javadoc)
	 * Getting the details for KAC Data Custom
	 * @see com.klm.chipnpin.chipnpinweb.chipnpinpersistance.repository.ReportDetails#findKacDetailsForCustom(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<KACServiceDetails> findKacDetailsForDataCustom(String kacNumber, String from, String to, String pnrEtkt) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date dateFrom = null;
		Date dateTo = null;
		Query query = null;
		List<KACServiceDetails> kacServicedetails = null;
		try {
			dateFrom = formatter.parse(from);
			dateTo = formatter.parse(to);
			dateTo = new Date(dateTo.getTime() + (1000 * 60 * 60 * 24));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (null != dateFrom && null != dateTo) {
			query = new Query(Criteria.where("serviceDetails.date").gte(dateFrom).lte(dateTo));
			if (!kacNumber.isEmpty() && !pnrEtkt.isEmpty()) {
				if(pnrEtkt.length() > 6) {
					query = new Query(Criteria.where("serviceDetails.date").gte(dateFrom).lte(dateTo).and("kacNumber").is(kacNumber).and("serviceDetails.eticket").regex(pnrEtkt));
				} else {
					query = new Query(Criteria.where("serviceDetails.date").gte(dateFrom).lte(dateTo).and("kacNumber").is(kacNumber).and("serviceDetails.pnr").regex(pnrEtkt));
				}
			} 
			if (!pnrEtkt.isEmpty() && kacNumber.isEmpty()) {
				// queryObject.put("title", new BasicDBObject("$regex", pnrEtkt)); - For regular expression
				if(pnrEtkt.length() > 6) {
					query = new Query(Criteria.where("serviceDetails.date").gte(dateFrom).lte(dateTo).and("serviceDetails.eticket").regex(pnrEtkt));
				} else {
					query = new Query(Criteria.where("serviceDetails.date").gte(dateFrom).lte(dateTo).and("serviceDetails.pnr").regex(pnrEtkt));
				}
			}
			if (pnrEtkt.isEmpty() && !kacNumber.isEmpty()) {
				query = new Query(Criteria.where("serviceDetails.date").gte(dateFrom).lte(dateTo).and("kacNumber").is(kacNumber));
			}
		}
		if(null != query) {
			kacServicedetails = mongoTemplate.find(query, KACServiceDetails.class);
		} /*else {
			kacServicedetails = mongoTemplate.findAll(KACServiceDetails.class, "kACServiceDetails");
		}*/
		
		return kacServicedetails;
	}
	
	// For getting the bill logs from DB
	@Override
	public List<BillLogs> getReportModelForBillLogs() {
		List<BillLogs> billLogDetails = mongoTemplate.findAll(BillLogs.class);
		return Collections.unmodifiableList(billLogDetails);
	}
	
	// For getting the dvo logs from DB
	@Override
	public List<DvoLogs> getReportModelForDvoLogs() {
		List<DvoLogs> dvoLogDetails = mongoTemplate.findAll(DvoLogs.class);
		return Collections.unmodifiableList(dvoLogDetails);
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
