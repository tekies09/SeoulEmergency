package com.SeoulEmergency.core.domain;


import lombok.Getter;
import org.springframework.data.geo.Point;

@Getter
public class EarthquakeShelterWithDistance extends EarthquakeShelter {
    private double distance;

    public void setDistance(final double distance) {
        this.distance = distance;
    }

    public EarthquakeShelterWithDistance(int seqNum, String name, String address, int equiArea, Point location, double distance) {
        super(seqNum, name, address, equiArea, location);
        this.distance = distance;
    }
}
