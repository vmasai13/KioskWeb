package com.tcs.fancylog;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
    	Date date1 = Calendar.getInstance().getTime();
		
		String month = String.valueOf(date1.getMonth()+1);
		String day = String.valueOf(date1.getDate());
		String year = String.valueOf(Calendar.getInstance().getWeekYear());
    	
    	String date = year;
		if(month.length()>1) {
			date= date+month;
		} else {
			date= date+"0"+month;
		}
		
		if(day.length()>1) {
			date= date+day;
		} else {
			date= date+"0"+day;
		}
    	System.out.println(date);
    }

}
