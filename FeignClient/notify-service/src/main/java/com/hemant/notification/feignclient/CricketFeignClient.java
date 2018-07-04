package com.hemant.notification.feignclient;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hemant.common.model.Match;
import com.hemant.notification.config.FeignClientConfig;

@FeignClient(value = "${cricket.service.name}",
fallbackFactory = CricketFeignClientFallbackFactory.class,
configuration = FeignClientConfig.class)
public interface CricketFeignClient {
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public List<Match> getAllMatches();
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public Match getMatchById(@PathVariable(value="id") String id);

}
