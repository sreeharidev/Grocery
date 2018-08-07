package com.noname.grocery.favourite;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.noname.grocery.Response;
import com.noname.grocery.shop.Shop;
@Component
public class FavouriteDAO {
	 
	private JdbcTemplate jdbcTemplate;
	
	public FavouriteDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Response<Object> addFavourite(Favourite favourite){
		String sql = "INSERT INTO favourites (userId,ppId) VALUES (?,?)";
		Response<Object> response = new Response<>();
		try {
			jdbcTemplate.update(sql, new Object[] { favourite.getUser(),favourite.getPpId() });
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			 System.out.println("Exception while adding favourite");
			 System.out.println(de);

		}

		return response;
	}
	public Response<Object> removeFavourite(Favourite favourite){
		String sql = "delete from  favourites where userId=? and ppId= ?";
		Response<Object> response = new Response<>();
		try {
			jdbcTemplate.update(sql, new Object[] { favourite.getUser(),favourite.getPpId() });
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			 System.out.println("Exception while adding favourite");
			 System.out.println(de);

		}

		return response;
	}
	
	//TODO add shop Id 
	
	public Response<List<Favourite>> getAllFavourites(String userId) {
		String sql = "select * from favourites where userId=?";
		Response<List<Favourite>> response = new Response<List<Favourite>>();
		List<Favourite> favouriteList = new ArrayList<Favourite>();
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[]{userId});
			for (Map<String, Object> row : rows) {
				Favourite favourite = new Favourite();
				favourite.setPpId((long) row.get("ppId"));
				favourite.setUserId((String)row.get("userId"));
				favouriteList.add(favourite);
			}
			response.setData(favouriteList);
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			
		}
		return response;
	}
	//TODO handle expception
	public Set<Long> getAllFavoiritePPID(String userId) {
		String sql = "select * from favourites where userId=?";
		 
		Set<Long> favouriteSet = new HashSet<Long>();
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[]{userId});
			for (Map<String, Object> row : rows) {
				favouriteSet.add((long) row.get("ppId"));
				 
			}
			 
		} catch (DataAccessException de) {
			 
			
		}
		return favouriteSet;
	}

}
