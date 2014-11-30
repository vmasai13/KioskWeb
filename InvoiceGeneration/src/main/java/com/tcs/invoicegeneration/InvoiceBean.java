package com.tcs.invoicegeneration;

public class InvoiceBean {
	private String wonNumber;
	private String projectName;
	private String milestoneDesc;
	private String milestoneValue;
	private String clientInvoiceDate;
	private String customerInvoiceNumber;
	private String vat;
	private String attention;
	private String reference;
	private String comment;
	private String totalAmount;
	private String totalAmountInWords;
	private String bankName;
	private String bankAccount;
	private String iBANNo;
	private String swiftCode;
	private String item;
	private String quantity;
	private String PONumber;
	private String pricePerUnit;
	private String empName;
	private String empRole;
	private String billingFrom;
	private String billingTo;
	private String units;
	private String projectType;
	private String invoiceType;
	

	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getPricePerUnit() {
		return pricePerUnit;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpRole() {
		return empRole;
	}
	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}
	public String getBillingFrom() {
		return billingFrom;
	}
	public void setBillingFrom(String billingFrom) {
		this.billingFrom = billingFrom;
	}
	public String getBillingTo() {
		return billingTo;
	}
	public void setBillingTo(String billingTo) {
		this.billingTo = billingTo;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public void setPricePerUnit(String pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public String getPONumber() {
		return PONumber;
	}
	public void setPONumber(String PONumber) {
		this.PONumber = PONumber;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public String getAttention() {
		return attention;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTotalAmountInWords() {
		return totalAmountInWords;
	}
	public void setTotalAmountInWords(String totalAmountInWords) {
		this.totalAmountInWords = totalAmountInWords;
	}
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
	public String getWonNumber() {
		return wonNumber;
	}
	public void setWonNumber(String wonNumber) {
		this.wonNumber = wonNumber;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getMilestoneDesc() {
		return milestoneDesc;
	}
	public void setMilestoneDesc(String milestoneDesc) {
		this.milestoneDesc = milestoneDesc;
	}
	public String getMilestoneValue() {
		return milestoneValue;
	}
	public void setMilestoneValue(String milestoneValue) {
		this.milestoneValue = milestoneValue;
	}
	public String getClientInvoiceDate() {
		return clientInvoiceDate;
	}
	public void setClientInvoiceDate(String clientInvoiceDate) {
		this.clientInvoiceDate = clientInvoiceDate;
	}
	public String getCustomerInvoiceNumber() {
		return customerInvoiceNumber;
	}
	public void setCustomerInvoiceNumber(String customerInvoiceNumber) {
		this.customerInvoiceNumber = customerInvoiceNumber;
	}

	public String toString() {
		return this.getWonNumber() + "\t"
		+ this.getProjectName() + "\t"
		+ this.getMilestoneDesc() + "\t"
		+ this.getMilestoneValue() + "\t"
		+ this.getClientInvoiceDate() + "\t"
		+ this.getCustomerInvoiceNumber() + "\t"
		+ this.getVat() + "\t"
		+ this.getAttention() + "\t"
		+ this.getReference() + "\t"
		+ this.getComment() + "\t"
		+ this.getTotalAmount() + "\t"
		+ this.getTotalAmountInWords() + "\t"
		+ this.getBankName() + "\t"
		+ this.getBankAccount() + "\t"
		+ this.getiBANNo() + "\t"
		+ this.getSwiftCode() + "\t"
		+ this.getItem() + "\t"
		+ this.getQuantity() + "\t"
		+ this.getPONumber();
	}
}	
