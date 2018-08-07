package com.noname.grocery.cart;

import com.noname.grocery.Grocery;

public class AddToCart extends Grocery {

	private long ppId;
	private int count;
	public long getPpId() {
		return ppId;
	}
	public void setPpId(long ppId) {
		this.ppId = ppId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
