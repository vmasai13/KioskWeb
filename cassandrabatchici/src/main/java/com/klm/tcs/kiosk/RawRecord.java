package com.klm.tcs.kiosk;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.klm.tcs.util.Constants;
import com.klm.tcs.util.MessageUtil;

public class RawRecord {

	private String logInfo;
	private String message;
	private String timestamp;
	private String date;
	private String uuid;
	private String[] logInfoSplit;
	private String kioskId;
	private String rawLine;
	private String requestType;
	private String pnr;
	private String etkt;
	private String checkinstatus;
	private String errorcode;
	private String subChannel;
	private String airportCode;
	private String host;
	private String clientId;
	private String lastName;
	private String firstName;
	private String uniquePassengerReference;
	private String ProhibitedReasonCode;
	private String ProhibitedReasonText;
	private String messageUUID;
	private String day;

	// for flowtrace
	private String flowView;
	private String executionKey;
	private String sessionId;
	private String firstPartInfo;
	private String kioskIdFromFlowTrace;
	private String flowTime;

	// for sellproduct request chip and pin
	private String paymentGroupCode;
	private String clientTransactionId;

	private String sellProductDate;
	private String sellProductSession;
	private String sellProductKey;
	private String sellProductKioskId;
	private String sellProductPNR;
	private String sellProductErrorCode;
	private String sellProductErrorText;
	private String sellProductPSPNumber;
	private String sellProductDepartStation;
	private String fileNameReference;

	public String getFileNameReference() {
		return fileNameReference;
	}

	public void setFileNameReference(String fileNameReference) {
		this.fileNameReference = fileNameReference;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

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
		return logInfoSplit[1];
	}

	public String getDateTime() {
		return logInfoSplit[1] + "_" + logInfoSplit[2];
	}

	public String getTime() {
		return logInfoSplit[2];
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

	public String getRawLine() {
		return rawLine;
	}

	public void setRawLine(String rawLine) {
		this.rawLine = rawLine;
	}

	public String getRequestType() {
		return MessageUtil.getFileName(this.message);
	}

	public String getPnr() {
		return MessageUtil.getElementValueFromXML(Constants.XML_PNR_ADDRESS, this.message, null);
	}

	public String getEtkt() {
		return MessageUtil.getElementValueFromXML(Constants.XML_TICKET_NUMBER, this.message, null) + "-";
	}

	public String getCheckinstatus() {
		return MessageUtil.getElementValueFromXML(Constants.XML_CHECKIN_STATUS, this.message, null);
	}

	public String getErrorcode() {
		return MessageUtil.getElementValueFromXML(Constants.XML_ERROR_CODE, this.message, null);
	}

	public String getKioskId() {
		return logInfoSplit[4];
	}

	public String getAirportCode() {
		return MessageUtil.getElementValueFromXML(Constants.XML_AIRPORT_CODE, this.message, null);
	}

	public String getHost() {
		return MessageUtil.getElementValueFromXML(Constants.XML_HOST, this.message, null);
	}

	public String getClientId() {
		return MessageUtil.getElementValueFromXML(Constants.XML_CLIENT_Id, this.message, null);
	}

	public String getLastName() {
		return MessageUtil.getElementValueFromXML(Constants.XML_LAST_NAME, this.message, null);
	}

	public String getFirstName() {
		return MessageUtil.getElementValueFromXML(Constants.XML_FIRST_NAME, this.message, null);
	}

	public String getUniquePassengerReference() {
		return MessageUtil.getElementValueFromXML(Constants.XML_UPR, this.message, null);
	}

	public String getProhibitedReasonCode() {
		return MessageUtil.getElementValueFromXML(Constants.XML_PROHIBITED_REASON_CODE, this.message, null);
	}

	public String getProhibitedReasonText() {
		return MessageUtil.getElementValueFromXML(Constants.XML_PROHIBITED_REASON_TEXT, this.message, null);
	}

	public String getMessageUUID() {
		return MessageUtil.getMessageUUID(this.message);
	}

	public String getDepartureStation() {
		return MessageUtil.getElementValueFromXML(Constants.XML_DEP_STATION, this.message, null);
	}

	public String getFlowView() {
		return flowView;
	}

	public void setFlowView(String flowView) {
		this.flowView = flowView;
	}

	public String getExecutionKey() {
		// 2012-12-03 05:28:34,630 YtfjsgDKH4rRZ80_YWmKS3g e11s10 BLS519557
		String[] split = firstPartInfo.split(" ");
		return split[3];
	}

	public void setExecutionKey(String executionKey) {
		this.executionKey = executionKey;
	}

	public String getSessionId() {
		// 2012-12-03 05:28:34,630 YtfjsgDKH4rRZ80_YWmKS3g e11s10 BLS519557
		String[] split = firstPartInfo.split(" ");
		return split[2];
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getFirstPartInfo() {
		return firstPartInfo;
	}

	public void setFirstPartInfo(String firstPartInfo) {
		this.firstPartInfo = firstPartInfo;
	}

	public String getKioskIdFromFlowTrace() {
		// 2012-12-03 05:28:34,630 YtfjsgDKH4rRZ80_YWmKS3g e11s10 BLS519557
		String[] split = firstPartInfo.split(" ");
		return split[4];
	}

	public void setKioskIdFromFlowTrace(String kioskIdFromFlowTrace) {
		this.kioskIdFromFlowTrace = kioskIdFromFlowTrace;
	}

	public String getFlowTime() {
		// 2012-12-03 05:28:34,630 YtfjsgDKH4rRZ80_YWmKS3g e11s10 BLS519557
		String[] split = firstPartInfo.split(" ");
		return split[0] + " " + split[1];
	}

	public void setFlowTime(String flowTime) {
		this.flowTime = flowTime;
	}

	public String getClientTransactionId() {
		return MessageUtil.getElementValueFromXML("clientTransactionId", this.message, null);
	}

	public String getPaymentGroupCode() {
		return MessageUtil.getElementValueFromXML("paymentGroupCode", this.message, null);
	}

	public String getSellProductDate() {
		return this.logInfoSplit[0] + this.logInfoSplit[1];
	}

	public String getSellProductSession() {
		return this.logInfoSplit[2];
	}

	public String getSellProductKey() {
		return this.logInfoSplit[3];
	}

	public String getSellProductKioskId() {
		return this.logInfoSplit[4];
	}

	public String getSellProductPNR() {
		return MessageUtil.getElementValueFromXML("reservationIdentifier", this.message, null);
	}

	public String getSellProductErrorCode() {
		return MessageUtil.getElementValueFromXML("errorCode", this.message, null);
	}

	public String getSellProductErrorText() {
		return MessageUtil.getElementValueFromXML("errorText", this.message, null);
	}

	public String getSellProductPSPNumber() {
		return MessageUtil.getElementValueFromXML("pspPaymentReferenceId", this.message, null);
	}

	public String getSellProductDepartStation() {
		List<String> valuesFromXML = MessageUtil.getElementValuesFromXML("originAirport", this.message, null);
		if (null != valuesFromXML && valuesFromXML.size() > 0) {
			return valuesFromXML.get(0);
		}
		return "";
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getSubChannel() {
		return MessageUtil.getElementValueFromXML("subchannel", this.message, null);
	}

	public void setSubChannel(String subChannel) {
		this.subChannel = subChannel;
	}
	
	
}
