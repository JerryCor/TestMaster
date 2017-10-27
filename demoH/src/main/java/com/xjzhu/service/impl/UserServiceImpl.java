package com.xjzhu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xjzhu.dao.UserDao;
import com.xjzhu.entity.User;
import com.xjzhu.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public void addOrUpdate(User user) {
		userDao.save(user);
	}

	@Override
	public List<User> getUserList() {
		List<User> userList = (List<User>) userDao.findAll();
		if(userList!=null){
			return userList;
		}
		return null;
	}

	@Override
	public User getUser(Integer uId) {
		User user =userDao.findOne(uId);
		if(user!=null){
			return user;
		}
		return null;
	}

	@Override
	public Page<User> getUserByPage(Integer page, Integer size) {
		Pageable pages = new PageRequest(page, size);
		Page<User> users =userDao.findAll(pages);
		return users;
	}

	@Override
	public void deleteById(Integer uId) {
		userDao.delete(uId);
	}

	@Override
	public void deleteByIds(List<Integer> uIds) {
		for(Integer id : uIds){
			userDao.delete(id);
		}	
	}
}
