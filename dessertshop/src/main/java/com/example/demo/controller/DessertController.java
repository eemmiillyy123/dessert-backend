package com.example.demo.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Dessert;
import com.example.demo.service.DessertService;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;

@RestController
public class DessertController{
	@Autowired
	private DessertService dessertService;

	@PostMapping("/applySelect")
	public List<Dessert> applySelect(@RequestBody Dessert dessert) {
		return  dessertService.select(dessert);
	}
	
	@PostMapping("/applyProduct")
	public List<Product> applyProduct(@RequestBody Product product) {
		return dessertService.productSelect(product);
	}
	
	@PostMapping("/showProductDescription")
	public List<Product> showProductDescription(@RequestBody Product product){
		return dessertService.showProductDescription(product.getProductId());
	}
}