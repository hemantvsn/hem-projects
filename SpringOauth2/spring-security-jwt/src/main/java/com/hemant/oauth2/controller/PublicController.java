package com.hemant.oauth2.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Referring to the HTTP Security logic in
 * #{com.hemant.oauth2.config.ResourceServerConfig} , the endpoints in this
 * 
 * controller can be accessed by any user - authenticated and non authenticated
 * 
 * @author hemant
 *
 */
@RestController
@RequestMapping("public")
public class PublicController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Map<String, String> getPublicData() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "pub-value-1");
		map.put("2", "pub-value-2");
		return map;
	}
}
