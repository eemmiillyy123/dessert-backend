package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.UserDAO;
import com.example.demo.entity.User;
import com.example.demo.utils.DESUtil;
import com.example.demo.utils.MyDataUtils;
import java.net.URLEncoder;  // 用於 URL 編碼
import java.nio.charset.StandardCharsets;  // 用於指定 UTF-8 編碼

@Service
public class UserImpl implements UserService {
	@Autowired
	private UserDAO userdao;
	@Autowired
	private JavaMailSender emailSender;

	@Value("${app.baseUrl}")
	private String baseUrl;

	@Override
	@Transactional
	public int createAccount(User user) {
		if ( userdao.checkUserExist(user.getUsername(), user.getEmail())>0) {
			return 0;// 表示已經註冊，return 0表示輸入0筆資料
		}
		String encryptPwd = encrypt(user.getPassword());
		user.setPassword(encryptPwd);
		int count=userdao.insert(user);
		sendVerificationEmail(user.getUsername(), user.getEmail());
		return count;
	}

//	@Override
//	@Transactional
//	public boolean updateStatus(String username) {// 在30分鐘以內點擊驗證信連結就將status改為1
//		Date time = userdao.getCurrentDate();
//		Date registertime=userdao.time(decrypt(username));
//		long timeDifference = time.getTime() - registertime.getTime();
//		String decryptedName = decrypt(username);
//		if (timeDifference > 0.5 * 60 * 1000) {// 檢查使用者是否在30分鐘內收信
//			userdao.delete(decryptedName);//超過時間就刪除這筆資料
//			return false;
//		} else {
//			userdao.update(decryptedName, time);
//			return true;
//		}
//	}
	
//	@Override
//	@Transactional
//	public boolean updateStatus(String username) {
//	    Date time = userdao.getCurrentDate();  // 获取当前时间
////	    String decryptedName = decrypt(username);  // 解密用户名
//	    // 先將 URL 中的 username 解碼
//        String decodedUsername = URLDecoder.decode(username, "UTF-8");
//        
//        // 然後對解碼後的 username 進行解密
//        String decryptedName = decrypt(decodedUsername);
//	    
//	    if (decryptedName == null) {
//	        // 解密失败的情况，直接返回 false 或抛出异常
//	        throw new IllegalArgumentException("Invalid username provided for decryption.");
//	    }
//	    
//	    Date registertime = userdao.time(decryptedName);  // 根据解密后的用户名获取注册时间
//	    
//	    if (registertime == null) {
//	        // 如果 registertime 为空，可能意味着该用户不存在或时间未存储
//	        throw new IllegalArgumentException("Register time not found for user: " + decryptedName);
//	    }
//	    
//	    // 计算当前时间与注册时间的差值
//	    long timeDifference = time.getTime() - registertime.getTime();
//	    
//	    if (timeDifference > 0.5 * 60 * 1000) {  // 检查是否超出30分钟
//	        userdao.delete(decryptedName);  // 超时则删除数据
//	        return false;
//	    } else {
//	        userdao.update(decryptedName, time);  // 更新用户状态
//	        return true;
//	    }
//	}

	@Override
	@Transactional
	public boolean updateStatus(String username) {
	    try {
	    	 // 先將空格替換回 +，然後進行 URL 解碼
	        String decodedUsername = URLDecoder.decode(username.replace(" ", "+"), "UTF-8");
	        
	        // 然後對解碼後的 username 進行解密
	        String decryptedName = decrypt(username);
	        
	        
	        System.out.println("decryptedName:"+decryptedName);
	        // 後續的業務邏輯保持不變
	        Date time = userdao.getCurrentDate();
	        Date registerTime = userdao.time(decryptedName);
	        System.out.println("time.getTime():"+time.getTime()+"registerTime.getTime()"+registerTime.getTime());
	        long timeDifference = time.getTime() - registerTime.getTime();
	     // 30 分钟的毫秒数为 30 * 60 * 1000，即 1800000 毫秒
	        long thirtyMinutesInMillis = (long) (0.5 * 60 * 60 * 1000); // 30 分钟对应的毫秒数
	        System.out.println(thirtyMinutesInMillis);

	        
	        System.out.println(timeDifference);
	        System.out.println(0.5 * 60 * 1000);
	        if (timeDifference > thirtyMinutesInMillis) { // 超過30分鐘
	            userdao.delete(decryptedName); // 刪除數據
	            return false;
	        } else {
	            userdao.update(decryptedName, time);
	            return true;
	        }
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	

	@Override
	public int usernameIsUsed(String username) {//檢查使用者名稱是否已被使用
		return userdao.usernameIsUsed(username);
	}

	@Override
	public int chekAccountCreated(String email, String psword) {//檢查是否註冊過
		try {
			Integer status = userdao.chekAccountCreated(email, encrypt(psword));//Integer才有null
			if (status == null) {
				return -1;
			} else {
				return status;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // 注意：捕獲異常時也返回-1，你可能需要更細粒度的錯誤處理
		}
	}
	
	@Override
	public String forgetPwd(String email) {
		String username=userdao.findUsernameByEmail(email);
		if(username==null||username.trim().equals("")) {//考慮到可能為null、""(空字串)、" "(空白字串)的情況
			return null;
		}
		String token=UUID.randomUUID().toString();
		sendResetPwdEmail(email,encrypt(username),token);
		return username;
	}
	
	@Override
	@Transactional
	public void updateResetPwdStatus(String username) {
		userdao.updateResetPwdStatus(decrypt(username),1);
	}
	
	@Override
	@Transactional
	public void resetPwd(String name,String pwd) {
		if(userdao.checkResetStatus(decrypt(name))==1) {
			userdao.updatePassword(decrypt(name),encrypt(pwd));
			userdao.updateResetPwdStatus(decrypt(name),0);
		}
	}
	
	
	
	@Override
	public String findUsernameByEmail(String email) {
		return userdao.findUsernameByEmail(email);
	}
	
	@Override
	public String encryptString(String string) {
		 return encrypt(string);
	}
	
	private void sendVerificationEmail(String name, String email) {
	    String encryptName = encrypt(name);
	    
	    // 將加密的名稱進行 URL 編碼，避免 + 等特殊字符出錯
	    String encodedEncryptName = URLEncoder.encode(encryptName, StandardCharsets.UTF_8);
	    
	    // 生成驗證連結
	    String verificationLink = baseUrl + "/applyUpdate?username=" + encodedEncryptName;
	    
	    // 發送驗證郵件
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setTo(email);
	    message.setSubject("Sugar Bliss會員驗證通知");
	    message.setText(name + "您好:\n\n" + 
	        "歡迎使用Sugar Bliss會員服務，請在30分鐘內點擊以下網址進行驗證" + 
	        "，以完成會員註冊:\n" + verificationLink + 
	        "\n超過30分鐘請重新註冊!");
	    
	    emailSender.send(message);
	}

	
//	private void sendVerificationEmail(String name, String email) {
//	    String encryptName = encrypt(name);
//	    
//	    // 將加密的名稱進行 URL 編碼，避免 + 等特殊字符出錯
//	    String encodedEncryptName = URLEncoder.encode(encryptName, StandardCharsets.UTF_8);
//	    
//	    // 生成驗證連結
//	    String verificationLink = baseUrl + "/applyUpdate?username=" + encodedEncryptName;
//	    
//	    // 發送驗證郵件
//	    SimpleMailMessage message = new SimpleMailMessage();
//	    message.setTo(email);
//	    message.setSubject("Sugar Bliss會員驗證通知");
//	    message.setText(name + "您好:\n\n" + 
//	        "歡迎使用Sugar Bliss會員服務，請在30分鐘內點擊以下網址進行驗證" + 
//	        "，以完成會員註冊:\n" + verificationLink + 
//	        "\n超過30分鐘請重新註冊!");
//	    
//	    emailSender.send(message);
//	}

//	private void sendVerificationEmail(String name, String email) {// 寄驗證信
//		String encryptName = encrypt(name);
//		String verificationLink = baseUrl + "/applyUpdate?username=" + encryptName;
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(email);
//		message.setSubject("Sugar Bliss會員驗證通知");
//		message.setText(name + "您好:\n\n" + "歡迎使用Sugar Bliss會員服務，請在30分鐘內點擊以下網址進行驗證" + "，以完成會員註冊:\n" + verificationLink+"\n超過30分鐘請重新註冊!");
//		emailSender.send(message);
//	}
//	
	private void sendResetPwdEmail(String email,String name,String token) {// 寄驗證信
		String verificationLink = baseUrl + "/user/changePassword?token=" + token+"&name="+name;
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Sugar Bliss重置密碼通知");
		message.setText("您好:\n\n" + "您在Sugar Bliss發起了重置密碼的請求，請在1小時內點擊以下網址進行重置密碼:\n"+ verificationLink);
		emailSender.send(message);
	}

	private String encrypt(String pwd) {// 加密
		try {
			return DESUtil.encrypt(MyDataUtils.CODE,pwd);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

//	private String decrypt(String pwd) {// 解密
//		try {
//			return DESUtil.decrypt(MyDataUtils.CODE,pwd);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "";
//		}
//	}
	private String decrypt(String pwd) {
	    try {
	        // 移除多余空格或非法字符
	        pwd = pwd.trim(); // 去除前后空格
	        return DESUtil.decrypt(MyDataUtils.CODE, pwd);
	    } catch (IllegalArgumentException e) {
	        System.err.println("Base64 decoding failed. Invalid characters found in: " + pwd);
	        e.printStackTrace();
	        return "";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "";
	    }
	}


}
