package com.example.demo.service;
import com.example.demo.entity.User;
public interface UserService {
	public int createAccount(User  user);
	public boolean updateStatus(String username) ;
	public int usernameIsUsed(String username);
	public int chekAccountCreated(String email,String psword);
	public String forgetPwd(String email);
	public void resetPwd(String name,String pwd);
	public void updateResetPwdStatus(String username);
	public String findUsernameByEmail(String email);
	public String encryptString(String string);
}
