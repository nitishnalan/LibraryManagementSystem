package org.library.model;

public class Borrowers {
	
	private String id;
	private String ssn;
	private String name;
	private String address;
	private String phone;
	private String totalFine;
	
	
	public String getTotalFine() {
		return totalFine;
	}
	public void setTotalFine(String totalFine) {
		this.totalFine = totalFine;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
