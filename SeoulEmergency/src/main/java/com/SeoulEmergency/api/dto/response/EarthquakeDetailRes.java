package com.SeoulEmergency.api.dto.response;

import com.SeoulEmergency.core.domain.shelter.Defense;
import com.SeoulEmergency.core.domain.shelter.Earthquake;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * API ([GET] /api/shelters/earthquakes/{shelter_id} 요청에 대한 응닶값 정의)
 */

@Getter
@Setter
@ApiModel("EarthquakeDetailRes")
public class EarthquakeDetailRes extends BaseResponseBody {

    @ApiModelProperty(name = "지진옥외 대피소 객체")
    Earthquake earthquakeDetail = new Earthquake();

    public static EarthquakeDetailRes of(Integer statusCode, String message, Earthquake earthquakeDetail) {
        EarthquakeDetailRes res = new EarthquakeDetailRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setEarthquakeDetail(earthquakeDetail);
        return res;
    }
}
