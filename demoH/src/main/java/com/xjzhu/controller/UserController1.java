package com.xjzhu.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
	public String index(Model model, @RequestParam(value="model", required=false) Integer modelfalg) {
		List<User> userList = userService.getUserList();
		if (userList != null) {
			model.addAttribute("users", userList);
		}
		if(modelfalg ==null ){
			model.addAttribute("model", 0);
		}else{
			model.addAttribute("model", modelfalg);
		}
		model.addAttribute("user", new User());
		return "indexbootstraptable";
	}
	
	@RequestMapping(value="/index", method = RequestMethod.POST)
	@ResponseBody
	public String getUsers(Model model, @RequestParam("page") Integer page, @RequestParam("rows") Integer rows) throws JsonProcessingException {
		Page<User> users = userService.getUserByPage(page-1, rows);
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("total", users.getTotalElements());
		maps.put("rows", users.getContent());
        String json = mapper.writeValueAsString(maps);
		return json;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public String addUser(Model model, User user) throws JsonProcessingException {
		if (user.getuId() != null) {
			//user.setCreateDate(userService.getUser(user.getuId()).getCreateDate());
			user.setUpdateDate(new Date());
		}else{
			user.setCreateDate(new Date());
		}
		userService.addOrUpdate(user);
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("flag", "success");
		//maps.put("errorMsg", true);
		//maps.put("error", "error");
        String json = mapper.writeValueAsString(maps);
		return json;
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public String deleteUser(Model model, @RequestParam("uIds") String uIds) throws IOException {
		//userService.deleteById(uId);;
		uIds = uIds.replace("[", "");
		uIds = uIds.replace("]", "");
		String[] ids = uIds.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for(String id : ids){
			idList.add(new Integer(id));
		}
		userService.deleteByIds(idList);
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("success", true);
		String json = mapper.writeValueAsString(maps);
		return json;
	}
	
	
	@InitBinder   
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    } 

}
