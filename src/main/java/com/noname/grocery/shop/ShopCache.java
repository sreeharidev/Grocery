package com.noname.grocery.shop;
import java.util.Map;

 
import org.springframework.stereotype.Component;
@Component
public class ShopCache {
	 
	private ShopDAO shopDAO; 
	private Map<Long, Shop> cache = null;
	
	public ShopCache(ShopDAO shopDAO){
		this.shopDAO = shopDAO;
		init();
	}
	public void init(){
		if(cache == null){
			cache = shopDAO.getShopCache();
		}
		 
	}
	public String getIcon(long shopId){
		return cache.get(shopId).getSmallImage();
	}
	public String getBanner(long shopId){
		return cache.get(shopId).getLargeImage();
	}
	public String getShopName(long shopId){
		return cache.get(shopId).getShopName();
		
	}
	public String getShopAddress(long shopId){
		return cache.get(shopId).getShopAddress();
		
	}
}