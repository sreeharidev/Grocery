package com.noname.grocery.shop;

public class Stock {

	private long shopId;
	private long productId;
	private long subProductId;
	private String isAvailable;
	
	public long getShopId() {
		return shopId;
	}
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getSubProductId() {
		return subProductId;
	}
	public void setSubProductId(long subProductId) {
		this.subProductId = subProductId;
	}
	public String getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	
}
