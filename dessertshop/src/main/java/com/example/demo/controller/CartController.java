package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.CartRequest;
import com.example.demo.service.CartService;
@RestController
public class CartController {
	@Autowired
	private CartService cartService;
	
	@PostMapping("/cartProductList")
	public String[] cartProductList(@RequestBody CartRequest cart) { 
		 return cartService.cartProductList(cart);
	}
	
	@PostMapping("/cartItemList")
	public List<CartItem> cartItemList(@RequestBody String[] dataArray) {
		 return cartService.cartItemList(dataArray);
	}
	
	@PostMapping("/serachIdAndCount")
	public String[] serachIdAndCount(@RequestBody String email) {
		 return cartService.serachIdAndCount(email);
	}
	
	@PostMapping("/serachIdByName")
	public String serachIdByName(@RequestBody String name) {
		 return cartService.serachIdByName(name);
	}
	
	@PostMapping("/deleteCartItem")
	public List<CartItem> deleteCartItem(@RequestBody CartRequest cart) {
		String [] string=cartService.deleteCartItem(cart);
		return cartService.cartItemList(string);	
	}
}
