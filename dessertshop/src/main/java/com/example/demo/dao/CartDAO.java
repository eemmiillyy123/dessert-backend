package com.example.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.CartRequest;

public interface CartDAO {
	@Insert("insert into EMILY_SHOPPINGCART (EMAIL,IDANDCOUNT) values(#{email},#{id}||','||#{count})")
	public void cartProductList(CartRequest cart);

	@Select("select * from EMILY_SHOPPINGCART where EMAIL=#{email}")
	public Cart findEmail(CartRequest cart);
	
	@Update("update EMILY_SHOPPINGCART set IDANDCOUNT=#{idAndCount} where EMAIL=#{email}")
	public void updatecartProductList(Cart cart);
	
	@Select("select PRODUCT_NAME,PRICE,IMG from EMILY_PRODUCT where PRODUCT_ID=#{dataArray}")
	@Results({
	    @Result(property = "productName", column = "PRODUCT_NAME"),
	    @Result(property = "price", column = "PRICE"),
	    @Result(property = "img", column = "IMG")
	})
	public CartItem findCartItem(String dataArray);

	@Select("select idAndCount from EMILY_SHOPPINGCART where EMAIL=#{email}")
	public String serachIdAndCount(String email);

	@Select("select PRODUCT_ID from EMILY_PRODUCT where PRODUCT_NAME=#{name}")
	public String serachIdByName(String name);
}
