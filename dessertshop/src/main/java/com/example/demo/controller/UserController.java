package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.UserService;
import com.example.demo.entity.User;

//@RestController
@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/createAccount")//註冊
	public ResponseEntity<Integer> createAccount(@RequestBody @Valid User user) {
		 return ResponseEntity.ok(userService.createAccount(user));
	}

	@GetMapping("/applyUpdate")//是否驗證成功(註冊)
	public ResponseEntity<String> updateStatus(@RequestParam String username) {
		if (userService.updateStatus(username)) {
			return ResponseEntity.ok("成功註冊!");
		} else {
			return ResponseEntity.badRequest().body("驗證失敗，請重新註冊!");
		}
	}

	@GetMapping("/checkUsername")//看使用者名稱是否被使用過
	public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
		return ResponseEntity.ok(userService.usernameIsUsed(username) > 0);
	}

	@PostMapping("/chekAccountCreated")//登入
	public ResponseEntity<Integer> chekAccountCreated(@RequestBody User user) {
		int status= userService.chekAccountCreated(user.getEmail(), user.getPassword());
		return ResponseEntity.ok(status);
	}
	
	@PostMapping("/forgetPwd")//忘記密碼-填寫信箱後會寄信
	public ResponseEntity<String> forgetPwd(@RequestBody User user) {
		if(userService.forgetPwd(user.getEmail())!=null) {
			return ResponseEntity.ok("Password reset email sent");
		}
		else {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred");
		}
	}
	
	@GetMapping("/user/changePassword")//在信箱點擊連結會出現重設密碼頁面
	public String  changePassword(@RequestParam String token,@RequestParam String name) {
		return "redirect:http://localhost:4200/updatePassword?name="+name;
	}
	
	@PostMapping("/updateResetPwdStatus")//將status改為1代表要更改密碼
	public ResponseEntity<String> updateResetPwdStatus(@RequestBody User user) {
		try {
			userService.updateResetPwdStatus(user.getUsername());
			return ResponseEntity.ok("Password updated successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password updated unsucessfully");
		}
	}
	
	@PostMapping("/resetPwd")//會去更改密碼
	public ResponseEntity<String>  resetPwd(@RequestBody User user) {
		try {
			userService.resetPwd(user.getUsername(),user.getPassword());
			return ResponseEntity.ok("sucessful");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unsucessful");
		}
	}
	
	@PostMapping("/findUsernameByEmail")
	public ResponseEntity<String> findUsernameByEmail(@RequestBody User user) {
		 String username= userService.findUsernameByEmail(user.getEmail());
		 return ResponseEntity.ok(username);
	}
	
//	@PostMapping("/saveUserData")
//	public ResponseEntity<String> saveUserData(){
//		
//	}
	@PostMapping("/encrypt")
	public ResponseEntity<String> encrypt(@RequestBody String string) {
		 String username= userService.encryptString(string);
		 return ResponseEntity.ok(username);
	}
}