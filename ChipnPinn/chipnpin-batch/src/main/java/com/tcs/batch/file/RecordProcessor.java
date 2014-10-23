package com.tcs.batch.file;

import org.springframework.batch.item.ItemProcessor;

import com.klm.chipnpin.chipnpinpersistance.domain.ServiceDetails;

public class RecordProcessor implements ItemProcessor<String, ServiceDetails>{

	@Override
	public ServiceDetails process(String recordString) throws Exception {
		String line ="";
		ServiceDetails details = new ServiceDetails();
		if (null != recordString && !recordString.isEmpty() && recordString.contains("EMV") && recordString.contains("ResponseCode")) {
			String[] data = recordString.split(" ");
			 System.out.println(line);
			details.setDate(data[1]);
			details.setKioskId(data[5]);
			details.setKey(data[4]);
			details.setResponseCode(data[10].replace(":", ""));
			if (data.length >14) {
				details.setResponseError(data[14]);
			} else {
				details.setResponseError("");
			}
			
			System.out.println("Country [code= " + data[0] 
                                 + " , name=" + data[3] + "]");
			
			return details;
		}
		
		return null;
	}
}
