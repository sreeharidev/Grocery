package com.noname.grocery.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.noname.grocery.Response;
import com.noname.grocery.shop.Shop;

@RestController
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;
	
	@RequestMapping(method=RequestMethod.POST,value={"/createCategory"})
	public Response<Object> createCategory(@RequestBody Category category){
		return categoryDAO.createCategory(category);
	}
	@RequestMapping(method=RequestMethod.POST,value={"/updateCategory"})
	public Response<Object> updateCategory(@RequestBody Category category){
		return categoryDAO.updateCategory(category);
	}
	@RequestMapping(method=RequestMethod.POST,value={"/deleteCategory"})
	public Response<Object> deleteCategory(@RequestBody Category category){
		return categoryDAO.deleteCategory(category);
	}
	@RequestMapping(method=RequestMethod.POST,value={"/createSubCategory"})
	public Response<Object> createSubCategory(@RequestBody SubCategory subCategory){
		return categoryDAO.createSubCategory(subCategory);
	}
	@RequestMapping(method=RequestMethod.POST,value={"/deleteSubCategory"})
	public Response<Object> deleteSubCategory(@RequestBody SubCategory subCategory){
		return categoryDAO.deleteSubCategory(subCategory);
	}
	@RequestMapping(method=RequestMethod.POST,value={"/updateSubCategory"})
	public Response<Object> updateSubCategory(@RequestBody SubCategory subCategory){
		return categoryDAO.updateSubCategory(subCategory);
	}
	@RequestMapping(method=RequestMethod.POST,value={"/getAllCategories"})
	public Response<List<Category>> getAllCategories(){
		return categoryDAO.getCategoryList();
	}
	@RequestMapping(method=RequestMethod.POST,value={"/getAllSubCategories"})
	public Response<List<SubCategory>> getAllSubCategories(){
		return categoryDAO.getSubCategoryList();
	}
	@RequestMapping(method=RequestMethod.POST,value={"/getSubCategoryByCategoryId"})
	public Response<List<SubCategory>> getSubCategoryByCategoryId(@RequestBody Category category){
		return categoryDAO.getSubCategoryByCategoryId(category);
	}
}
