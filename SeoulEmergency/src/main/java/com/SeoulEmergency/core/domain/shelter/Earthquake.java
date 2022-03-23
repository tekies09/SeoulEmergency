package com.SeoulEmergency.core.domain.shelter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 지진옥외 대피소 엔티티
 */

@Getter
@Setter
@Document(collection = "earthquake")
public class Earthquake extends Shelter {

}
