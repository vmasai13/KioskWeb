package com.tcs.batch.file;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import com.klm.chipnpin.chipnpinpersistance.domain.KACServiceDetails;
import com.klm.chipnpin.chipnpinpersistance.domain.ServiceDetails;


public class RecordProcessor implements ItemProcessor<String, ServiceDetails> {
	
public ResourceHolderBean resourceHolderBean;
	
	public void setResourceHolderBean(ResourceHolderBean resourceHolderBean) {
		this.resourceHolderBean = resourceHolderBean;
	}

	public static void main(String args[]) {
		RecordProcessor rc = new RecordProcessor();
		String st = "ERROR 2015-01-03 00:32:46,005 B2faG51pNzvVVZWqldPWxXx e1s10 BLS252883 - EMV ResponseCode : 0000: EMV ErrorCode : 0000";
		try {
			rc.process(st);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
    public ServiceDetails process(String recordString) throws Exception {
		String fileInstance = "";
        /*String fileInstances[] = {"fip", "fjq", "fbi", "fcj"};
        String resourceFileName = resourceHolderBean.getResourceFileName();
//        System.out.println("fileInstances: "+ resourceFileName);
        if(!StringUtils.isEmpty(resourceFileName)) {
        	for(String fileIns:fileInstances) {
        		if(resourceFileName.contains(fileIns)) {
        			fileInstance = fileIns;
        		}
        	}
        }*/
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (null != recordString && !recordString.isEmpty() && recordString.contains("EMV") && recordString.contains("ResponseCode")) {
            String[] data = recordString.split(" ");
            Date date = formatter.parse(data[1]+" "+data[2]);
            ServiceDetails details = new ServiceDetails();
            details.setDateStr(data[1]+" "+data[2]);
            details.setDate(date);
            details.setKioskId(data[5]);
            details.setKey(data[4]);
            details.setResponseCode(data[10].replace(":", ""));
            if (data.length > 14) {
                details.setResponseError(data[14]);
            }
            else {
                details.setResponseError("");
            }
            return details;
        }
        if (null != recordString && !recordString.isEmpty() && recordString.contains("KAC :")) {
//        	System.out.println(recordString);
            String[] data = recordString.split(" ");
            ServiceDetails serviceDetails = new ServiceDetails();
            KACServiceDetails kacdetails = new KACServiceDetails();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
//            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = formatter.parse(data[1]+" "+data[2]);
            serviceDetails.setDateStr(data[1]+" "+data[2]);
            serviceDetails.setDate(date);
            serviceDetails.setKioskId(data[5]);
            // Session id
            serviceDetails.setSessionId(data[3]);
            // Set second part of session id
            serviceDetails.setKey(data[4]);
            // kac number
            kacdetails.setKacNumber(StringUtils.substringAfter(recordString, "KAC :[").substring(0, StringUtils.substringAfter(recordString, "KAC :[").length() - 1));
            // Pnr address
            if(recordString.contains("Id: [")) {
            	serviceDetails.setPnr(recordString.substring(recordString.indexOf("Id: [")+5, recordString.indexOf("] ")));
            }
            // e-ticket
            if(recordString.contains("ETKT : [")) {
            	serviceDetails.setEticket(recordString.substring(recordString.indexOf("ETKT : [")+8, recordString.indexOf("] UPR : [")));;
            }
            // Node instance
            serviceDetails.setNodeInstance(fileInstance);
            kacdetails.setServiceDetails(serviceDetails);
            return kacdetails;
        }
        return null;
    }
}
