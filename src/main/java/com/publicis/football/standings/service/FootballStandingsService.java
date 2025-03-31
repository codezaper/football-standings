package com.publicis.football.standings.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.publicis.football.standings.dto.StandingsResponse;
import com.publicis.football.standings.exception.StandingException;
import com.publicis.football.standings.model.Error;
import com.publicis.football.standings.model.Standing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FootballStandingsService {

    private final RestClient restClient;

    @Autowired
    public FootballStandingsService(RestClient restClient) {
        this.restClient = restClient;
    }

    public Object getStandings(String countryName, String leagueId, String teamName) {
        var standingData = fetchStandingData(leagueId);
        return switch (standingData) {
            case Error error -> error;
            case List<?> list when list.stream().allMatch(Standing.class::isInstance) -> filterStandingData(list.stream().map(Standing.class::cast).toList(), countryName, teamName);
            default -> {
                Error error = new Error();
                error.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
                error.setMessage("Unknown Response");
                throw new StandingException("InvalidStandingResponse",error);
            }
        };
    }

    private Object fetchStandingData(String leagueId) {
        return restClient.get()
                        .uri(uriBuilder -> uriBuilder
                        .queryParam("action", "get_standings")
                        .queryParam("league_id", leagueId)
                        .build())
                        .acceptCharset(StandardCharsets.UTF_8)
                        .exchange((clientRequest, clientResponse) -> {
                                if(!clientResponse.getStatusCode().is2xxSuccessful()) {
                                    Error error = new Error();
                                    error.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
                                    error.setMessage("InvalidStandingResponse");
                                    throw new StandingException("InvalidStandingResponse",error);
                                }
                                var standingResponse = Objects.requireNonNull(clientResponse.bodyTo(String.class));
                                ObjectMapper objectMapper = new ObjectMapper();
                                if(standingResponse.contains("error")) {
                                    return objectMapper.readValue(standingResponse, new TypeReference<Error>() {});
                                } else {
                                    return objectMapper.readValue(standingResponse, new TypeReference<List<Standing>>() {});
                                }
                        });
    }

    private List<StandingsResponse> filterStandingData(List<Standing> standings,String countryName,String teamName) {
        var standingsResponses = standings.stream()
                .filter(s -> s.getCountryName().equals(countryName) || s.getTeamName().equals(teamName))
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        var exactMatchStandingsResponse = standingsResponses.stream()
                .filter(s -> s.countryName().equals(countryName) && s.teamName().equals(teamName))
                .toList();

        return exactMatchStandingsResponse.isEmpty() ? standingsResponses : exactMatchStandingsResponse;
    }

    private StandingsResponse mapToResponse(Standing standing) {
        return new StandingsResponse(
                standing.getCountryName(),
                standing.getLeagueId(),
                standing.getLeagueName(),
                standing.getTeamId(),
                standing.getTeamName(),
                standing.getOverallLeaguePosition()
        );
    }
}
