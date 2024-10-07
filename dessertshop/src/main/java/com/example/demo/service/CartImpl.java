package com.example.demo.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.CartDAO;
import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.CartRequest;

@Service
public class CartImpl implements CartService {
	@Autowired
	private CartDAO cartDAO;
	
	@Override
	@Transactional
	public String[]  cartProductList(CartRequest cart) {
		Cart cartEntity=cartDAO.findEmail(cart);
		if(cartEntity==null) {//使用者尚未加任何東西到購物車
			cartDAO.cartProductList(cart);
			return  null;
		}
		else {
			String[] cartDataArray = cartEntity.getIdAndCount().split(",");
			Map<String,String> cartDataMap = new LinkedHashMap<>();
			for(int i = 0 ; i < cartDataArray.length ; i+=2) {
				cartDataMap.put(cartDataArray[i], cartDataArray[i+1]);
			}
			int sum = cartDataMap.containsKey(cart.getId()) ? Integer.parseInt(cartDataMap.get(cart.getId())) : 0;
			sum+=Integer.parseInt(cart.getCount());
			cartDataMap.put(cart.getId(), String.valueOf(sum));
//			for(int i = 0 ; i < cartDataArray.length ; i+=2) {
//				if(cartDataArray[i].equals(cart.getId())) {
//						int sum=Integer.parseInt(cartDataArray[i+1])+Integer.parseInt(cart.getCount());
//						String sumCount=String.valueOf(sum);
//						System.out.println("sumCount:"+sumCount);
//						cartDataMap.put(cartDataArray[i], sumCount);
//					} 
//					else {
//						cartDataMap.put(cartDataArray[i], cartDataArray[i+1]);
////					cartDataMap.put(cart.getId(), cart.getCount());
//					}
//			}
//			cartDataMap.put(cart.getId(),cart.getCount());
			//id一樣的話會覆蓋count，不一樣會新增一筆資料
			List<String> dataList = new ArrayList<>();
			for (Map.Entry<String, String> entry : cartDataMap.entrySet()) {
			    dataList.add(entry.getKey());
			    dataList.add(entry.getValue());
			}
			String[] dataArray = dataList.toArray(new String[0]);
			String combinedString = String.join(",", dataArray);
			cartEntity.setIdAndCount(combinedString);
			cartDAO.updatecartProductList(cartEntity);
			return dataArray;
		}
	}
	
	@Override
	public List<CartItem> cartItemList(String[] dataArray) {
		List<CartItem> allItemsList = new ArrayList<>();
		for(int i = 0 ; i < dataArray.length ; i+=2) {
			CartItem item=cartDAO.findCartItem(dataArray[i]);
			item.setCount(Integer.parseInt(dataArray[i+1]));
			allItemsList.add(item);
		}
		return allItemsList;
	}
	
	@Override
	public String[] serachIdAndCount(String email) {
		String idAndCount=cartDAO.serachIdAndCount(email);
		String[] arr = idAndCount.split(",");
		return arr;
	}
	
	@Override
	public String serachIdByName(String name) {
		return cartDAO.serachIdByName(name);
	}
	
	@Override
	@Transactional
	public String[] deleteCartItem(CartRequest cart) {
		String idAndCount=cartDAO.serachIdAndCount(cart.getEmail());
		String[] arr = idAndCount.split(",");
		List<String> itemslList = new ArrayList<>();
		for(int i = 0 ; i < arr.length ; i+=2) {
			if(Integer.parseInt(arr[i])!=Integer.parseInt(cart.getId())) {
				itemslList.add(arr[i]);
				itemslList.add(arr[i+1]);	
			}
		}
		String[] dataArray = itemslList.toArray(new String[0]);
		String combinedString = String.join(",", dataArray);
		Cart cartEntity = new Cart();
		cartEntity.setEmail(cart.getEmail());
		cartEntity.setIdAndCount(combinedString);
		cartDAO.updatecartProductList(cartEntity);
		return dataArray;
	}
}
