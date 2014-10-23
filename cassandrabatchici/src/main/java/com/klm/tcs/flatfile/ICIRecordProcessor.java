package com.klm.tcs.flatfile;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;

import com.klm.tcs.util.Constants;
import com.klm.tcs.util.MessageUtil;

public class ICIRecordProcessor implements ItemProcessor<String, RawRecord> {

	@Override
	public RawRecord process(String rawString) throws Exception {
		RawRecord rawRecord = null;
		if (null != rawString) {
			rawRecord = new RawRecord();
			String[] split = rawString.split(" - <");
			rawRecord.setLogInfo(split[0]);
			rawRecord.setMessage(split[1]);
			rawRecord.setRequestType(MessageUtil.getFileName(rawRecord.getMessage()));
			String pnr = MessageUtil.getElementValueFromXML(Constants.XML_PNR_ADDRESS, rawRecord.getMessage(), null);
			if (StringUtils.isNotBlank(pnr)) {
				rawRecord.setPnr(pnr);
			} else {
				rawRecord.setPnr("No PNR");
			}

			String etkt = MessageUtil.getElementValueFromXML(Constants.XML_TICKET_NUMBER, rawRecord.getMessage(), null);

			if (StringUtils.isNotBlank(etkt)) {
				rawRecord.setEtkt(etkt);
			} else {
				rawRecord.setEtkt("No Etkt");
			}

			String checkIn = MessageUtil.getElementValueFromXML(Constants.XML_CHECKIN_STATUS, rawRecord.getMessage(), null);

			if (StringUtils.isNotBlank(checkIn)) {
				rawRecord.setCheckinstatus(checkIn);
			} else {
				rawRecord.setCheckinstatus("No Checkin");
			}

			String errorcode = MessageUtil.getElementValueFromXML(Constants.XML_ERROR_CODE, rawRecord.getMessage(), null);

			if (StringUtils.isNotBlank(errorcode)) {
				rawRecord.setErrorcode(errorcode);
			} else {
				rawRecord.setErrorcode("No Error code");
			}
		}
		return rawRecord;
	}

}
