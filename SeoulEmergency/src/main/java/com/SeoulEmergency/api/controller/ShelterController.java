package com.SeoulEmergency.api.controller;

import com.SeoulEmergency.api.dto.response.BaseResponseBody;
import com.SeoulEmergency.api.dto.response.DefenseDetailRes;
import com.SeoulEmergency.api.dto.response.EarthquakeDetailRes;
import com.SeoulEmergency.api.service.ShelterService;
import com.SeoulEmergency.core.domain.shelter.Defense;
import com.SeoulEmergency.core.domain.shelter.Earthquake;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "대피소 검색 및 조회 API", tags = {"Shelters"})
@RestController
@RequestMapping("/api/shelters")
public class ShelterController {

    @Autowired
    ShelterService shelterService;

    @GetMapping("/earthquakes/{shelter_id}")
    @ApiOperation(
            value = "지진옥외 대피소 상세 정보 조회",
            notes = "지진옥외 대피소 시설번호로 대피소 상세 정보를 조회한다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공", response = EarthquakeDetailRes.class)
    })
    public ResponseEntity<? extends BaseResponseBody> earthquakeDetail(
            @PathVariable(name = "shelter_id")
            @ApiParam(value = "지진옥외 대피소 _id", required = true) String shelterId) {
        Earthquake earthquakeDetail = shelterService.getEarthquakeDetail(shelterId);
        if (earthquakeDetail == null) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Not Found"));
        return ResponseEntity.status(200).body(EarthquakeDetailRes.of(200, "Success", earthquakeDetail));
    }

    @GetMapping("/defenses/{shelter_id}")
    @ApiOperation(
            value = "민방위 대피소 상세 정보 조회",
            notes = "민방위 대피소 시설번호로 대피소 상세 정보를 조회한다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공", response = DefenseDetailRes.class)
    })
    public ResponseEntity<? extends BaseResponseBody> defenseDetail(
            @PathVariable(name = "shelter_id")
            @ApiParam(value = "민방위 대피소 _id", required = true) String shelterId) {
        Defense defenseDetail = shelterService.getDefenseDetail(shelterId);
        if (shelterService == null) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Not Found"));
        return ResponseEntity.status(200).body(DefenseDetailRes.of(200, "Success", defenseDetail));
    }
}
