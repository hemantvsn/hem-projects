package com.hemant.notification.service;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class NotificationServiceImpl implements NotificationService {

	private static final Logger LOG = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;

	private static final String CRICKET_SERVICE_ID = "cricket";
	
	/**
	 * More on HYSTRIX PROPERTIES IN
	 * https://github.com/Netflix/Hystrix/wiki/Configuration#intro
	 * 
	 */
	@Override
	@HystrixCommand(fallbackMethod = "getMatchDetailsByIdFallback")
	public Properties getMatchDetailsById(int matchId) {
		LOG.info("Calling the underlying service");
		List<ServiceInstance> cricketServiceInstances = discoveryClient.getInstances(CRICKET_SERVICE_ID);
		if (cricketServiceInstances.isEmpty()) {
			LOG.error("No cricket service found for ID : {}", CRICKET_SERVICE_ID);
			throw new RuntimeException("No cricket service found");
		}

		ServiceInstance cricketServiceInstance = cricketServiceInstances.get(0);
		String url = cricketServiceInstance.getUri().toString() + "/" + matchId;
		ResponseEntity<Properties> response = restTemplate.exchange(url, HttpMethod.GET, null, Properties.class);

		Properties matchDetails = response.getBody();
		LOG.info("Found match:{} for matchId:{} from underlying cricket service", matchDetails, matchId);

		return matchDetails;

	}
	
	public Properties getMatchDetailsByIdFallback(int matchId) {
		LOG.warn("HYSTRIX FALLBACK METHOD IS INVOKED");
		return new Properties();
	}
}
