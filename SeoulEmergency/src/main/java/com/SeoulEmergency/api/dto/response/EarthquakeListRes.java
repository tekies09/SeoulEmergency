package com.SeoulEmergency.api.dto.response;

import com.SeoulEmergency.core.domain.EarthquakeShelter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("EarthquakeShelterListResponse")
public class EarthquakeListRes extends BaseResponseBody {

    @ApiModelProperty(name = "지진옥외 대피소 객체 리스트")
    List<EarthquakeShelter> earthquakeList = new ArrayList<>();

    public static EarthquakeListRes of(Integer statusCode, String message, List<EarthquakeShelter> earthquakeList) {
        EarthquakeListRes res = new EarthquakeListRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        for (EarthquakeShelter earthquakeShelter : earthquakeList) {
            res.earthquakeList.add(earthquakeShelter);
        }
        return res;
    }
}
