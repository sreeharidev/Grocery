package com.noname.grocery.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.noname.grocery.Response;
import com.noname.grocery.product.Product;

@RestController
public class UserService {
	@Autowired
	UserDAO userDAO;
	@RequestMapping(method=RequestMethod.POST,value={"/createUser"})
	public Response<User> createString(@RequestBody User user){
		return userDAO.createUser(user);
		 
	}
	@RequestMapping(method=RequestMethod.POST,value={"/getMyFavourites"})
	public Response<List<Product>> getMyFavourites(@RequestBody User user) {
		return userDAO.getMyFavourites(user.getUserId());
	}
}
