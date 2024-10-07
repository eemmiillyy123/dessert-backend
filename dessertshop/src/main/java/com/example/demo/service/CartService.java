package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.CartRequest;

public interface CartService {

	public String[] cartProductList(CartRequest cart);

	public List<CartItem> cartItemList(String[] dataArray);

	public String[] serachIdAndCount(String email);

	public String[] deleteCartItem(CartRequest cart);

	public String serachIdByName(String name);

}
