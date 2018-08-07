package com.noname.grocery.brand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.noname.grocery.Response;

@RestController
public class BrandService {
	@Autowired
	BrandDAO brandDAO;
	
	@RequestMapping(method=RequestMethod.POST,value={"/createBrand"})
	public Response<Object> createBrand(@RequestBody Brand brand){
		return brandDAO.createBrand(brand);
	}
	
	@RequestMapping(method=RequestMethod.POST,value={"/getAllBrands"})
	public Response<List<Brand>> getAllBrands(){
		return brandDAO.getAllBrands();
	}
	@RequestMapping(method=RequestMethod.POST,value={"/deleteBrand"})
	public Response<Object> deleteBrand(@RequestBody Brand brand){
		return brandDAO.deleteBrand(brand);
	}
	@RequestMapping(method=RequestMethod.POST,value={"/updateBrand"})
	public 	Response<Object> updateBrand(@RequestBody Brand brand){
		return brandDAO.updateBrand(brand);
	}
}
