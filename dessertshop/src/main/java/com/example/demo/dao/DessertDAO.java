package com.example.demo.dao;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.example.demo.entity.Dessert;
import com.example.demo.entity.Product;
@Mapper
public interface DessertDAO {

	@Select(" select * from EMILY_DROPDOWN ")
	@Results({ @Result(column = "FIRST_LAYER_ID", property = "firstLayerId", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "SECOND_LAYER_ID", property = "secondLayerId", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "THIRD_LAYER_ID", property = "thirdLayerId", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "DROPDOWN_ID", property = "dropdownId", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR, id = true)
          })
	public List<Dessert> select(Dessert dessert);
	
	public Integer insert(Dessert memberAccount);
	public Integer update(Dessert memberAccount);
	public Integer delete(Dessert memberAccount);
	
	@Select(" select * from EMILY_PRODUCT where DROPDOWN_ID = #{dropdownId}")
	@Results({ @Result(column = "PRODUCT_ID", property = "productId", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "PRODUCT_NAME", property = "productName", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "PRICE", property = "price", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "DESCRIPTION", property = "description", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "INTRODUCTION", property = "introduction", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "IMG", property = "img", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "STATUS", property = "status", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "DROPDOWN_ID", property = "dropdownId", jdbcType = JdbcType.INTEGER, id = true)
          })
	public List<Product> productSelect(Product product);
	
	@Select(" select * from EMILY_PRODUCT where PRODUCT_ID = #{productId}")
	@Results({ @Result(column = "PRODUCT_ID", property = "productId", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "PRODUCT_NAME", property = "productName", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "PRICE", property = "price", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "DESCRIPTION", property = "description", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "INTRODUCTION", property = "introduction", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "IMG", property = "img", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "STATUS", property = "status", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "DROPDOWN_ID", property = "dropdownId", jdbcType = JdbcType.INTEGER, id = true)
          })
	public List<Product> showProductDescription(int productId);
}
