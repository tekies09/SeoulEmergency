package com.SeoulEmergency.api.dto.response;

import com.SeoulEmergency.core.domain.DefenseShelter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("DefenseShelterListResponse")
public class DefenseListRes extends BaseResponseBody{

    @ApiModelProperty(name = "민방위 대피소 객체 리스트")
    List<DefenseShelter> defenseList = new ArrayList<>();

    public static DefenseListRes of(Integer statusCode, String message, List<DefenseShelter> defenseList) {
        DefenseListRes res = new DefenseListRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        for (DefenseShelter defenseShelter : defenseList) {
            res.defenseList.add(defenseShelter);
        }
        return res;
    }
}
