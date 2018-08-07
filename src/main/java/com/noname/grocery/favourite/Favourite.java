package com.noname.grocery.favourite;

import com.noname.grocery.Grocery;

public class Favourite extends Grocery {

	private long shopId;
	public long getShopId() {
		return shopId;
	}
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	public long getPpId() {
		return ppId;
	}
	public void setPpId(long ppId) {
		this.ppId = ppId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private long ppId;
	private String userId;
	
	
}
