package com.xjzhu.entity;

import java.util.Date;

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
	
	@Column(name="update_date")
	private Date updateDate;
	
	@Column(name="create_date")
	private Date createDate;
	
	
	
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
