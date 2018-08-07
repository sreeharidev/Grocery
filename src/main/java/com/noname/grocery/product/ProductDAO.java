package com.noname.grocery.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.Statement;
import com.noname.grocery.Response;
import com.noname.grocery.brand.BrandCache;
import com.noname.grocery.cart.CartDAO;
import com.noname.grocery.cart.CartResponse;
import com.noname.grocery.cart.Checkout;
import com.noname.grocery.category.CategoryCache;
import com.noname.grocery.favourite.FavouriteDAO;
import com.noname.grocery.shop.Shop;
import com.noname.grocery.shop.ShopCache;
import com.noname.grocery.shop.ShopRequest;
import com.noname.grocery.user.User;
import com.noname.grocery.user.UserDAO;

@Component
public class ProductDAO {
	private static final Logger logger = LogManager.getLogger(ProductDAO.class);
	@Autowired CategoryCache categoryCache;
	@Autowired ShopCache shopCache;
	@Autowired
	private BrandCache brandCache;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired CartDAO cartDAO;
	@Autowired UserDAO userDAO;
	@Autowired FavouriteDAO favouriteDAO;
	public ProductDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	


	public Response<Object> createProduct(Product product) {
		Response<Object> response = new Response<>();
		final KeyHolder holder = new GeneratedKeyHolder();
		String sql = "INSERT INTO products "
				+ "(categoryId,subCategoryId,productName,productDescription,smallImage,largeImage) VALUES (?,?,?,?,?,?)";
		try {
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, product.getCategoryId());
					ps.setLong(2, product.getSubCategoryId());
					ps.setString(3, product.getProductName());
					ps.setString(4, product.getProductDescription());
					ps.setString(5, product.getSmallImage());
					ps.setString(6, product.getLargeImage());
					return ps;
				}
			}, holder);
			response.setMessage("Product is created successfully :" + holder.getKey().longValue());
			product.getProductDetails().setProductId(holder.getKey().longValue());
			createProductDetails(product.getProductDetails());
		} catch (DataAccessException de) {
			response.setStatus("ERROR");
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}
	private void createProductDetails(ProductDetails productDetails){
	 
		String sql = "INSERT INTO productDetails " + "(productId,details,ingrediants,directions,warning) VALUES (?,?,?,?,?)";
		try {
			jdbcTemplate.update(sql, new Object[] { productDetails.getProductId(), productDetails.getDetails(),
					productDetails.getIngrediants(), productDetails.getDirections(),productDetails.getWarning() });
		} catch (DataAccessException de) {
			 
			logger.error(de);

		}
	}
	private void updateProductDetails(ProductDetails productDetails){
		 
		String sql = "update productDetails " + "set details=?,ingrediants=?,directions=?,warning=? where productId=?";
		try {
			jdbcTemplate.update(sql, new Object[] { productDetails.getDetails(),
					productDetails.getIngrediants(), productDetails.getDirections(),productDetails.getWarning(),productDetails.getProductId() });
		} catch (DataAccessException de) {
			 
			logger.error(de);

		}
	}
	public Response<Object> updateProduct(Product product) {
		Response<Object> response = new Response<>();
		final KeyHolder holder = new GeneratedKeyHolder();
		String sql = "update products "
				+ " set categoryId=?,subCategoryId=?,productName=?,productDescription=?,smallImage=?,largeImage=? where productId=?";
		try {
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, product.getCategoryId());
					ps.setLong(2, product.getSubCategoryId());
					ps.setString(3, product.getProductName());
					ps.setString(4, product.getProductDescription());
					ps.setString(5, product.getSmallImage());
					ps.setString(6, product.getLargeImage());
					ps.setLong(7, product.getProductId());
					return ps;
				}
			}, holder);
			product.getProductDetails().setProductId(product.getProductId());
			updateProductDetails(product.getProductDetails());
			response.setMessage("Product is created successfully :" + holder.getKey().longValue());
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}

	public Response<Object> createProductPrice(ProductPrice productPrice) {
		Response<Object> response = new Response<>();
		String sql = "INSERT INTO productsPricing " + "(shopId,productId,weight,unit,price,discount,stock) VALUES (?,?,?,?,?,?,?)";
		try {
			jdbcTemplate.update(sql, new Object[] { productPrice.getShopId(), productPrice.getProductId(),
					productPrice.getPrice(),productPrice.getUnit(),productPrice.getPrice(), productPrice.getDiscount(),productPrice.getDiscount() });
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}
 

	public Response<Object> deleteProduct(Product product) {
		Response<Object> response = new Response<>();
		String sql = "delete from products where productId= ? ";
		try {
			int rows = jdbcTemplate.update(sql, new Object[] { product.getProductId() });
			response.setMessage("Deleted records: " + rows + ": " + product.getProductId());
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}

	public Response<Product> getProductAndPrice(long productId) {
		Response<Product> response = new Response<Product>();
		Product product = new Product();
		List<ProductPrice> priceList = new ArrayList<>();
		String sql = "select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.weight weight, pp.price price, pp.discount discount from products p, productsPricing pp where p.productId = ? and p.productId = pp.productId;";
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { new Long(productId) });
			for (Map<String, Object> row : rows) {
				product.setProductId((long) row.get("productId"));
				product.setCategoryId((long) row.get("categoryId"));
				product.setProductName((String) row.get("productName"));
				product.setProductDescription((String) row.get("productDescription"));
				product.setSmallImage((String) row.get("smallImage"));
				product.setLargeImage((String) row.get("largeImage"));
				ProductPrice productPrice = new ProductPrice();
				productPrice.setProductId((long) row.get("productId"));
				productPrice.setDiscount((double) row.get("discount"));
				productPrice.setPrice((double) row.get("price"));
				productPrice.setWeight((double) row.get("weight"));
				priceList.add(productPrice);
			}
			response.setData(product);
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}
	
	
	public Response<List<ProductPrice>> getProductPriceByShop(long shopId) {
		Response<List<ProductPrice>> response = new Response<List<ProductPrice>>();
		 
		List<ProductPrice> priceList = new ArrayList<>();
		String sql = "select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.weight weight, pp.price price, pp.discount discount from products p, productsPricing pp where pp.shopId = ? and p.productId = pp.productId;";
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { new Long(shopId) });
			for (Map<String, Object> row : rows) {
				 
				ProductPrice productPrice = new ProductPrice();
				productPrice.setProductId((long) row.get("productId"));
				productPrice.setProductName((String)row.get("productName"));
				productPrice.setCategoryId((long)row.get("categoryId"));
				productPrice.setDiscount((double) row.get("discount"));
				productPrice.setPrice((double) row.get("price"));
				if(row.get("weight")!=null){
					productPrice.setWeight((double) row.get("weight"));
				}
				if(row.get("stock")!=null){
					productPrice.setStock((int)row.get("stock"));
				}
				productPrice.setUnit((String)row.get("unit"));
				
				priceList.add(productPrice);
			}
			 response.setData(priceList);
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}

	public Response<List<Product>> getProductsByCategory(ShopRequest shop) {

		long shopId = shop.getShopId();
		long categoryId = shop.getCategoryId();
		System.out.println("Call:" + shopId + "::" + categoryId+":"+shop.getUser());
		
		Response<List<Product>> response = new Response<List<Product>>();
		List<Product> productList = new ArrayList<>();
		String sql = "select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.ppId ppId, pp.shopId shopId,pp.weight weight, pp.price price, pp.discount discount from productsPricing pp,products p where pp.shopId = ? and  p.categoryId= ? and  p.productId = pp.productId";
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,
					new Object[] { new Long(shopId), new Long(categoryId) });
			Set<Long> favouriteList =favouriteDAO.getAllFavoiritePPID(shop.getUser());
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
					
				} 
				Product product = prodCache.get(productId);
	 
				if(favouriteList.contains((long) row.get("ppId"))){
					product.setFavourite(true);
				}
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

	public Response<List<Product>> getProductsByShop(long shopId) {

		Response<List<Product>> response = new Response<List<Product>>();
		List<Product> productList = new ArrayList<>();
		String sql = "select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage, pp.price price, pp.discount discount,pp.shopId shopId from productsPricing pp,products p where pp.shopId = ? and  p.productId = pp.productId";
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { new Long(shopId) });
			for (Map<String, Object> row : rows) {
				Product product = new Product();
				productList.add(product);
				product.setProductId((long) row.get("productId"));
				product.setCategoryId((long) row.get("categoryId"));
				product.setProductDescription((String) row.get("productDescription"));
				product.setProductName((String) row.get("productName"));
				product.setSmallImage((String) row.get("smallImage"));
				product.setLargeImage((String) row.get("largeImage"));
				 
			}
			response.setData(productList);
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}

	public String getOrder(long shopId) {
		String result = "";
		String sql = "select * from shopsHome where shopId=? order by priority";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { new Long(shopId) });
		int i = 0;
		for (Map<String, Object> row : rows) {
			if (i != 0) {
				result += ",";
			}
			result += ((long) row.get("categoryId"));

			i++;

		}
		return result;
	}

	public Response<HomeResponse> getProductsByShopHome(long shopId) {
		String order = getOrder(shopId);
		Map<String, List<Product>> products = new HashMap<>();
		Response<HomeResponse> res = new Response<HomeResponse>();
		HomeResponse response = new HomeResponse();
		res.setData(response);
		response.setPriority(order);
		response.setProducts(products);
		String sql = "select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.weight weight, pp.price price, pp.discount discount,pp.shopId shopId from productsPricing pp,products p where pp.shopId = ? and  p.productId = pp.productId";
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { new Long(shopId) });
			Map<String, String> catNameCache = new HashMap<>();
			response.setCategories(catNameCache);
			for (Map<String, Object> row : rows) {
				String catId = "" + (long) row.get("categoryId");
				if (products.get(catId) == null) {
					List<Product> pList = new ArrayList<>();
					products.put(catId, pList);
					catNameCache.put(catId, categoryCache.getCategoryName((long) row.get("categoryId")));
				}
				Product product = new Product();
				products.get(catId).add(product);
				product.setProductId((long) row.get("productId"));
				product.setCategoryId((long) row.get("categoryId"));
				product.setProductDescription((String) row.get("productDescription"));
				product.setProductName((String) row.get("productName"));
				product.setSmallImage((String) row.get("smallImage"));
				product.setLargeImage((String) row.get("largeImage"));
				 
			}
		} catch (DataAccessException de) {
			System.out.println(de);
			res.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return res;
	}
	
	//TODO need to rewrite in a better way
	public Response<HomeResponse> getProductsByShopHomeForMobile(Shop shop) {
		long shopId = shop.getShopId();
		String order = getOrder(shopId);
		Map<String, List<Product>> products = new HashMap<>();
		Response<HomeResponse> res = new Response<HomeResponse>();
		HomeResponse response = new HomeResponse();
		response.setShopId(shopId);
		res.setData(response);
		response.setPriority(order);
		response.setProducts(products);
		String sql = "select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.ppId ppId, pp.weight weight, pp.price price, pp.discount discount,pp.shopId shopId,pp.unit unit from productsPricing pp,products p where pp.shopId = ? and  pp.productId = p.productId";
		try {
			//TODO check all the users logged in or not
			if(!shop.getLoggedIn()){
				User user = new User();
				user.setUserId(shop.getUser());
				user.setSession(shop.getSession());
				user.setSrc("Mobile");
				user.setUserName(shop.getUserName());;
				userDAO.createUser(user);
				System.out.println("User created for first time");
			}else{
				System.out.println("Existing user request");
				
			}
			Set<Long> favouriteList =favouriteDAO.getAllFavoiritePPID(shop.getUser());
			response.setCartCount(cartDAO.getCartCount(shop.getUser()));
			response.setBanner(shopCache.getBanner(shopId));
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { new Long(shopId) });
			Map<String, String> catNameCache = new HashMap<>();
			Map<String,Product> prodCache = new HashMap<>();
			response.setCategories(catNameCache);
			 
			for (Map<String, Object> row : rows) {
				String catId = ""+ row.get("categoryId");
				String prodCId = catId+"-"+row.get("productId");
				
				if (products.get(catId) == null) {
					List<Product> pList = new ArrayList<>();
					products.put(catId, pList);
					System.out.println((long) row.get("categoryId"));
					catNameCache.put(catId, categoryCache.getCategoryName((long) row.get("categoryId")));
				}
				if(prodCache.get(prodCId)== null){
					Product p = new Product();
					prodCache.put(prodCId,p);
					products.get(catId).add(p);
				}
				Product product =  prodCache.get(prodCId);
				 
					ProductPrice productPrice = new ProductPrice();
					 
					product.setProductId((long) row.get("productId"));
					product.setCategoryId((long) row.get("categoryId"));
					product.setProductDescription((String) row.get("productDescription"));
					product.setProductName((String) row.get("productName"));
					product.setSmallImage((String) row.get("smallImage"));
					product.setLargeImage((String) row.get("largeImage"));
					
					productPrice.setPpId((long) row.get("ppId"));
					productPrice.setDiscount((double) row.get("discount"));
					productPrice.setPrice((double) row.get("price"));
					productPrice.setWeight((double) row.get("weight"));
					productPrice.setUnit((String) row.get("unit"));
					productPrice.setShopId((long) row.get("shopId"));
					product.getProductPriceList().add(productPrice);
					if(favouriteList.contains(productPrice.getPpId())){
						product.setFavourite(true);
					}
				}
		 
		} catch (DataAccessException de) {
			res.setErrorMessage("Exception happend");
			System.out.println(de);
			logger.error(de);

		}
		return res;
	}
	public Response<List<Product>> getProductsByCategory(long categoryId) {

		Response<List<Product>> response = new Response<List<Product>>();
		List<Product> productList = new ArrayList<>();
		String sql = "select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage from products p where p.categoryId= ? ";
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { new Long(categoryId) });
			for (Map<String, Object> row : rows) {
				Product product = new Product();
				productList.add(product);
				product.setProductId((long) row.get("productId"));
				product.setCategoryId((long) row.get("categoryId"));
				product.setProductDescription((String) row.get("productDescription"));
				product.setProductName((String) row.get("productName"));
				product.setSmallImage((String) row.get("smallImage"));
				product.setLargeImage((String) row.get("largeImage"));
				 

			}

			response.setData(productList);
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend"+de.getMessage());
			logger.error(de);

		}
		return response;
	}

	

	public Response<List<Product>> getAllProducts() {

		Response<List<Product>> response = new Response<List<Product>>();
		List<Product> productList = new ArrayList<>();
		String sql = "select * from products p, productDetails pd where p.productId = pd.productId";
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			for (Map<String, Object> row : rows) {
				Product product = new Product();
				productList.add(product);
				product.setProductId((long) row.get("productId"));
				product.setCategoryId((long) row.get("categoryId"));
				product.setCategoryName(categoryCache.getCategoryName(product.getCategoryId()));
				product.setSubCategoryId((long) row.get("subCategoryId"));
				product.setProductDescription((String) row.get("productDescription"));
				product.setProductName((String) row.get("productName"));
				product.setSmallImage((String) row.get("smallImage"));
				product.setLargeImage((String) row.get("largeImage"));
				ProductDetails pd = new ProductDetails();
				product.setProductDetails(pd);
				pd.setProductId(product.getProductId());
				pd.setDetails((String) row.get("details"));
				pd.setDirections((String) row.get("directions"));
				pd.setIngrediants((String) row.get("ingrediants"));
				pd.setWarning((String) row.get("warning")); 
			}
			response.setData(productList);
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}
	
	public Response<ProductDetails> getProductsDetails(long productId) {

		Response<ProductDetails> response = new Response<ProductDetails>();
		ProductDetails productDetails = new ProductDetails();
		String sql = "select * from productDetails where productId=?";
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { new Long(productId) });
			for (Map<String, Object> row : rows) {
				productDetails.setProductId((long) row.get("productId"));
				productDetails.setDetails((String)row.get("details"));
				productDetails.setDirections((String)row.get("directions"));
				productDetails.setIngrediants((String)row.get("ingrediants"));
				productDetails.setWarning((String)row.get("warning"));
				
			}

			response.setData(productDetails);
		} catch (DataAccessException de) {
			response.setErrorMessage("Exception happend");
			logger.error(de);

		}
		return response;
	}
	
	
	public Response<CartResponse> getProductsByCart(String userId) {
	 	Map<String, List<Product>> products = new HashMap<>();
		Response<CartResponse> res = new Response<CartResponse>();
		CartResponse response = new CartResponse();
	 	res.setData(response);
	 	response.setProducts(products);
		String sql = "select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.ppId ppId, pp.weight weight, pp.price price, pp.discount discount,pp.shopId shopId,pp.unit unit from productsPricing pp,products p where   pp.productId = p.productId and pp.ppId in(select ppId from Cart where cartId=?)";
		try {
			//TODO check all the users logged in or not
			 
			long cartId = cartDAO.getCartId(userId);
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { new Long(cartId) });
			Map<String, String> shopNameCache = new HashMap<>();
			Map<String,Product> prodCache = new HashMap<>();
			response.setShops(shopNameCache);
			 
			for (Map<String, Object> row : rows) {
				String shopId = ""+ row.get("shopId");
				String prodCId = shopId+"-"+row.get("productId");
				
				if (products.get(shopId) == null) {
					List<Product> pList = new ArrayList<>();
					products.put(shopId, pList);
					shopNameCache.put(shopId, shopCache.getIcon((long) row.get("shopId")));
				}
				if(prodCache.get(prodCId)== null){
					Product p = new Product();
					prodCache.put(prodCId,p);
					products.get(shopId).add(p);
				}
				Product product =  prodCache.get(prodCId);
				 
					ProductPrice productPrice = new ProductPrice();
					 
					product.setProductId((long) row.get("productId"));
					product.setCategoryId((long) row.get("categoryId"));
					product.setProductDescription((String) row.get("productDescription"));
					product.setProductName((String) row.get("productName"));
					product.setSmallImage((String) row.get("smallImage"));
					product.setLargeImage((String) row.get("largeImage"));
					
					productPrice.setPpId((long) row.get("ppId"));
					productPrice.setDiscount((double) row.get("discount"));
					productPrice.setPrice((double) row.get("price"));
					productPrice.setWeight((double) row.get("weight"));
					productPrice.setUnit((String) row.get("unit"));
					productPrice.setShopId((long) row.get("shopId"));
					product.getProductPriceList().add(productPrice);
					 
				}
		 
		} catch (DataAccessException de) {
			res.setErrorMessage("Exception happend");
			System.out.println(de);
			logger.error(de);

		}
		return res;
	}
	
	public String getCartAsText(Checkout checkout) {
	 	Map<String, List<Product>> products = new HashMap<>();
		Response<CartResponse> res = new Response<CartResponse>();
		CartResponse response = new CartResponse();
	 	res.setData(response);
	 	response.setProducts(products);
	 	StringBuffer resps = new StringBuffer();
	 	resps.append("Hi "+checkout.getName()+"\n\n\n");
		String sql = "select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.ppId ppId, pp.weight weight, pp.price price, pp.discount discount,pp.shopId shopId,pp.unit unit from productsPricing pp,products p where   pp.productId = p.productId and pp.ppId in(select ppId from Cart where cartId=?)";
		try {
			//TODO check all the users logged in or not
			 
			long cartId = cartDAO.getCartId(checkout.getUser());
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { new Long(cartId) });
			Map<String, String> shopNameCache = new HashMap<>();
			Map<String,Product> prodCache = new HashMap<>();
			response.setShops(shopNameCache);
			
			for (Map<String, Object> row : rows) {
				String shopId = ""+ row.get("shopId");
				String prodCId = shopId+"-"+row.get("productId");
				
				if (products.get(shopId) == null) {
					List<Product> pList = new ArrayList<>();
					products.put(shopId, pList);
					shopNameCache.put(shopId, shopCache.getIcon((long) row.get("shopId")));
					resps.append("\n From Shop: "+shopCache.getShopName((long) row.get("shopId"))+"\n");
					resps.append("\n From Shop Address : "+shopCache.getShopAddress((long) row.get("shopId"))+"\n");
					if(checkout.isCashOnDelivery()){
						resps.append("\n Cash on Delivery\n");
						resps.append("\nDelivery Address:"+checkout.getAddress()+"\n");
						
					}else{
						resps.append("\n Please Pick your order from the store\n");
					}
					resps.append("\nCart Id: "+cartId+"\n");
				}
				if(prodCache.get(prodCId)== null){
					Product p = new Product();
					prodCache.put(prodCId,p);
					products.get(shopId).add(p);
				}
				Product product =  prodCache.get(prodCId);
				 
					ProductPrice productPrice = new ProductPrice();
					resps.append("\n"+(String) row.get("productName")+"\n");
					resps.append("\n"+ row.get("price")+"\n");
					
					product.setProductId((long) row.get("productId"));
					product.setCategoryId((long) row.get("categoryId"));
					product.setProductDescription((String) row.get("productDescription"));
					product.setProductName((String) row.get("productName"));
					product.setSmallImage((String) row.get("smallImage"));
					product.setLargeImage((String) row.get("largeImage"));
					
					productPrice.setPpId((long) row.get("ppId"));
					productPrice.setDiscount((double) row.get("discount"));
					productPrice.setPrice((double) row.get("price"));
					productPrice.setWeight((double) row.get("weight"));
					productPrice.setUnit((String) row.get("unit"));
					productPrice.setShopId((long) row.get("shopId"));
					product.getProductPriceList().add(productPrice);
					 
				}
		 
		} catch (DataAccessException de) {
			res.setErrorMessage("Exception happend");
			System.out.println(de);
			logger.error(de);

		}
		resps.append("\n\nThanks\n GroBucks Team");
		return resps.toString();
	}
}
