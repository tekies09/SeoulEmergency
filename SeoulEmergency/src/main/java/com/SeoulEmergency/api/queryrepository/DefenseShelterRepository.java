package com.SeoulEmergency.api.queryrepository;

import com.SeoulEmergency.core.domain.DefenseShelter;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DefenseShelterRepository
        extends MongoRepository<DefenseShelter, String>
        ,CustomDefenseShelterRepository {

}