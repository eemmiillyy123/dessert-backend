package com.example.demo.entity;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class User {
	@NotBlank(message="使用者名稱不可為空")
	@Length(min=6,max=12,message="使用者名稱必須在6-12碼")
	private String username;
	
	@NotBlank(message="電子信箱不可為空")
	@Email(message="不符合有效的電子郵件地址")
	private String email;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\w]{6,16}$", 
	 		 message = "密碼必須為長度6~16位碼大小寫英文加數字")
	@NotBlank(message="密碼不可為空")
	private String password;
	
	private int status;
	
	private Date registerTime;
	
	private int resetPwd;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public int getResetPwd() {
		return resetPwd;
	}
	public void setResetPwd(int resetPwd) {
		this.resetPwd = resetPwd;
	}
}
