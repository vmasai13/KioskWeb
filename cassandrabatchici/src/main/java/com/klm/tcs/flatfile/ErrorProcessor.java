package com.klm.tcs.flatfile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.batch.item.ItemProcessor;

import com.klm.tcs.kiosk.RawRecord;

public class ErrorProcessor  implements ItemProcessor<String, ErrorCode> {

	@Override
	public ErrorCode process(String rawString) throws Exception {
		Properties props = new Properties();
		//InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("C:/Nidhin/personal/test/kioskids.properties");
		props.load(new FileInputStream("D:\\chipnpin\\cassandrabatchici\\src\\main\\resources\\kioskids.properties"));
		ErrorCode errorcode = null;
		System.out.println("kkkkkkk");
		if (null != rawString) {
			String station ="";
			String[] split = rawString.split(" ");
			errorcode = new ErrorCode();
			errorcode.setKioskId(split[5]);
			station = props.getProperty((split[5]));
			String temp = split[10].replaceAll(":","");
			
			errorcode.setStation(station);
			errorcode.setError(temp.trim());
		}
		
		return errorcode;
		
	}

}
