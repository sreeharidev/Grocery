package com.noname.grocery.cart;

import com.noname.grocery.Grocery;

public class Checkout extends Grocery {

	private String name;
	private String address;
	private String email;
	private boolean storePick;
	private boolean cashOnDelivery;
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
	public String getEmail() {
		return email;
	}
	public boolean isStorePick() {
		return storePick;
	}
	public void setStorePick(boolean storePick) {
		this.storePick = storePick;
	}
	public boolean isCashOnDelivery() {
		return cashOnDelivery;
	}
	public void setCashOnDelivery(boolean cashOnDelivery) {
		this.cashOnDelivery = cashOnDelivery;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
