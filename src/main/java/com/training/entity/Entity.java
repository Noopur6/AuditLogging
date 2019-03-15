package com.training.entity;

import java.util.Date;

public class Entity {

	private String detail;	
	private Date time;
	public Entity(String detail, Date time) {
		
		this.detail = detail;
		this.time = time;
	}
	public Entity() {
		
		// TODO Auto-generated constructor stub
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	  
}
