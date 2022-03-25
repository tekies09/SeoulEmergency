package com.SeoulEmergency.api.queryrepository;

import com.SeoulEmergency.core.domain.DefenseShelterWithDistance;
import org.springframework.data.geo.Point;

import java.util.List;

public interface CustomDefenseShelterRepository {

    List<DefenseShelterWithDistance> findByLocationWithDistance(Point point);
}
