package com.SeoulEmergency.core.repository;

import com.SeoulEmergency.core.domain.shelter.Earthquake;
import org.springframework.data.repository.CrudRepository;

public interface EarthquakeRepository extends CrudRepository<Earthquake, String> {
}
