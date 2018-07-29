package com.hemant.cricservice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CricketController {

	private Map<Integer, Properties> currentMatchData;

	@PostConstruct
	public void createMockData() {
		
		currentMatchData = new HashMap<>();
		
		Properties p1 = new Properties();
		p1.setProperty("MatchName", "IND VS PAK");
		p1.setProperty("Team1", "IND");
		p1.setProperty("Team2", "PAK");
		p1.setProperty("Team1 Score", "189 For 7 IN 20 Overs");
		p1.setProperty("Team2 Score", "120 For 7 IN 15 Overs");
		p1.setProperty("Status", "IN-PROGRESS");
		p1.setProperty("Current", "PAK Needs 69 Rune to Win in 30 Balls With 3 wickets in hand");
		
		currentMatchData.put(1, p1);
		
		Properties p2 = new Properties();
		p2.setProperty("MatchName", "AUS VS ENG");
		p2.setProperty("Team1", "AUS");
		p2.setProperty("Team2", "ENG");
		p2.setProperty("Team1 Score", "177 For 8 IN 20 Overs");
		p2.setProperty("Team2 Score", "157 For 9 IN 20 Overs");
		p2.setProperty("Status", "COMPLETED");
		p2.setProperty("Current", "ENG Won by 20 Runs");
		
		currentMatchData.put(2, p2);
		
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Properties getMatchById(@PathVariable(value = "id") int matchId) {
		Properties matchData = currentMatchData.get(matchId);
		if(null == matchData) {
			throw new IllegalArgumentException("No match exists for id = " + matchId);
		}
		return matchData;
	}

}
