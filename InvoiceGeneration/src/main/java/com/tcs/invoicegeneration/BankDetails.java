package com.tcs.invoicegeneration;

import java.math.BigDecimal;

public class BankDetails {
	String bankName;
	String bankAccount;
	String iBANNo;
	String swiftCode;
	BigDecimal vatPercentage;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getiBANNo() {
		return iBANNo;
	}

	public void setiBANNo(String iBANNo) {
		this.iBANNo = iBANNo;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public BigDecimal getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(BigDecimal vatPercentage) {
		this.vatPercentage = vatPercentage;
	}

}
