package com.SeoulEmergency.api.service;

import com.SeoulEmergency.core.domain.shelter.Defense;
import com.SeoulEmergency.core.domain.shelter.Earthquake;

/**
 * 대피소 관련 비즈니스 로직을 처리하는 서비스 인터페이스
 */
public interface ShelterService {

    Earthquake getEarthquakeDetail(String shelterId);
    Defense getDefenseDetail(String shelterId);
}
