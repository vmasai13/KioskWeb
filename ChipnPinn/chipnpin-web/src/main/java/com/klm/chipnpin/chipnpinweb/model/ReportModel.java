package com.klm.chipnpin.chipnpinweb.model;

import java.util.Date;

public class ReportModel {

	private String date;
	private Date reportDate;
	private String sessionId;
	private String kioskId;
	private String view;
	private String key;
	private String responseCode;
	private String responseError;
	private int countSuccess;
	
	
	
	public int getCountSuccess() {
		return countSuccess;
	}
	public void setCountSuccess(int countSuccess) {
		this.countSuccess = countSuccess;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getKioskId() {
		return kioskId;
	}
	public void setKioskId(String kioskId) {
		this.kioskId = kioskId;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseError() {
		return responseError;
	}
	public void setResponseError(String responseError) {
		this.responseError = responseError;
	}
	
	
}
