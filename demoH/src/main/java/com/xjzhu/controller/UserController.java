package com.xjzhu.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjzhu.entity.User;
import com.xjzhu.service.UserService;

@Controller
@RequestMapping("/demo")
public class UserController {
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/index")
	public String index(Model model){
		List<User> userList = userService.getUserList();
		if(userList!= null){
			model.addAttribute("users", userList);
			model.addAttribute("user", new User());
			model.addAttribute("asd", "123");
		}
		return "index";
	}
	
	@RequestMapping(value="/addUser", method= RequestMethod.POST)
	public String addUser(Model model, User user){
		if(user.getuId()!= null){
			user.setCreateDate(userService.getUser(user.getuId()).getCreateDate());
			user.setUpdateDate(new Date());
		}
		userService.addOrUpdate(user);
		return "redirect:index";
	}
	
	@RequestMapping(value="/deleteUser", method= RequestMethod.POST)
	public String deleteUser(Model model, User user){
		userService.delete(user);
		return "redirect:index";
	}
	
	@RequestMapping(value="/getUser", method= RequestMethod.GET)
	@ResponseBody
	public String getUser(Model model, @RequestParam("uId") Integer uId, User user) throws JsonProcessingException{
		user = userService.getUser(uId);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);
		return json;
	}
}
