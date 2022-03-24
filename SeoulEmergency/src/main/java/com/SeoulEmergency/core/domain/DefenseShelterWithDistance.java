package com.SeoulEmergency.core.domain;


import lombok.Getter;
import org.springframework.data.geo.Point;

import java.time.LocalDate;

@Getter
public class DefenseShelterWithDistance extends DefenseShelter {
    private double distance;

    public void setDistance(final double distance) {
        this.distance = distance;
    }

    public DefenseShelterWithDistance(int seqNum, String name, String address, int equiArea, Point location, String type, int headCount, String isOpen, String date, double distance) {
        super(seqNum, name, address, equiArea, location, type, headCount, isOpen, date);
        this.distance = distance;
    }
}