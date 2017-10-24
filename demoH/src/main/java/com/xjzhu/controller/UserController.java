package com.xjzhu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		}
		return "index";
	}
	
	@RequestMapping(value="/addUser", method= RequestMethod.POST)
	public String addUser(Model model, @ModelAttribute("user") User user){
		userService.addOrUpdate(user);
		return "redirect:index";
	}
}
