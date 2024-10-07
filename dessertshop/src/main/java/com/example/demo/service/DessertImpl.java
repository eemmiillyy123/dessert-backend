package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DessertDAO;
import com.example.demo.entity.Dessert;
import com.example.demo.entity.Product;
@Service
public class DessertImpl implements DessertService {
	
	@Autowired
	private DessertDAO dessertDao;
	
	@Override
	public List<Dessert> select(Dessert dessert) {
		return dessertDao.select(dessert);
	}
	
	public List<Product> productSelect(Product product) {
		return dessertDao.productSelect(product);
	}
	@Override
	public int insert(Dessert dessert) {
		return dessertDao.insert(dessert);
	}
	@Override
	public int update(Dessert dessert) {
		return dessertDao.update(dessert);
	}
	@Override
	public int delete(Dessert dessert) {
		return dessertDao.delete(dessert);
	}
	@Override
	public List<Product> showProductDescription(int productId){
		return dessertDao.showProductDescription(productId);
	}
}
