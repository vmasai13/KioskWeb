package com.tcs.batch.file;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.klm.chipnpin.chipnpinpersistance.domain.KACServiceDetails;
import com.klm.chipnpin.chipnpinpersistance.domain.LogBean;
import com.klm.chipnpin.chipnpinpersistance.domain.ServiceDetails;
import com.klm.chipnpin.chipnpinpersistance.repository.ReportDetails;

public class LineWriter implements ItemWriter<ServiceDetails> {

	@Autowired
	ReportDetails reportDetails;
	
	public void write(List<? extends ServiceDetails> details) throws Exception {
		
		if (details.size()>0) {
			for (Object detail : details) {
				if (null != detail && detail instanceof KACServiceDetails) {
					saveOrUpdateServiceKACInformation((KACServiceDetails)detail);
				}else if (null != detail && detail instanceof ServiceDetails) {
					saveOrUpdateServiceInformation((ServiceDetails) detail);
				} else if(null != detail && detail instanceof LogBean) {
					saveOrUpdateLogBean((LogBean) detail);
				}
			}
		}	
	}
	
	private void saveOrUpdateServiceKACInformation(KACServiceDetails detail) {
		if (detail != null) {
			saveOrUpdateKACRecord(detail);
			}
	}

	private void saveOrUpdateKACRecord(KACServiceDetails detail) {
		reportDetails.saveKac(detail);
	}
	
	private void saveOrUpdateLogBean(LogBean logBean) {
		if (logBean != null) {
		saveOrUpdateLogRecord(logBean);
			
		}
	}
	
	private void saveOrUpdateLogRecord(LogBean logBean) {
		reportDetails.saveLogBean(logBean);
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
