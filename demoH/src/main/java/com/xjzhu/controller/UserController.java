package com.xjzhu.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String index(Model model) {
		Page<User> userList = userService.getUserByPage(0, 5);
		if (userList != null) {
			model.addAttribute("users", userList.getContent());
			model.addAttribute("user", new User());
			model.addAttribute("pageTotal", userList.getTotalPages());
			model.addAttribute("currentPage", Integer.valueOf(1));
		}
		return "index";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(Model model, User user) {
		if (user.getuId() != null) {
			//user.setCreateDate(userService.getUser(user.getuId()).getCreateDate());
			user.setUpdateDate(new Date());
		}else{
			user.setCreateDate(new Date());
		}
		userService.addOrUpdate(user);
		return "redirect:index";
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteUser(Model model, User user) {
		userService.delete(user);
		return "redirect:index";
	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	@ResponseBody
	public String getUser(Model model, @RequestParam("uId") Integer uId, User user) throws JsonProcessingException {
		user = userService.getUser(uId);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);
		return json;
	}
	
	@RequestMapping("/showPage/{currentPage}")
	public String showPage(Model model, @PathVariable int currentPage) throws JsonProcessingException {
		/*Page<User> userList = userService.getUserByPage(0, 5);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(userList);*/
		Page<User> userList = userService.getUserByPage(currentPage-1, 5);
		if (userList != null) {
			model.addAttribute("users", userList.getContent());
			model.addAttribute("user", new User());
			model.addAttribute("pageTotal", userList.getTotalPages());
			model.addAttribute("currentPage", currentPage);
		}
		return "index";
	}
	
	@InitBinder   
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    } 

}
