package com.SeoulEmergency.api.service;


import com.SeoulEmergency.api.queryrepository.DefenseShelterRepository;
import com.SeoulEmergency.core.domain.DefenseShelter;
import com.SeoulEmergency.core.domain.DefenseShelterWithDistance;
import com.SeoulEmergency.core.domain.EarthquakeShelter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefenseShelterService {

    private final DefenseShelterRepository defenseShelterRepository;

    public List<DefenseShelterWithDistance> getNearDefenseShelters(Double longitude, Double latitude) {
        // 입력받은 경도, 위도 정보로 point 객체를 만들어 넘김.
        Point point = new Point(longitude, latitude);

        return defenseShelterRepository.findByLocationWithDistance(point);
    }
    public List<DefenseShelter> getnameSearchDefenseShelters(String title) {
        // 입력받은 이름으로 검색 ㄱ
        String Regtitle = ".*" + title + ".*";
        return defenseShelterRepository.findByNameRegex(Regtitle);
    }
    public List<DefenseShelter> getareaSearchDefenseShelters(String title) {
        // 입력받은 이름으로 검색 ㄱ
        String Regtitle = ".*" + title + ".*";
        return defenseShelterRepository.findByAddressRegex(Regtitle);
    }
}
