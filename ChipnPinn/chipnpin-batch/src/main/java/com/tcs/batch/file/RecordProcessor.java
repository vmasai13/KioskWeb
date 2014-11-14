package com.tcs.batch.file;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import com.klm.chipnpin.chipnpinpersistance.domain.KACServiceDetails;
import com.klm.chipnpin.chipnpinpersistance.domain.ServiceDetails;
public class RecordProcessor implements ItemProcessor<String, ServiceDetails> {
    @Override
    public ServiceDetails process(String recordString) throws Exception {
        ServiceDetails details = new ServiceDetails();
        KACServiceDetails kacdetails = new KACServiceDetails();
        if (null != recordString && !recordString.isEmpty() && recordString.contains("EMV") && recordString.contains("ResponseCode")) {
            String[] data = recordString.split(" ");
            System.out.println(recordString);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(data[1]);
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
            System.out.println("Country [code= " + data[0] + " , name=" + data[3] + "]");
            return details;
        }
        if (null != recordString && !recordString.isEmpty() && recordString.contains("KAC :")) {
            String[] data = recordString.split(" ");
            ServiceDetails serviceDetails = new ServiceDetails();
            System.out.println(recordString);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(data[1]);
            serviceDetails.setDate(date);
            serviceDetails.setKioskId(data[5]);
            serviceDetails.setKey(data[4]);
            kacdetails.setKacNumber(StringUtils.substringAfter(recordString, "KAC :[").substring(0, StringUtils.substringAfter(recordString, "KAC :[").length() - 1));
            kacdetails.setPnr(StringUtils.substringAfter(recordString, "Id: [").substring(0, 6));
            kacdetails.setServiceDetails(serviceDetails);
            return kacdetails;
        }
        return null;
    }
}
