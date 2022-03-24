package com.SeoulEmergency.api.dto.response;

import com.SeoulEmergency.core.domain.DefenseShelter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * API ([GET] /api/shelters/defense/{shelter_id} 요청에 대한 응닶값 정의)
 */

@Getter
@Setter
@ApiModel("DefenseDetailRes")
public class DefenseDetailRes extends BaseResponseBody {

    @ApiModelProperty(name = "민방위 대피소 객체")
    DefenseShelter defenseDetail = new DefenseShelter();

    public static DefenseDetailRes of(Integer statusCode, String message, DefenseShelter defenseDetail) {
        DefenseDetailRes res = new DefenseDetailRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setDefenseDetail(defenseDetail);
        return res;
    }
}
