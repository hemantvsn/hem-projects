package com.hemant.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
	private DiscoveryClient discoveryClient;

	@Autowired
	private RestTemplate restTemplate;

	private String userServiceName = "user-service";

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public String getUserById(@PathVariable String id) {
		List<ServiceInstance> serviceInstances = discoveryClient.getInstances(userServiceName);

		if (serviceInstances.isEmpty()) {
			throw new IllegalArgumentException("No instance found with serviceId :" + userServiceName);
		}
		ServiceInstance userServiceInstance = serviceInstances.get(0);
		String url = userServiceInstance.getUri().toString();

		ResponseEntity<String> authResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		return authResponse.getBody();
	}

}
