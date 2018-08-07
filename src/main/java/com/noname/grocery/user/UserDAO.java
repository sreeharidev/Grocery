package com.noname.grocery.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.noname.grocery.Response;
import com.noname.grocery.product.Product;
import com.noname.grocery.product.ProductPrice;
import com.noname.grocery.shop.ShopRequest;

@Component
public class UserDAO {
	private static final Logger logger = LogManager.getLogger(UserDAO.class);
	JdbcTemplate jdbcTemplate;

	public UserDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Response<User> createUser(User user) {
		String sql = "INSERT INTO user " + "(userId,userName,email,password,session) VALUES (?,?,?,?,?)";
		String sqlSession = "INSERT INTO userSessions " + "(userId,session) VALUES (?,?)";
		Response<User> response = new Response<>();
		String hashed = BCrypt.hashpw("sreehari", BCrypt.gensalt(12));
		user.setPassword(hashed);
		try{
		jdbcTemplate.update(sql, new Object[] { user.getUserId(), user.getUserName(), user.getEmail(),user.getPassword(),user.getSrc() });
		jdbcTemplate.update(sqlSession, new Object[] { user.getUserId(),user.getSession() });
		user.setPassword(null);
	    response.setData(user);
		} catch (DataAccessException de) {
			jdbcTemplate.update(sqlSession, new Object[] { user.getUserId(),user.getSession() });
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		 
		return response;

	}
	
	public Response<List<Product>> getMyFavourites(String userId) {

		 
		
		Response<List<Product>> response = new Response<List<Product>>();
		List<Product> productList = new ArrayList<>();
		String sql = "select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.ppId ppId, pp.shopId shopId,pp.weight weight, pp.price price, pp.discount discount from productsPricing pp,products p, favourites f where  p.productId = pp.productId and pp.ppId = f.ppId and f.userId = ?";
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,
					new Object[] { userId});
			Map<Long,Product> prodCache = new HashMap<>();
			for (Map<String, Object> row : rows) {
				
				long productId = (long) row.get("productId");
				if(prodCache.get(productId) == null){
					Product prod = new Product();
					prodCache.put(productId, prod);
					productList.add(prodCache.get(productId));
					prod.setCategoryId((long) row.get("categoryId"));
					prod.setProductDescription((String) row.get("productDescription"));
					prod.setProductName((String) row.get("productName"));
					prod.setSmallImage((String) row.get("smallImage"));
					prod.setLargeImage((String) row.get("largeImage"));
					prod.setProductId(productId);
					prod.setFavourite(true);
					
					
				} 
				Product product = prodCache.get(productId);
	 
				
			 	ProductPrice productPrice = new ProductPrice();
			 	productPrice.setPpId((long) row.get("ppId"));
				productPrice.setDiscount((double) row.get("discount"));
				productPrice.setPrice((double) row.get("price"));
				productPrice.setWeight((double) row.get("weight"));
				productPrice.setUnit((String) row.get("unit"));
				productPrice.setShopId((long) row.get("shopId"));
				product.getProductPriceList().add(productPrice);
			}

			response.setData(productList);
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			System.out.println(de);

		}
		return response;
	}
}
