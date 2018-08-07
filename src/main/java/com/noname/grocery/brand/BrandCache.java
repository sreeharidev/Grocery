package com.noname.grocery.brand;
import java.util.Map;

import org.springframework.stereotype.Component;
@Component
public class BrandCache {

	private BrandDAO brandDAO;
	private Map<Long, String> cache;
	public BrandCache(BrandDAO brandDAO){
		this.brandDAO = brandDAO;
		cache = this.brandDAO.getBrandsCache();
	}
	public String getBrandName(long brandId){
		return cache.get(brandId);
	}
	
}
