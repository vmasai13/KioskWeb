package com.klm.tcs.kiosk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CheckTransactionIds {
	public static void main(String[] args) {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("D:\\chip n pin\\cassandrabatchici\\cassandrabatchici\\src\\main\\resources\\kioskids.properties"));
			String kioskIds = props.getProperty("transactionId");
			String[] result = kioskIds.split(",");
			File file = new File("D:\\chipnpin\\soap\\output\\26feb.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			List<String> transids = new ArrayList<String>();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				for (int x = 0; x < result.length; x++) {
					if (line.contains(result[x])) {
					//	System.out.println("transactionId: "+result[x]);
						transids.add(result[x]);
						break;
					}
				}
				
			}

			for (int x = 0; x < result.length; x++) {
				if(!transids.contains(result[x])) {
						System.out.println(result[x]);
					}
			}
			
			fileReader.close();		
		} catch (IOException e) {
			e.printStackTrace();
		}
}
}
