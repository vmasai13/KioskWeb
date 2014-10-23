package com.klm.tcs.kiosk;

import org.springframework.batch.item.ItemProcessor;

public class KioskRecordProcessor implements ItemProcessor<String, RawRecord> {

	@Override
	public RawRecord process(String rawString) throws Exception {
		RawRecord rawRecord = null;
		if (null != rawString) {
			rawRecord = new RawRecord();
			String[] split = rawString.split(" - <");
			rawRecord.setLogInfo(split[0]);
			rawRecord.setMessage(split[1]);
			rawRecord.setRawLine(rawString);
		}
		return rawRecord;
	}

}
