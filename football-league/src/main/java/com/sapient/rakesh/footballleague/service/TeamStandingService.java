package com.sapient.rakesh.footballleague.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.rakesh.footballleague.client.TeamStandingRestClient;
import com.sapient.rakesh.footballleague.dto.TeamStandingDto;
import com.sapient.rakesh.footballleague.exception.BadRequestException;
import com.sapient.rakesh.footballleague.model.Country;
import com.sapient.rakesh.footballleague.model.Leagues;
import com.sapient.rakesh.footballleague.model.TeamStanding;
import com.sapient.rakesh.footballleague.model.TeamStandingRequest;

@Service
public class TeamStandingService {

	@Autowired
	private TeamStandingRestClient teamStandingRestClient;


	public TeamStandingDto getTeamStanding(TeamStandingRequest teamStandingRequest) {
		
		TeamStanding teamStandingDefault = getDefaultTeamStanding(teamStandingRequest);
//		System.out.println(teamStandingDefault.toString());
		
		//method to find country ID
		List<Country> countries = getCountries();
		Country country = getCountryByName(teamStandingRequest, countries);
		if (!isValidateCountryResponse(teamStandingRequest, teamStandingDefault, country)) {
			return TeamStandingDto.DatafromTeamStanding(teamStandingDefault);
		}
		teamStandingDefault.setCountryId(country.getId());

		//method to Find League ID
		List<Leagues> leaguesList = getLeagues(country.getId());
		Leagues leagues = getLeaguesByName(teamStandingRequest, leaguesList);
		if (!isValidLeagueResponse(teamStandingRequest, teamStandingDefault, leagues)) {
			return (TeamStandingDto.DatafromTeamStanding(teamStandingDefault));
		}
		teamStandingDefault.setLeagueId(leagues.getLeagueId());
		
		//Method to get Position
		List<TeamStanding> teamStandings = getTeamStanding(leagues.getLeagueId());
		TeamStanding teamStandingsFiltered = getFilteredTeamStanding(teamStandingRequest, teamStandings);
		teamStandingsFiltered.setCountryId(country.getId());
		if (teamStandingsFiltered.getTeamId() == 0) {
			return TeamStandingDto.DatafromTeamStanding(teamStandingDefault);
		}

		return TeamStandingDto.DatafromTeamStanding(teamStandingsFiltered);
	}

	private Country getCountryByName(TeamStandingRequest teamStandingRequest, List<Country> countries) {
		return countries.stream().filter(c -> teamStandingRequest.getCountryName().equalsIgnoreCase(c.getName()))
				.findFirst().orElse(null);
	}

	private Leagues getLeaguesByName(TeamStandingRequest teamStandingRequest, List<Leagues> leaguesList) {
		return leaguesList.stream().filter(l -> teamStandingRequest.getLeagueName().equalsIgnoreCase(l.getLeagueName()))
				.findAny().orElse(null);
	}

	private TeamStanding getFilteredTeamStanding(TeamStandingRequest teamStandingRequest,
			List<TeamStanding> teamStandings) {
		return teamStandings.stream().filter(t -> teamStandingRequest.getTeamName().equalsIgnoreCase(t.getTeamName()))
				.findFirst().orElse(null);
	}

	private boolean isValidLeagueResponse(TeamStandingRequest teamStandingRequest, TeamStanding teamStandingDefault,
			Leagues leagues) {
		if (Objects.isNull(leagues)) {
			throw new BadRequestException("League not found " + teamStandingRequest.getLeagueName());
		}
		if (leagues.getLeagueId() == 0) {
			return false;
		}
		return true;
	}

	private boolean isValidateCountryResponse(TeamStandingRequest teamStandingRequest, TeamStanding teamStandingDefault,
			Country country) {
		if (Objects.isNull(country)) {
			throw new BadRequestException("Country Not found " + teamStandingRequest.getCountryName());
		}

		if (country.getId() == 0) {
			teamStandingDefault.setCountryId(0);
			return false;
		}
		return true;
	}

	private TeamStanding getDefaultTeamStanding(TeamStandingRequest teamStandingRequest) {
		TeamStanding teamStanding = new TeamStanding();
		teamStanding.setTeamName(teamStandingRequest.getTeamName());
		teamStanding.setCountryName(teamStandingRequest.getCountryName());
		teamStanding.setLeagueName(teamStandingRequest.getLeagueName());
		return teamStanding;
	}

	private List<Country> getCountries() {
		return new ArrayList<>(Arrays.asList(teamStandingRestClient.getCountries()));
	}

	private List<Leagues> getLeagues(int countryId) {
		return new ArrayList<>(Arrays.asList(teamStandingRestClient.getLeagues(countryId)));
	}

	private List<TeamStanding> getTeamStanding(int leagueId) {
		return new ArrayList<>(Arrays.asList(teamStandingRestClient.getTeamStanding(leagueId)));
	}

}
