package com.klm.tcs.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class MessageUtil {

	public static List<String> getElementValuesFromXML(String element, String message, String nameSpace) {
		List<String> elementValues = null;
		String namespace = null;
		String[] values = null;
		if (null != element && null != message) {
			elementValues = new ArrayList<String>();
			if (StringUtils.isEmpty(nameSpace)) {
				namespace = getNameSpace(message);
			} else {
				namespace = nameSpace;
			}
			if (StringUtils.isNotEmpty(namespace)) {
				// if the xml element has namespace then use namespace
				values = StringUtils.substringsBetween(message, "<" + namespace + ":" + element + ">", "</" + namespace + ":" + element + ">");
			} else {
				// the elements has no namespace
				values = StringUtils.substringsBetween(message, "<" + element + ">", "</" + element + ">");
			}
			if (null == values) {
				// the request\response has namespace but not the elements
				values = StringUtils.substringsBetween(message, "<" + element + ">", "</" + element + ">");
			}
			if (null != values) {
				elementValues = Arrays.asList(values);
			}
		}
		return elementValues;
	}

	public static String getElementValueFromXML(String element, String message, String nameSpace) {
		List<String> elementValuesFromXML = getElementValuesFromXML(element, message, nameSpace);
		if (null != elementValuesFromXML && !elementValuesFromXML.isEmpty()) {
			return elementValuesFromXML.get(0);
		}
		return null;
	}

	public static String getElementValueFromXMLNoNameSpace(String element, String message) {
		String str = null;
		if (null != element && null != message) {
			str = StringUtils.substringBetween(message, "<" + element + ">", "</" + element + ">");
		}
		return str;
	}

	public static String getNameSpace(String message) {
		return StringUtils.substringBetween(message, ":Body><", ":");
	}

	public static String getFileName(String message) {
		String columnValue = "Default";
		if (null != message) {
			int start = StringUtils.lastOrdinalIndexOf(message, "</", 3);
			int end = StringUtils.lastOrdinalIndexOf(message, ">", 3);
			columnValue = StringUtils.substring(message, start + 2, end);
			if (null != columnValue && columnValue.contains(":")) {
				columnValue = StringUtils.substring(columnValue, StringUtils.indexOf(columnValue, ":") + 1);
			}
		}
		return columnValue;
	}

	public static String getMessageUUID(String message) {
		String fileName = getFileName(message);
		if (fileName.endsWith("Request")) {
			return StringUtils.substringBetween(message, "addressing\">uuid:", "</MessageID>");
		} else {
			return StringUtils.substringBetween(message, "ReplyTo\">uuid:", "</RelatesTo>");
		}
	}

}
