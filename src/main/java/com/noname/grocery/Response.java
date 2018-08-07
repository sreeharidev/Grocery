package com.noname.grocery;

public class Response<T> {
	private T data;
	private String status="success";
	private String message="successfuly responded";
	public Response(){
		
	}
	public Response(T data){
		this.data = data;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setErrorMessage(String message){
		this.status = "failure";
		this.message = message;
	}	
}
