package com.hemant.notification.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.notification.service.NotificationService;

@RestController
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	@RequestMapping(value = "/{matchId}", method = RequestMethod.GET)
	public Map<String, Object> getMatchNotifications(@PathVariable int matchId) {
		Map<String, Object> map = new HashMap<>();
		map.put("currentTime", new Date());
		map.put("latestScore", notificationService.getMatchDetailsById(matchId));
		map.put("nextUpdateAvailableIn", "60 sec");
		map.put("userId", UUID.randomUUID().toString());

		return map;
	}
}
