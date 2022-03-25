package com.SeoulEmergency.api.queryrepository;

import com.SeoulEmergency.core.domain.EarthquakeShelter;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface EarthquakeShelterRepository
        extends MongoRepository<EarthquakeShelter, String>
        ,CustomEarthquakeShelterRepository {

}
