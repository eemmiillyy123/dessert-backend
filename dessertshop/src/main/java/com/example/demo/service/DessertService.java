package com.example.demo.service;
import java.util.List;

import com.example.demo.entity.Dessert;
import com.example.demo.entity.Product;
public interface DessertService {
	public List<Dessert> select(Dessert dessert);
	public int insert(Dessert student);
	public int delete(Dessert student);
	public int update(Dessert student);
	public List<Product> productSelect(Product product);
	public List<Product> showProductDescription(int productId);
}
