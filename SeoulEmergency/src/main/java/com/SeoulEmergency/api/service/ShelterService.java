package com.SeoulEmergency.api.service;

import com.SeoulEmergency.core.domain.DefenseShelter;
import com.SeoulEmergency.core.domain.EarthquakeShelter;

import java.util.List;

/**
 * 대피소 관련 비즈니스 로직을 처리하는 서비스 인터페이스
 */
public interface ShelterService {

    EarthquakeShelter getEarthquakeDetail(String shelterId);
    DefenseShelter getDefenseDetail(String shelterId);
    List<EarthquakeShelter> getNearEarthquakeShelters(String longitude, String latitude);
    List<DefenseShelter> getNearDefenseShelters(String longitude, String latitude);
}
