package com.SeoulEmergency.api.queryrepository;

import com.SeoulEmergency.core.domain.DefenseShelter;
import com.SeoulEmergency.core.domain.EarthquakeShelter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface DefenseShelterRepository
        extends MongoRepository<DefenseShelter, String>
        ,CustomDefenseShelterRepository {
    List<DefenseShelter> findByNameRegex(String title);
    List<DefenseShelter> findByAddressRegex(String title);
}