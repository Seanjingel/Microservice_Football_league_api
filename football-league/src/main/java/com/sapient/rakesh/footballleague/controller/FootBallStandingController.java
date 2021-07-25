package com.sapient.rakesh.footballleague.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.rakesh.footballleague.dto.TeamStandingDto;
import com.sapient.rakesh.footballleague.model.TeamStandingRequest;
import com.sapient.rakesh.footballleague.service.TeamStandingService;

@RestController
@RequestMapping("/team/standing")
public class FootBallStandingController {

	@Autowired
	private TeamStandingService teamStandingService;


	@GetMapping
	public ResponseEntity<TeamStandingDto> getStandings(@Valid TeamStandingRequest teamStandingRequest) {
		return ResponseEntity.ok(teamStandingService.getTeamStanding(teamStandingRequest));
	}
	
	

}
