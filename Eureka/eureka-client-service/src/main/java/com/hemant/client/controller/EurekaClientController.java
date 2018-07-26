package com.hemant.client.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eureka-clients")
public class EurekaClientController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<String> getAllEurekaClients() {
		return discoveryClient.getServices();
	}

	@RequestMapping(value = "/{serviceName}", method = RequestMethod.GET)
	public Map<String, Object> getEurekaClientURLByName(@PathVariable String serviceName) {
		List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
		
		if (serviceInstances.isEmpty()) {
			throw new IllegalArgumentException("No instance found with serviceId :" + serviceName);
		}
		
		ServiceInstance instance = serviceInstances.get(0);
		
		Map<String, Object> infoMap = new LinkedHashMap<>();
		infoMap.put("host", instance.getHost());
		infoMap.put("port", instance.getPort());
		infoMap.put("uri", instance.getUri());
		infoMap.put("serviceId", instance.getServiceId());
		infoMap.put("secured", instance.isSecure());
		
		return infoMap;
	}

}
