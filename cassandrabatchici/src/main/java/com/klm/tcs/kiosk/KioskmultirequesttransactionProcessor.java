package com.klm.tcs.kiosk;

import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.batch.item.ItemProcessor;

public class KioskmultirequesttransactionProcessor implements ItemProcessor<String, RawRecord>  {

	@Override
	public RawRecord process(String rawString) throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream("D:\\chip n pin\\cassandrabatchici\\cassandrabatchici\\src\\main\\resources\\kioskids.properties"));
		String kioskIds = props.getProperty("transactionId");
		String[] result = kioskIds.split(",");
		
		RawRecord rawRecord = null;
		if (null != rawString) {
			for (int x = 0; x < result.length; x++) {
				if (!rawString.contains(result[x])) {
					rawRecord = new RawRecord();
					
					rawRecord.setRawLine(rawString);
					//System.out.println(rawString);
				}
			}
		}
		return rawRecord;
	}
}
