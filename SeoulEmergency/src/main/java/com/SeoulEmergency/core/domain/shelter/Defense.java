package com.SeoulEmergency.core.domain.shelter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 민방위 대피소 엔티티
 */

@Getter
@Setter
@Document(collection = "defense")
public class Defense extends Shelter {

    private long headCount;               // 대피시설 인원수
    private String type;                  // 평시활용 유형
    private String isOpen;                // 개방여부
    private String date;                  // 데이터기준일자

}
