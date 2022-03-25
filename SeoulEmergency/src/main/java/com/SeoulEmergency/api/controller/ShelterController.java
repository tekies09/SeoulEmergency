package com.SeoulEmergency.api.controller;

import com.SeoulEmergency.api.service.DefenseShelterService;
import com.SeoulEmergency.api.service.EarthquakeShelterService;
import com.SeoulEmergency.core.domain.DefenseShelterWithDistance;
import com.SeoulEmergency.core.domain.EarthquakeShelterWithDistance;

import com.SeoulEmergency.api.dto.response.*;
import com.SeoulEmergency.api.service.ShelterService;
import com.SeoulEmergency.core.domain.DefenseShelter;
import com.SeoulEmergency.core.domain.EarthquakeShelter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 지진옥외/민방위 대피소 관련 API 요청을 처리한다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shelters")
public class ShelterController {

    @Autowired
    ShelterService shelterService;

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
    

    @GetMapping("/detail/earthquakes/{shelter_id}")
    @Operation(
            summary = "지진옥외 대피소 상세 정보 조회",
            description = "지진옥외 대피소 _id로 대피소 상세 정보를 조회한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ResponseEntity<? extends BaseResponseBody> earthquakeDetail(
            @PathVariable(name = "shelter_id")
            @Parameter(name = "shelter_id", description = "지진옥외 대피소 _id", required = true) String shelterId) {
        EarthquakeShelter earthquakeDetail = shelterService.getEarthquakeDetail(shelterId);
        // 조회 데이터 없음(404)
        if (earthquakeDetail == null) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Not Found"));
        // 조회 성공(200)
        return ResponseEntity.status(200).body(EarthquakeDetailRes.of(200, "Success", earthquakeDetail));
    }

    @GetMapping("/detail/defenses/{shelter_id}")
    @Operation(
            summary = "민방위 대피소 상세 정보 조회",
            description = "민방위 대피소 _id로  대피소 상세 정보를 조회한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ResponseEntity<? extends BaseResponseBody> defenseDetail(
            @PathVariable(name = "shelter_id")
            @Parameter(name = "shelter_id", description = "민방위 대피소 _id", required = true) String shelterId) {
        DefenseShelter defenseDetail = shelterService.getDefenseDetail(shelterId);
        // 조회 데이터 없음(404)
        if (defenseDetail == null) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Not Found"));
        // 조회 성공(200)
        return ResponseEntity.status(200).body(DefenseDetailRes.of(200, "Success", defenseDetail));
    }
}
