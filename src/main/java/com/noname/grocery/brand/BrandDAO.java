package com.noname.grocery.brand;

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

@Component
public class BrandDAO {
	private static final Logger logger = LogManager.getLogger(BrandDAO.class);
	JdbcTemplate jdbcTemplate;

	public BrandDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Response<Object> createBrand(Brand brand) {
		Response<Object> response = new Response<>();
		String sql = "INSERT INTO brand " + "(brandName,brandIcon) VALUES (?,?)";
		jdbcTemplate.update(sql, new Object[] { brand.getBrandName(), brand.getBrandIcon() });
		return response;
	}

	public Response<List<Brand>> getAllBrands() {
		String sql = "select * from brand";
		Response<List<Brand>> response = new Response<List<Brand>>();
		List<Brand> brandList = new ArrayList<Brand>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : rows) {
			Brand brand = new Brand();
			brand.setBrandName((String) row.get("brandName"));
			brand.setBrandId((long) row.get("brandId"));
			brand.setBrandIcon((String) row.get("brandIcon"));
			brandList.add(brand);
		}
		response.setData(brandList);
		return response;
	}

	public Map<Long, String> getBrandsCache() {
		String sql = "select * from brand";
		Map<Long, String> cache = new HashMap<>();
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			for (Map<String, Object> row : rows) {
				cache.put((long) row.get("brandId"), (String) row.get("brandName"));
			}
		} catch (DataAccessException de) {

			logger.error(de);

		}
		return cache;
	}

	public Response<Object> deleteBrand(Brand brand) {
		Response<Object> response = new Response<>();
		String sql = "delete from brand where brandId= ? ";
		try {
			jdbcTemplate.update(sql, new Object[] { brand.getBrandId() });
			response.setMessage("Deleted records: " + ": " + brand.getBrandId());
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}

	public Response<Object> updateBrand(Brand brand) {
		Response<Object> response = new Response<>();
		String sql = "update brand set brandName=?,brandIcon=? where brandId= ? ";
		try {
			jdbcTemplate.update(sql, new Object[] { brand.getBrandName(), brand.getBrandIcon(), brand.getBrandId() });
			response.setMessage("updated records for: " + ": " + brand.getBrandId());
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}

}
