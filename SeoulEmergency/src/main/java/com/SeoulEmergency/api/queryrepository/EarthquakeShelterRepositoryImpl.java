package com.SeoulEmergency.api.queryrepository;

import com.SeoulEmergency.core.domain.EarthquakeShelter;
import com.SeoulEmergency.core.domain.EarthquakeShelterWithDistance;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Repository;


import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@RequiredArgsConstructor
@Repository
public class EarthquakeShelterRepositoryImpl implements CustomEarthquakeShelterRepository {

    private final MongoOperations operations;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 사용자로부터의 거리순으로 지진옥외 대피소 10개의 정보를 거리와 함께 리턴한다.
     */
    public List<EarthquakeShelterWithDistance> findByLocationWithDistance(Point point) {

        final NearQuery nearQuery = NearQuery.near(point);

        // 10개의 데이터를 가져온다.
        nearQuery.limit(10);
        nearQuery.spherical(true);

        // 사용자로부터의 거리를 "distance" 필드명으로 추가한다.
        final Aggregation a = newAggregation(geoNear(nearQuery, "distance"));

        final AggregationResults<EarthquakeShelterWithDistance> results =
                mongoTemplate.aggregate(a, EarthquakeShelter.class, EarthquakeShelterWithDistance.class);

        // 각 distance 필드에 10,000,000을 곱하고 소수점 이하는 버린다.
        for (EarthquakeShelterWithDistance result : results) {
            result.setDistance(Math.floor(result.getDistance() * 10000000));
        }

        //  for (EarthquakeShelterWithDistance result : results) {
        //      System.out.println(result.getName() + " | " + result.getDistance());
        //  }

        return results.getMappedResults();
    }





}
