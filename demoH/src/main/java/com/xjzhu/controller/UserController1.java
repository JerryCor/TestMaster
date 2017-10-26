package com.xjzhu.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
@RequestMapping("/demo1")
public class UserController1 {
	@Autowired
	private UserService userService;

	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Model model) {
		List<User> userList = userService.getUserList();
		if (userList != null) {
			model.addAttribute("users", userList);
		}
		return "indexbootstraptable";
	}
	
	@RequestMapping(value="/index", method = RequestMethod.POST)
	@ResponseBody
	public String getUsers(Model model, @RequestParam("page") Integer page, @RequestParam("rows") Integer rows) throws JsonProcessingException, JSONException {
		Page<User> users = userService.getUserByPage(page-1, rows);
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonResult = new JSONObject();  
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("total", users.getTotalElements());
		maps.put("rows", users.getContent());
        jsonResult.put("total", users.getTotalElements());  
        jsonResult.put("rows", users.getContent());  
        String json = mapper.writeValueAsString(maps);
		return json;
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
		return "redirect:indexbootstraptable";
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteUser(Model model, User user) {
		userService.delete(user);
		return "redirect:indexbootstraptable";
	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	@ResponseBody
	public String getUser(Model model, @RequestParam("uId") Integer uId, User user) throws JsonProcessingException {
		user = userService.getUser(uId);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);
		return json;
	}
	
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	@ResponseBody
	public String getAllUsers(Model model, @RequestParam("page") Integer page, @RequestParam("rows") Integer rows) throws JsonProcessingException {
		Page<User> users = userService.getUserByPage(page, rows);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(users.getContent());
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
		return "indexbootstraptable";
	}
	
	@InitBinder   
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    } 

}
