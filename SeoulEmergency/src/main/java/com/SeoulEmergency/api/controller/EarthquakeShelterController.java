/**
 * ShelterController로 통합 (임시로 남겨둠.)
 */

//package com.SeoulEmergency.api.controller;
//
//
//import com.SeoulEmergency.api.service.EarthquakeShelterService;
//import com.SeoulEmergency.core.domain.EarthquakeShelter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
///**
// * 지진옥외/민방위 대피소 리스트 API
// */
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/shelters/earthquake")
//public class EarthquakeShelterController {
//
//    private final EarthquakeShelterService earthquakeShelterService;
//
//    /**
//     * 지진옥외 대피소의 전체 목록을 가져온다.
//     */
//    @GetMapping
//    public ResponseEntity<List<EarthquakeShelter>> findEarthquakeSheltersByDistance() {
//        return ResponseEntity.ok(earthquakeShelterService.getEarthquakeShelters());
//    }
//
//    /**
//     * 사용자의 현재 위치에서 가까운 순서대로 지진옥외 대피소 정보 10개를 가져온다.
//     */
//    @GetMapping("/nearest")
//    public ResponseEntity<List<EarthquakeShelter>> EarthquakeSheltersByDistance(
//            @RequestParam Double longitude,
//            @RequestParam Double latitude) {
//
//        return ResponseEntity.ok(earthquakeShelterService.getNearEarthquakeShelters(longitude, latitude));
//    }
//
//}
