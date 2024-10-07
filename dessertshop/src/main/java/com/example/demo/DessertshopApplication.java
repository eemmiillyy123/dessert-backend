package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@MapperScan("com.example.demo.dao")
@SpringBootApplication
@Configuration
public class DessertshopApplication implements WebMvcConfigurer {//設置跨域
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:4200") //表示對這個路徑實行跨域訪問權限的設置
				.allowedMethods("GET", "POST", "PUT", "DELETE","PATCH","HEAD","OPTIONS") //允許的請求方式
				.allowCredentials(true); //是否發送cookie消息
	}
	public static void main(String[] args) {
		SpringApplication.run(DessertshopApplication.class, args);
	}

}
