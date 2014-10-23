package com.klm.tcs.flatfile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RawRecord {

	private String logInfo;
	private String message;
	private String timestamp;
	private String date;
	private String uuid;
	private String[] logInfoSplit;

	private String requestType;
	private String pnr;
	private String etkt;
	private String checkinstatus;
	private String errorcode;

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
		if (null != logInfo) {
			this.logInfoSplit = logInfo.split(" ");
		}
	}

	public String getMessage() {
		return "<" + message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		if (null != logInfoSplit) {
			String first = logInfoSplit[0];
			String[] split = first.split(":");
			this.date = split[1] + " " + logInfoSplit[1];
			this.timestamp = getTimestamp(split[1] + " " + logInfoSplit[1]);
		}
		return timestamp;
	}

	private String getTimestamp(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,S");
		try {
			Date today = df.parse(date);
			return String.valueOf(today.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUuid() {
		if (null != this.logInfoSplit) {
			this.uuid = this.logInfoSplit[2];
		}
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDate() {
		// String temp = getTimestamp();
		return logInfoSplit[0] + "_" + logInfoSplit[1];
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String[] getLogInfoSplit() {
		return logInfoSplit;
	}

	public void setLogInfoSplit(String[] logInfoSplit) {
		this.logInfoSplit = logInfoSplit;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getEtkt() {
		return etkt;
	}

	public void setEtkt(String etkt) {
		this.etkt = etkt;
	}

	public String getCheckinstatus() {
		return checkinstatus;
	}

	public void setCheckinstatus(String checkinstatus) {
		this.checkinstatus = checkinstatus;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

}
