package com.noname.grocery.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.noname.grocery.Response;

@RestController
public class ShopService {
	@Autowired
	private ShopDAO shopDAO;
	@RequestMapping(method=RequestMethod.POST,value={"/createShop"})
	public Response<Object> createShop(@RequestBody Shop shop){
		return shopDAO.createShop(shop);
	}
	@RequestMapping(method=RequestMethod.POST,value={"/updateShop"})
	public Response<Object> updateShop(@RequestBody Shop shop){
		return shopDAO.updateShop(shop);
	}
	@RequestMapping(method=RequestMethod.POST,value={"/deleteShop"})
	public Response<Object> deleteShop(@RequestBody Shop shop){
		return shopDAO.deleteShop(shop);
	}
	@RequestMapping(method=RequestMethod.POST,value={"/getAllShops"})
	public Response<List<Shop>> getAllPatentProducts(){
		return shopDAO.getAllshops();
	}
	
	
	
}
