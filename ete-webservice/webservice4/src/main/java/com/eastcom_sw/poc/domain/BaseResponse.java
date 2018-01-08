package com.eastcom_sw.poc.domain;

public class BaseResponse {
	private String resultCode = "0";//为0是表示调用服务正常，其他值表示调用异常
	private String message = "success";//调用服务相关信息
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public BaseResponse(String resultCode, String message) {
		super();
		this.resultCode = resultCode;
		this.message = message;
	}
	public BaseResponse() {
		super();
	}
	
}
