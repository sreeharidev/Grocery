package com.noname.grocery.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeResponse {

	private String priority;
	private Map<String,List<Product>> products = new HashMap<>();
	private Map<String,String> categories = new HashMap<>();
	private long shopId;
	private int cartCount;
	private String banner;
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Map<String, List<Product>> getProducts() {
		return products;
	}
	public void setProducts(Map<String, List<Product>> products) {
		this.products = products;
	}
	public Map<String,String> getCategories() {
		return categories;
	}
	public void setCategories(Map<String,String> categories) {
		this.categories = categories;
	}
	public long getShopId() {
		return shopId;
	}
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	public int getCartCount() {
		return cartCount;
	}
	public void setCartCount(int cartCount) {
		this.cartCount = cartCount;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	
}
