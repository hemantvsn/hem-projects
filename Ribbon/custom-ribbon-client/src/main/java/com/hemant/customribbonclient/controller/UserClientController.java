package com.hemant.customribbonclient.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserClientController {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
	public Map<String, String> getUserById(@PathVariable String id) {
		String url = "http://user-service" + "/" + id;
		ResponseEntity<String> authResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		
		Map<String, String> map = new LinkedHashMap<>();
		map.put("url", url);
		map.put("user-service-response", authResponse.getBody());
		return map;
	}

}
