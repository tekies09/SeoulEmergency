package com.SeoulEmergency.api.controller;


import com.SeoulEmergency.api.service.DefenseShelterService;
import com.SeoulEmergency.api.service.EarthquakeShelterService;
import com.SeoulEmergency.core.domain.DefenseShelterWithDistance;
import com.SeoulEmergency.core.domain.EarthquakeShelterWithDistance;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 지진옥외/민방위 대피소 관련 API 요청을 처리한다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shelters")
public class ShelterController {

    private final EarthquakeShelterService earthquakeShelterService;
    private final DefenseShelterService defenseShelterService;

    /**
     * 사용자의 현재 위치에서 가까운 순서대로 지진옥외 대피소 정보를 가져온다.
     */
    @GetMapping(value = "/list/earthquakes")
    @Operation(summary = "지진 대피소 리스트 조회", description = "사용자의 현재 위치에서 가까운 순으로 민방위 대피소를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<EarthquakeShelterWithDistance>> EarthquakeSheltersByDistance(
            @RequestParam Double longitude,
            @RequestParam Double latitude) {

        return ResponseEntity.ok(earthquakeShelterService.getNearEarthquakeShelters(longitude, latitude));
    }

    /**
     * 사용자의 현재 위치에서 가까운 순서대로 민방위 대피소 정보를 가져온다.
     */
    @GetMapping(value = "/list/defenses")
    @Operation(summary = "민방위 대피소 리스트 조회", description = "사용자의 현재 위치에서 가까운 순으로 민방위 대피소를 탐색한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<DefenseShelterWithDistance>> DefenseSheltersByDistance(
            @RequestParam Double longitude,
            @RequestParam Double latitude) {

        return ResponseEntity.ok(defenseShelterService.getNearDefenseShelters(longitude, latitude));
    }
}
