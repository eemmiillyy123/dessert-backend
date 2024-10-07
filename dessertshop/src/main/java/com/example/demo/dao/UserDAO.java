package com.example.demo.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.example.demo.entity.User;

public interface UserDAO {
//	@Results({@Result(column="EMAIL",property="email",jdbcType=JdbcType.VARCHAR,id=true),
//		@Result(column="PSWORD",property="password",jdbcType=JdbcType.VARCHAR,id=true),
//		@Result(column="USERNAME",property="username",jdbcType=JdbcType.VARCHAR,id=true),
//		@Result(column="REGISTER_TIME",property="registerTime",jdbcType=JdbcType.DATE,id=true),
//		@Result(column="STATUS",property="status",jdbcType=JdbcType.INTEGER,id=true),
//		@Result(column="RESETPWD",property="resetPwd",jdbcType=JdbcType.INTEGER,id=true)
//	})
	
//	@Insert("insert into  EMILY_USER values(#{email},#{password},#{username},SYSDATE,#{status},#{resetPwd})")
	@Insert("insert into  EMILY_USER values(#{email},#{password},#{username},NOW(),#{status},#{resetPwd})")
	public int insert(User user);
	
	@Select("select count(*) from EMILY_USER where USERNAME=#{username} or EMAIL=#{email}")
	int checkUserExist(@Param("username")String USERNAME,@Param("email")String EMAIL);
	
	@Update("update EMILY_USER  set STATUS=1,REGISTER_TIME=#{refreshTime} where USERNAME=#{username}")
	public void update(String username,Date refreshTime);
	
	@Select("select REGISTER_TIME from EMILY_USER where USERNAME=#{username}")
	public Date time(String username);
	
	@Select("select NOW() from DUAL")
    Date getCurrentDate();
	
	@Delete("delete from EMILY_USER where USERNAME=#{username}")
	public int delete(String username);

	@Select("select count(*) from EMILY_USER where USERNAME=#{username}")
	public int usernameIsUsed(String username);
	
	@Select("select NOW() from EMILY_USER where EMAIL=#{email} and PSWORD=#{password}")
	public Integer  chekAccountCreated(String email,String password);

	@Select("select USERNAME from EMILY_USER where EMAIL=#{email}")
	public String findUsernameByEmail(String email);

	@Update("update EMILY_USER set PSWORD=#{pwd},RESETPWD=1 where USERNAME=#{name}")
	public void updatePassword(String name,String pwd);
	
	@Update("update EMILY_USER set RESETPWD=#{resetPwd} where USERNAME=#{name}")
	public void updateResetPwdStatus(String name,int resetPwd );
	
	@Select("select RESETPWD from EMILY_USER where USERNAME=#{name}")
	public int checkResetStatus(String name);
}
