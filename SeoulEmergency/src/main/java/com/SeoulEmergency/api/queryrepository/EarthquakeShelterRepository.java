package com.SeoulEmergency.api.queryrepository;

import com.SeoulEmergency.core.domain.EarthquakeShelter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EarthquakeShelterRepository
        extends MongoRepository<EarthquakeShelter, String> {

    List<EarthquakeShelter> findByLocationNear(Point location);
}
