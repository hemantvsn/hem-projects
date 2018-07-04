package com.hemant.notification.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.notification.feignclient.CricketFeignClient;

@RestController
public class NotificationController {
	
	@Autowired
	CricketFeignClient cricketFeignClient;

	@RequestMapping(value="", method = RequestMethod.GET)
	public Map<String, Object> get() {
		Map<String, Object> map = new HashMap<>();
		//map.put("allMatches", cricketFeignClient.getAllMatches());
		map.put("id=1", cricketFeignClient.getMatchById("ODI1"));
		map.put("id=9999", cricketFeignClient.getMatchById("ODI999"));
		
		return map;
	}
	
	
}
