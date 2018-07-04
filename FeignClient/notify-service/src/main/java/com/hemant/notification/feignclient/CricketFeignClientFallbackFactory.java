package com.hemant.notification.feignclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class CricketFeignClientFallbackFactory implements FallbackFactory<CricketFeignClient> {

	@Autowired
	private CricketFeignClientFallbackImpl cricketClientFallback;
	
	private static final Logger LOG = LoggerFactory.getLogger(CricketFeignClientFallbackFactory.class);
	
	@Override
	public CricketFeignClient create(Throwable cause) {
		LOG.error("Error occured :", cause);
		return cricketClientFallback;
	}
	
	
}
