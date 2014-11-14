package com.klm.chipnpin.chipnpinweb.chipnpinpersistance.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ServiceDetails {

	@Id
	private String id;
	private Date date;
	private String sessionId;
	private String kioskId;
	private String view;
	private String key;
	private String responseCode;
	private String responseError;
	public String getId() {
		return id;
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


	public void setId(String id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
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
	
	
}
