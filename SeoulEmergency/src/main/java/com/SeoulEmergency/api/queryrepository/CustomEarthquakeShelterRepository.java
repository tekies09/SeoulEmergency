package com.SeoulEmergency.api.queryrepository;

import com.SeoulEmergency.core.domain.EarthquakeShelterWithDistance;
import org.springframework.data.geo.Point;

import java.util.List;

public interface CustomEarthquakeShelterRepository {

    List<EarthquakeShelterWithDistance> findByLocationWithDistance(Point point);
}
