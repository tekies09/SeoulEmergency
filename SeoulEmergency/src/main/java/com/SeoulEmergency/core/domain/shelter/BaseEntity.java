package com.SeoulEmergency.core.domain.shelter;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * 대피소 엔티티 PK _id 필드
 */

@Getter
@Setter
public class BaseEntity {

    @Id
    private ObjectId id;

}
