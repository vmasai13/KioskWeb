package com.klm.tcs.kiosk;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.batch.item.ItemProcessor;

public class KioskMultiRequestProcessor implements ItemProcessor<String, RawRecord> {

	public static String kioskId_Properties = "C:/develop/code/Reporting/cassandrabatchici/src/main/resources/kioskids.properties";
	
	@Override
	public RawRecord process(String rawString) throws Exception {
		
		Properties props = new Properties();
		RawRecord rawRecord = null;
		String kioskIds = null;
		props.load(new FileInputStream(kioskId_Properties));


		String[] rawStringKioskIdArray = rawString.split(" ");
		String rawStringKioskId = null;

		try 
		{
			if (null != rawStringKioskIdArray && rawStringKioskIdArray[0].equals("ERROR") && rawString.contains("ResponseCode")) {
				// For BLS Kiosks			
				kioskIds = props.getProperty("kioskcdgIds"); 
				rawStringKioskId = rawStringKioskIdArray[5];
			} else if(rawString.contains("SellProducts")) {
				// For IBM kiosks
				kioskIds = props.getProperty("AMS_IBM_KIOSKS");
				rawStringKioskId = rawStringKioskIdArray[4];

			}
		} catch(Exception exception) {
			System.out.println("Exception occurs: "+exception);
		}

		if(null!= rawStringKioskId && !rawStringKioskId.isEmpty()) {
			String[] result = kioskIds.split(",");
			List<String> kioskIdList = Arrays.asList(result);
			if (null != kioskIdList && kioskIdList.size() > 0) {
				if (kioskIdList.contains(rawStringKioskId)) {
					rawRecord = new RawRecord();
					rawRecord.setRawLine(rawString);				
				}
			}
		}
		return rawRecord;
		/*if (null != rawString) {
			for (int x = 0; x < result.length; x++) {
				if (rawString.contains(result[x])) {
					rawRecord = new RawRecord();
					rawRecord.setDay(split[0].trim());
					rawRecord.setTimestamp(split[1].trim());
					rawRecord.setKioskIdFromFlowTrace(split[4].trim());
					rawRecord.setFirstPartInfo(split1[0]);
					rawRecord.setFlowView(split[6].trim());
					rawRecord.setRawLine(rawString);
					//System.out.println(rawString);
				}
			}
		}*/
	}
}
