package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.entity.User;

@ControllerAdvice(assignableTypes = {UserController.class})//只對UserController生效
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)//驗證失敗時拋出的MethodArgumentNotValidException異常
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
    	//當發生MethodArgumentNotValidException異常時，將所有的字段错誤捕捉並添加到errors中。
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {//當驗證失败時，Spring會把驗證錯誤相關的信息绑定到 BindingResult	中
        	//	getAllErrors()方法:獲取所有的驗證錯誤
            String fieldName = ((FieldError) error).getField();//getField() 方法返回引發驗證錯誤的字串的名稱
            String errorMessage = error.getDefaultMessage();//獲取和這個字串相關的錯誤訊息
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);//任何一个方法参数驗證失败時，系统會返回一個包含所有相关辜驗證錯誤的HTTP400響應
    }
}
