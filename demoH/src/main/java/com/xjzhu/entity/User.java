package com.xjzhu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue
	private Integer uId;
	
	@Column(name="u_name")
	private String uName;
	
	@Column(name="u_introduce")
	private String uIntroduce;
	
	public User(){
		super();
	}
	public User(String name, String introduce){
		
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuIntroduce() {
		return uIntroduce;
	}
	public void setuIntroduce(String uIntroduce) {
		this.uIntroduce = uIntroduce;
	}
	
}
