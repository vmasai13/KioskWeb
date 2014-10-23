package com.klm.tcs.kiosk;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "2012-12-03 05:28:35,722 c68WO4fVGl_BNHsmRgLxaJW  1-CIB217-175 - errorEnd - identificationData pnrSearchSettings currentStateId commonKioskData";
		String[] split = str.split(" ");
		for(String strs : split){
			System.out.println(strs);
		}
		System.out.println(split.length);
		
	}

}
