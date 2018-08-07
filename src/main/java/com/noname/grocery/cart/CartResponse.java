package com.noname.grocery.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.noname.grocery.product.Product;

public class CartResponse {

	private String priority;
	private Map<String,List<Product>> products = new HashMap<>();
	private Map<String,String> shops = new HashMap<>();
	private long shopId;
	private int cartCount;
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
	public Map<String,String> getShops() {
		return shops;
	}
	public void setShops(Map<String,String> shops) {
		this.shops = shops;
	}
	
}
