package com.htl.microservice.controller.vo;

public class Result<T> {

	private String code = "0".intern();
	
	private String message = "success".intern();
	
	private T data = null;
	
	public Result(){}
	
	public Result(T t) {
		this.data = t;
	}
	public Result(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

}
