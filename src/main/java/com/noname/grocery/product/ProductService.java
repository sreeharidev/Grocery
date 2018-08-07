package com.noname.grocery.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.noname.grocery.Response;
import com.noname.grocery.shop.Shop;
import com.noname.grocery.shop.ShopRequest;

@RestController
public class ProductService {
	@Autowired
	private ProductDAO productDAO;
	

	@RequestMapping(method=RequestMethod.POST,value={"/createProduct"})
	public Response<Object> createProduct(@RequestBody Product product){
		return productDAO.createProduct(product);
	}
	@RequestMapping(method=RequestMethod.POST,value={"/updateProduct"})
	public Response<Object> updateProduct(@RequestBody Product product){
		return productDAO.updateProduct(product);
	}
	
	@RequestMapping(method=RequestMethod.POST,value={"/createProductPrice"})
	public  Response<Object> createProductPrice(@RequestBody ProductPrice productPrice){
		return productDAO.createProductPrice(productPrice);
	}
	
	@RequestMapping(method=RequestMethod.POST,value={"/getProductPrice"})
	public Response<Product> getProductPrice( @RequestBody Product product){
		return productDAO.getProductAndPrice(product.getProductId());
	}
	@RequestMapping(method=RequestMethod.POST,value={"/getProductDetails"})
	public Response<ProductDetails> getProductDetails( @RequestBody Product product){
		return productDAO.getProductsDetails(product.getProductId());
	}
	@RequestMapping(method=RequestMethod.POST,value={"/getProductsByCategory"})
	public Response<List<Product>> getProductsByCategory( @RequestBody ShopRequest shop){ 
		return productDAO.getProductsByCategory(shop);
	}
	@RequestMapping(method=RequestMethod.POST,value={"/getProductsByCategoryId"})
	public Response<List<Product>> getProductsByCategoryId( @RequestBody Product product){
		return productDAO.getProductsByCategory( product.getCategoryId());
	}
	@RequestMapping(method=RequestMethod.POST,value={"/getProductsByShop"})
	public Response<List<Product>> getProductsByShop( @RequestBody Shop shop){
		return productDAO.getProductsByShop( shop.getShopId());
	}
	@RequestMapping(method=RequestMethod.POST,value={"/getProductPriceByShop"})
	public Response<List<ProductPrice>> getProductPriceByShop( @RequestBody Shop shop){
		return productDAO.getProductPriceByShop( shop.getShopId());
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value={"/getProductsByShopHomeForMobile"})
	public Response<HomeResponse> getProductsByShopHomeForMobile( @RequestBody Shop shop){
		System.out.println("Shop Home is called by: "+shop.getUser());
		System.out.println("Session: "+shop.getSession());
		System.out.println("isLoggedIn:"+shop.getLoggedIn());
		System.out.println("userName:"+shop.getUserName());
		return productDAO.getProductsByShopHomeForMobile( shop);
	}
	
	@RequestMapping(method=RequestMethod.POST,value={"/getProductsByShopHome"})
	public Response<HomeResponse> getProductsByShopHome( @RequestBody Shop shop){
		return productDAO.getProductsByShopHome( shop.getShopId());
	}
	
 	@RequestMapping(method=RequestMethod.POST,value={"/getAllProducts"})
	public Response<List<Product>> getAllProducts(){
		return productDAO.getAllProducts();
	}
	
	@RequestMapping(method=RequestMethod.POST,value={"/deleteProduct"})
	public Response<Object> deleteParentProduct( @RequestBody Product product){
		return productDAO.deleteProduct(product);
	}
	
	
}
