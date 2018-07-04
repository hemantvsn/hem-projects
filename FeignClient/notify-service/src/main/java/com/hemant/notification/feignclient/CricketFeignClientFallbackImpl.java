package com.hemant.notification.feignclient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hemant.common.model.Match;

@Component
public class CricketFeignClientFallbackImpl implements CricketFeignClient {

	@Override
	public List<Match> getAllMatches() {
		return new ArrayList<>();
	}

	@Override
	public Match getMatchById(String id) {
		return null;
	}

}
