package com.noname.grocery.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.noname.grocery.Response;
import com.noname.grocery.brand.Brand;

@Component
public class ShopDAO {
	private static final Logger logger = LogManager.getLogger(ShopDAO.class);
	private JdbcTemplate jdbcTemplate;

	public ShopDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Response<Object> createShop(Shop shop) {
		String sql = "INSERT INTO shops (shopName,shopAddress,pin,smallImage,largeImage) VALUES (?,?,?,?,?)";
		Response<Object> response = new Response<>();
		try {
			jdbcTemplate.update(sql, new Object[] { shop.getShopName(), shop.getShopAddress(), shop.getPin(),shop.getSmallImage(),shop.getLargeImage() });
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}

		return response;
	}

	public Response<Object> deleteShop(Shop shop) {
		String sql = "delete from shops where shopId= ?";
		Response<Object> response = new Response<>();
		try {
			jdbcTemplate.update(sql, new Object[] { shop.getShopId() });
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);
		}
		return response;
	}

	public Response<List<Shop>> getAllshops() {
		String sql = "select * from shops";
		Response<List<Shop>> response = new Response<List<Shop>>();
		List<Shop> shopList = new ArrayList<Shop>();
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			for (Map<String, Object> row : rows) {
				Shop shop = new Shop();
				shop.setShopId((long) row.get("shopId"));
				shop.setShopName((String) row.get("shopName"));
				shop.setShopAddress((String) row.get("shopAddress"));
				shop.setPin((String) row.get("pin"));
				shop.setSmallImage((String) row.get("smallImage"));
				shop.setLargeImage((String) row.get("largeImage"));
				shopList.add(shop);
			}
			response.setData(shopList);
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);
		}
		return response;
	}
	public Map<Long,Shop> getShopCache() {
		String sql = "select * from shops";
		Map<Long, Shop> response = new HashMap<>();
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			for (Map<String, Object> row : rows) {
				Shop shop = new Shop();
				shop.setShopId((long) row.get("shopId"));
				shop.setShopName((String) row.get("shopName"));
				shop.setShopAddress((String) row.get("shopAddress"));
				shop.setPin((String) row.get("pin"));
				shop.setSmallImage((String) row.get("smallImage"));
				shop.setLargeImage((String) row.get("largeImage"));
				response.put((long) row.get("shopId"), shop);
			} 
		} catch (DataAccessException de) {
			logger.error(de);
		}
		return response;
	}

	public Response<Object> updateShop(Shop shop) {
		Response<Object> response = new Response<>();
		String sql = "update shops set shopName=?,shopAddress=?,pin=?,smallImage=?,largeImage=? where shopId= ? ";
		try {
			jdbcTemplate.update(sql,
					new Object[] { shop.getShopName(), shop.getShopAddress(), shop.getPin(),shop.getSmallImage(),shop.getLargeImage(), shop.getShopId() });
			response.setMessage("updated records for: " + ": " + shop.getShopId());
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);
		}
		return response;
	}
}
