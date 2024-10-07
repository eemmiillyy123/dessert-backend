package com.example.demo.dao;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.Dessert;
import com.example.demo.entity.Product;
@Mapper
public interface DessertMapper {
	@Insert(" INSERT INTO test_project.member_account ( "
	          + "	   USERNAME, PASSWORD, SALT, "
			  + "	   CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME "
			  + " ) "
			  + " VALUE ( "
			  + "	   #{username}, #{password}, #{salt}, "
			  + "	   #{create_by}, NOW(), #{update_by}, NOW() "
			  + " ) ")
		public Integer insert(Dessert dessert);
		
		@Select(" SELECT "
			  + "	   * "
			  + " FROM "
			  + "	   TRAINEE.EMILY_DROPDOWN ")
//			  + " WHERE "
//			  + "	   FIRST_LAYER_ID = #{firstLayerId},SECOND_LAYER_ID = #{secondLayerId} "
//			  + "FETCH FIRST 1 ROWS ONLY")
		public Dessert select(Dessert dessert);
		@Select(" SELECT "
				  + "	   * "
				  + " FROM "
				  + "	   TRAINEE.EMILY_PRODUCT ")
		public List<Product> productSelect(Product product);
		@Update(" UPDATE "
			  + "	   test_project.member_account "
			  + " SET "
			  + "	   PASSWORD = #{password}, UPDATE_BY = #{update_by}, UPDATE_TIME = NOW() "
			  + " WHERE "
			  + "	   ID = #{id} ")
		public Integer update(Dessert dessert);
		
}
