package com.hemant.cricservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.common.model.Inning;
import com.hemant.common.model.Match;
import com.hemant.common.model.Team;

@RestController
public class CricketController {
	
	private List<Match> matches = new ArrayList<>();
	
	@PostConstruct
	public void createMockData() {
		/*
		 * Match 1 - Ind vs Pak
		 */
		Inning indInn = Inning.builder().runs(180).overs("20").wickets(6).build();
		Inning pakInn = Inning.builder().runs(100).overs("16.3").wickets(3).build();

		Team ind = Team.builder().id("IND").name("INDIA").inning(indInn).build();
		Team pak = Team.builder().id("PAK").name("PAK").inning(pakInn).build();

		Match indVsPak = Match.builder().battingTeam(ind).bowlingTeam(pak).id("ODI1").matchName("IND VS PAK ODI 1").build();
		matches.add(indVsPak);
		
		/**
		 * Match 2 - Aus vs SA
		 */
		
		Inning ausInn = Inning.builder().runs(150).overs("20").wickets(10).build();
		Inning saInn = Inning.builder().runs(100).overs("15.3").wickets(6).build();

		Team aus = Team.builder().id("AUS").name("AUSTRALIA").inning(ausInn).build();
		Team sa = Team.builder().id("SA").name("SOUTH AFRICA").inning(saInn).build();

		Match match = Match.builder().battingTeam(aus).bowlingTeam(sa).id("ODI2").matchName("IND VS PAK ODI 1").build();
		matches.add(match);
	
	}

	@RequestMapping(value = "", method=RequestMethod.GET)
	public List<Match> getAllMatches() {
		return matches;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public Match getMatchById(@PathVariable(value="id") String id) {
		for (Match m : matches) {
			if(m.getId().equalsIgnoreCase(id)) {
				return m;
			}
		}
		
		throw new IllegalArgumentException("No match exists for id :" + id);
	}

}
