package com.publicis.football.standings.controller;

import com.publicis.football.standings.dto.StandingsResponse;
import com.publicis.football.standings.model.Error;
import com.publicis.football.standings.service.FootballStandingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/football")
public class FootballStandingsController {

    private final FootballStandingsService footballStandingsService;

    public FootballStandingsController(FootballStandingsService footballStandingsService) {
        this.footballStandingsService = footballStandingsService;
    }


    @GetMapping("/standings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return List of Standing Response or Error in case of missing API key", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StandingsResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))}),
           })
    @Operation(summary = "Find standings of a team playing league football match using country name, league name and team name.")
    public ResponseEntity<Object> getFootballStandings(@RequestParam String countryName, @RequestParam String leagueId, @RequestParam String teamName) {
        var standingResponse = footballStandingsService.getStandings(countryName,leagueId,teamName);
        return ResponseEntity.ok(standingResponse);
    }
}
