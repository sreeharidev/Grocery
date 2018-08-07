package com.noname.grocery.favourite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.noname.grocery.Response;

 

@RestController
public class FavouriteService {
	@Autowired
	private FavouriteDAO favouriteDAO;
	
	@RequestMapping(method=RequestMethod.POST,value={"/addFavourite"})
	public Response<Object> addFavourite(@RequestBody Favourite favourite){
		return favouriteDAO.addFavourite(favourite);
	}
	
	@RequestMapping(method=RequestMethod.POST,value={"/removeFavourite"})
	public Response<Object> removeFavourite(@RequestBody Favourite favourite){
		return favouriteDAO.removeFavourite(favourite);
	}
	
}
