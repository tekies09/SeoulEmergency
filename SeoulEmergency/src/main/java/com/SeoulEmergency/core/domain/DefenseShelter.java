package com.SeoulEmergency.core.domain;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document(collection = "defense")
@Getter
public class DefenseShelter {

    @Id
    private ObjectId _id;
    // 시설번호
    private int seqNum;
    // 대피시설명
    private String name;
    // 지번주소
    private String address;
    // 면적
    private int equiArea;
    // [경도, 위도]
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;

    // only in Defense Shelter
    // 활용유형
    private String type;
    // 인원수
    private int headCount;
    // 개방 여부
    private String isOpen;
    // 데이터 기준일자
    private String date;

    protected DefenseShelter(int seqNum, String name, String address, int equiArea, Point location, String type, int headCount, String isOpen, String date) {
        this.seqNum = seqNum;
        this.name = name;
        this.address = address;
        this.equiArea = equiArea;
        this.location = location;
        this.type = type;
        this.headCount = headCount;
        this.isOpen = isOpen;
        this.date = date;
    }
}
