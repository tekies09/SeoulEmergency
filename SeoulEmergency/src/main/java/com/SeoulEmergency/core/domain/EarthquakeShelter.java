package com.SeoulEmergency.core.domain;


import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "earthquake")
@Getter
public class EarthquakeShelter {

    @Id
    private ObjectId _id;
    // 시설번호
    private int seqNum;
    // 수용시설명
    private String name;
    // 상세주소
    private String address;
    // 시설면적
    private int equiArea;
    // [경도, 위도]
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;

    protected EarthquakeShelter(int seqNum, String name, String address, int equiArea, Point location) {
        this.seqNum = seqNum;
        this.name = name;
        this.address = address;
        this.equiArea = equiArea;
        this.location = location;
    }
}
