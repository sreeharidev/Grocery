package com.noname.grocery.shop;

import com.noname.grocery.Grocery;

public class ShopRequest extends Grocery {

	private long shopId;
	public long getShopId() {
		return shopId;
	}
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	private long categoryId;
	
	
}
