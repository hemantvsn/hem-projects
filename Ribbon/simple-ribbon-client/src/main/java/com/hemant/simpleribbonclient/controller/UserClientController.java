package com.hemant.simpleribbonclient.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * In typical Eureka Client Application,
 * we autowire #{org.springframework.cloud.client.discovery.DiscoveryClient}
 * 
 * The instance is selected via following logic:
 * <code>
 * 	 @Autowired
 *   private DiscoveryClient discoveryClient;
 *   
 *   @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
 *   public Map<String, String> getUserById(@PathVariable String id) {
 *   List<ServiceInstance> serviceInstances = discoveryClient.getInstances(userServiceName);
 *   if (serviceInstances.isEmpty()) {
 *   	throw new IllegalArgumentException("No instance found with serviceId :" + userServiceName);
 *   }
 *   ServiceInstance userServiceInstance = serviceInstances.get(0);
 *   String url = userServiceInstance.getUri().toString() + "/" + id;
 *   ...
 * 
 * </code>
 * The drawback of this approach is at line: 
 * ServiceInstance userServiceInstance = serviceInstances.get(0);
 * 
 * even if multiple service instances are available, we are choosing the same every time.
 * 
 * With Ribbon, we use 
 * 1. #{LoadBalancerClient} instead of #{DiscoveryClient}
 * 2. The instance is chosen via following line :
 * ServiceInstance userServiceInstance = loadBalancerClient.choose(userServiceName);
 * 
 * Thus load-balancer determines which instance to choose.
 * 
 * @author hemant
 *
 */
@RestController
public class UserClientController {

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	private RestTemplate restTemplate = new RestTemplate();

	private final String userServiceName = "user-service";

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public Map<String, String> getUserById(@PathVariable String id) {
		ServiceInstance userServiceInstance = loadBalancerClient.choose(userServiceName);
		String url = userServiceInstance.getUri().toString() + "/" + id;
		ResponseEntity<String> authResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		
		Map<String, String> map = new LinkedHashMap<>();
		map.put("url", url);
		map.put("user-service-response", authResponse.getBody());
		
		return map;
	}

}
