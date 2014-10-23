package com.tcs.batch.file;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.klm.chipnpin.chipnpinpersistance.domain.ServiceDetails;
import com.klm.chipnpin.chipnpinpersistance.repository.ReportDetails;

public class LineWriter implements ItemWriter<ServiceDetails> {

	@Autowired
	ReportDetails reportDetails;
	
	public void write(List<? extends ServiceDetails> details) throws Exception {		
		for (ServiceDetails detail : details) {
			//saveOrUpdateRecord(serviceDetail);
			if(null != detail) {
				saveOrUpdateServiceInformation(detail);
			}
		}				
	}
	
	private void saveOrUpdateServiceInformation(ServiceDetails detail) {
		if (detail != null) {
		saveOrUpdateRecord(detail);
			
		}
	}
	
	private void saveOrUpdateRecord(ServiceDetails detail) {
		reportDetails.save(detail);
	}
}
