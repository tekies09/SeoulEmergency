package com.SeoulEmergency.api.service;

import com.SeoulEmergency.core.domain.shelter.Defense;
import com.SeoulEmergency.core.domain.shelter.Earthquake;
import com.SeoulEmergency.core.repository.DefenseRepository;
import com.SeoulEmergency.core.repository.EarthquakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("ShelterService")
public class ShelterServiceImpl implements ShelterService{

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    EarthquakeRepository earthquakeRepository;

    @Autowired
    DefenseRepository defenseRepository;

    @Override
    public Earthquake getEarthquakeDetail(String shelterId) {
        Optional<Earthquake> queryResult = earthquakeRepository.findById(shelterId);
        Earthquake earthquakeDetail = queryResult.orElse(null);
        return earthquakeDetail;
    }

    @Override
    public Defense getDefenseDetail(String shelterId) {
        Optional<Defense> queryResult = defenseRepository.findById(shelterId);
        Defense defenseDetail = queryResult.orElse(null);
        return defenseDetail;
    }
}
