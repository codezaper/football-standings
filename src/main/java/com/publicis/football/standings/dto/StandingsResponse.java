package com.publicis.football.standings.dto;

public record StandingsResponse(String countryName, String leagueId, String leagueName, String teamId, String teamName, String leaguePosition) {
}
