package com.noname.grocery.category;
import java.util.Map;

 
import org.springframework.stereotype.Component;
//TODO check the singleton object initialization
@Component
public class CategoryCache {
	 
	private CategoryDAO categoryDAO; 
	private Map<Long, String> cache = null;
	private Map<Long, SubCategory> subCache = null;
	public CategoryCache(CategoryDAO categoryDAO){
		this.categoryDAO = categoryDAO;
		init();
	}
	public void init(){
		System.out.println("Init cache....@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		if(cache == null){
			System.out.println("Init cache....@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			cache = categoryDAO.getCategoryCache();
		}
		if(subCache == null){
			subCache = categoryDAO.getSubCategoryCache();
		}
	}
	public  String getCategoryName(long categoryId){
		return cache.get(categoryId);
	}
	public String getSubCategoryName(long subCategoryId){
	 
		return subCache.get(subCategoryId) != null ? subCache.get(subCategoryId).getSubCategoryName():""+subCategoryId;
	}
}
