package com.SeoulEmergency.core.domain.shelter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

/**
 * 대피소 엔티티 공통 필드
 */

@Getter
@Setter
public class Shelter extends BaseEntity {

    private int seqNum;                // 시설번호
    private String name;               // 대피시설명
    private String address;            // 지번주소
    private long equiArea;             // 대피시설 면적
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;            // 위도, 경도

}
