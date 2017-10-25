package com.xjzhu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xjzhu.entity.User;

@Service
public interface UserService {
	void addOrUpdate(User user);
	void delete(User user);
	User getUser(Integer uId);
	List<User> getUserList();
}
