package com.noname.grocery.cart;

import java.util.List;

import com.noname.grocery.Grocery;
import com.noname.grocery.product.Product;

public class Cart extends Grocery {

	private long cartId;
	private String status;
	private String userId;
	private List<Product> productsList;
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<Product> getProductsList() {
		return productsList;
	}
	public void setProductsList(List<Product> productsList) {
		this.productsList = productsList;
	}
	
	
}
