package com.noname.grocery.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.noname.grocery.Grocery;
import com.noname.grocery.Response;
import com.noname.grocery.product.ProductDAO;
 

@RestController
public class CartService {

	@Autowired
	CartDAO cartDAO;
	@Autowired
	ProductDAO productDAO;
	
	@RequestMapping(method=RequestMethod.POST,value={"/addToCart"})
	public Response<Object> addToCart(@RequestBody AddToCart addToCart){
		return cartDAO.addToCart(addToCart.getUser(),addToCart.getPpId(),addToCart.getCount());
		 
	}
	
	@RequestMapping(method=RequestMethod.POST,value={"/getCart"})
	public Response<CartResponse> getCart(@RequestBody Grocery grocery){
		return  productDAO.getProductsByCart(grocery.getUser());
		 
	}
	@RequestMapping(method=RequestMethod.POST,value={"/placeOrder"})
	public Response<Object> placeOrder(@RequestBody Checkout checkout){
		return  cartDAO.placeOrder(checkout);
		 
	}
}
