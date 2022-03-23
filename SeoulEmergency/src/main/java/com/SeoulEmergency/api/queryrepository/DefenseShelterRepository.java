package com.SeoulEmergency.api.queryrepository;

import com.SeoulEmergency.core.domain.DefenseShelter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DefenseShelterRepository
        extends MongoRepository<DefenseShelter, String> {

    List<DefenseShelter> findByLocationNear(Point location);
}
