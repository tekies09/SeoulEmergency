package com.SeoulEmergency.api.service;

import com.SeoulEmergency.core.domain.DefenseShelter;
import com.SeoulEmergency.core.domain.EarthquakeShelter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ShelterService")
public class ShelterServiceImpl implements ShelterService{

    @Autowired
    MongoTemplate mongoTemplate;

    // 좌표 기준 가까운 대피소 최대, 최소 거리 설정
    String maxDistance = "1000";
    String minDistance = "10";

    /**
     * 지진옥외 대피소 상세 정보 조회 - "shelter" DB의 "earthquake" Collection에서 "_id"가 일치하는 데이터 조회
     * @param shelterId
     * @return EarthquakeShelter (지진옥외 대피소 Entity)
     */
    @Override
    public EarthquakeShelter getEarthquakeDetail(String shelterId) {
        int number = Integer.parseInt(shelterId);
        Query query = new Query(Criteria.where("seqNum").is(number));
        EarthquakeShelter earthquakeDetail = mongoTemplate.findOne(query, EarthquakeShelter.class, "earthquake");
        return earthquakeDetail;
    }

    /**
     * 민방위 대피소 상세 정보 조회 - "shelter" DB의 "defense" Collection에서 "_id"가 일치하는 데이터 조회
     * @param shelterId
     * @return DefenseShelter (민방위 대피소 Entity)
     */
    @Override
    public DefenseShelter getDefenseDetail(String shelterId) {
        int number = Integer.parseInt(shelterId);
        Query query = new Query(Criteria.where("seqNum").is(number));
        DefenseShelter defenseDetail = mongoTemplate.findOne(query, DefenseShelter.class, "defense");
        return defenseDetail;
    }

    @Override
    public List<EarthquakeShelter> getNearEarthquakeShelters(String longitude, String latitude) {
        BasicQuery query = new BasicQuery("{location:{$near:{$geometry:{type:'Point', coordinates:[" + longitude + ", " + latitude + "]}, $maxDistance:" + maxDistance + ", $minDistance:" + minDistance + "}}}");
        List<EarthquakeShelter> earthquakeShelterList = mongoTemplate.find(query, EarthquakeShelter.class, "earthquake");
        return earthquakeShelterList;
    }

    @Override
    public List<DefenseShelter> getNearDefenseShelters(String longitude, String latitude) {
        BasicQuery query = new BasicQuery("{location:{$near:{$geometry:{type:'Point', coordinates:[" + longitude + ", " + latitude + "]}, $maxDistance:" + maxDistance + ", $minDistance:" + minDistance + "}}}");
        List<DefenseShelter> defenseShelterList = mongoTemplate.find(query, DefenseShelter.class, "defense");
        return defenseShelterList;
    }
}
