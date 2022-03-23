package com.SeoulEmergency.api.service;


import com.SeoulEmergency.api.queryrepository.EarthquakeShelterRepository;
import com.SeoulEmergency.core.domain.EarthquakeShelter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EarthquakeShelterService {

    private final EarthquakeShelterRepository earthquakeShelterRepository;

//    public List<EarthquakeShelter> getEarthquakeShelters() {
//        return earthquakeShelterRepository.findAll();
//    }

    public List<EarthquakeShelter> getNearEarthquakeShelters(Double longitude, Double latitude) {
        // 입력받은 경도, 위도 정보로 point 객체를 만들어 넘김.
        Point point = new Point(longitude, latitude);

        return earthquakeShelterRepository.findByLocationNear(point);
    }
}
