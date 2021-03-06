package com.hemant.userservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.userservice.model.User;

@RestController
public class UserController {

	private Map<String, User> userMap;

	@PostConstruct
	public void initMap() {
		userMap = new HashMap<>();
		for (int i = 1; i <= 5; i++) {
			String id = "user-"+ i;
			userMap.put(id, new User(id, "User" + i, "user" + i + "@gmail.com"));
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return new ArrayList<>(userMap.values());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable("id") String id) {
		User user = userMap.get(id);
		if (null == user) {
			throw new IllegalArgumentException("User not found for : " + id);
		}
		return user;
	}

}
