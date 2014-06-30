package com.unitedwardrobe.uwfinance;

public class Cost {
	
	private String description;
	private String amount;
	
	public Cost() {
	}
	
	public Cost(String description, String amount) {
		this.description = description;
		this.amount = amount;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
