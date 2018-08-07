package com.noname.grocery.category;

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
public class CategoryDAO {

	private static final Logger logger = LogManager.getLogger(CategoryDAO.class);
	JdbcTemplate jdbcTemplate;
	public CategoryDAO(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Response<Object> createCategory(Category category){
		Response<Object> response = new Response<>();
		String sql = "INSERT INTO productCategory " +
				"(categoryName) VALUES (?)";
		try{
		jdbcTemplate.update(sql, new Object[] {category.getCategoryName()});
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}
	public Response<Object> deleteCategory(Category category){
		String sql = "delete from productCategory where categoryId= ?";
		Response<Object> response = new Response<>();
		try{
		jdbcTemplate.update(sql, new Object[] {category.getCategoryId()});
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		
		return response;
	}
	public Response<Object> updateCategory(Category category){
		Response<Object> response = new Response<>();
		String sql = "update productCategory set categoryName=? where categoryId= ? ";
		try{
		jdbcTemplate.update(sql, new Object[] {category.getCategoryName(),category.getCategoryId()});
		response.setMessage("updated records for: "+": "+category.getCategoryId());
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	} 
	public Response<Object> createSubCategory(SubCategory subCategory){
		Response<Object> response = new Response<>();
		String sql = "INSERT INTO productSubCategory " +
				"(categoryId,subCategoryName) VALUES (?,?)";
		try{
		jdbcTemplate.update(sql, new Object[] {subCategory.getCategoryId(),subCategory.getSubCategoryName()});
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}
	public Response<Object> updateSubCategory(SubCategory subCategory){
		Response<Object> response = new Response<>();
		String sql = "update productSubCategory set categoryId=?,subCategoryName=? where subCategoryId=?";
		try{
		jdbcTemplate.update(sql, new Object[] {subCategory.getCategoryId(),subCategory.getSubCategoryName(),subCategory.getSubCategoryId()});
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}
	public Response<Object> deleteSubCategory(SubCategory subCategory){
		Response<Object> response = new Response<>();
		String sql = "delete from productSubCategory where subCategoryId=?";
		try{
		jdbcTemplate.update(sql, new Object[] {subCategory.getSubCategoryId()});
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}
	// 
	
	
	public Response<List<Category>> getCategoryList(){
		String sql = "select * from productCategory";
		Response<List<Category>> response = new Response<List<Category>> ();
		List<Category> categoryList = new ArrayList<Category>();
		try{
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> row:rows){
			Category category = new Category();
			category.setCategoryId((long)row.get("categoryId")); //TODO convert into wrapper type
			category.setCategoryName((String)row.get("categoryName"));
			categoryList.add(category);
		}
		response.setData(categoryList);
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}
	//TODO try handling exception properly
	public Map<Long,String> getCategoryCache(){
		Map<Long,String> catCache = new HashMap<>();
		String sql = "select * from productCategory";
		 System.out.println("Init....sadf");
		try{
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> row:rows){
			System.out.println((long)row.get("categoryId")+":.@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:"+(String)row.get("categoryName"));
			catCache.put((long)row.get("categoryId"),(String)row.get("categoryName")); //TODO convert into wrapper type
		}
		} catch (DataAccessException de) {
			 System.out.println(de);
			logger.error(de);

		}
		return catCache;
	}
	
	public Response<List<SubCategory>> getSubCategoryList(){
		String sql = "select sc.categoryId categoryId, sc.subCategoryId subCategoryId, sc.subCategoryName subCategoryName,c.categoryName categoryName from productSubCategory sc,productCategory c  where sc.categoryId = c.categoryId";
		Response<List<SubCategory>> response = new Response<List<SubCategory>> ();
		List<SubCategory> categoryList = new ArrayList<SubCategory>();
		try{
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> row:rows){
			SubCategory subCategory = new SubCategory();
			
			subCategory.setCategoryId((long)row.get("categoryId")); //TODO convert into wrapper type
			subCategory.setCategoryName((String)row.get("categoryName"));
			subCategory.setSubCategoryId((long)row.get("subCategoryId"));
			subCategory.setSubCategoryName((String)row.get("subCategoryName"));
			categoryList.add(subCategory);
		}
		response.setData(categoryList);
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}
	//TODO try handling exception properly
	public Map<Long,SubCategory> getSubCategoryCache(){
		Map<Long,SubCategory>  subCache = new HashMap<>();
		String sql = "select sc.categoryId categoryId, sc.subCategoryId subCategoryId, sc.subCategoryName subCategoryName,c.categoryName categoryName from productSubCategory sc,productCategory c  where sc.categoryId = c.categoryId";
		try{
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> row:rows){
			SubCategory subCategory = new SubCategory();
			subCategory.setCategoryId((long)row.get("categoryId"));  
			subCategory.setSubCategoryId((long)row.get("subCategoryId"));
			subCategory.setSubCategoryName((String)row.get("subCategoryName"));
			subCache.put(subCategory.getSubCategoryId(), subCategory);
		}
		} catch (DataAccessException de) {
			 
			logger.error(de);

		}
		return subCache;
	}
	public Response<List<SubCategory>> getSubCategoryByCategoryId(Category category){
		String sql = "select sc.categoryId categoryId, sc.subCategoryId subCategoryId, sc.subCategoryName subCategoryName,c.categoryName categoryName from productSubCategory sc,productCategory c  where sc.categoryId = ?";
		Response<List<SubCategory>> response = new Response<List<SubCategory>> ();
		List<SubCategory> categoryList = new ArrayList<SubCategory>();
		try{
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[] {category.getCategoryId()});
		for(Map<String, Object> row:rows){
			SubCategory subCategory = new SubCategory();
			
			subCategory.setCategoryId((long)row.get("categoryId")); //TODO convert into wrapper type
			subCategory.setCategoryName((String)row.get("categoryName"));
			subCategory.setSubCategoryId((long)row.get("subCategoryId"));
			subCategory.setSubCategoryName((String)row.get("subCategoryName"));
			categoryList.add(subCategory);
		}
		response.setData(categoryList);
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}
}
