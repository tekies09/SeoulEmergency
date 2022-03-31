package com.SeoulEmergency.api.controller;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/geo")
@CrossOrigin(origins="*", allowedHeaders="*")
public class GeoController extends RootController {

    @GetMapping
    public ResponseEntity<String> getReverseGeocoding(String location) {
        // 민감정보!!!
        // ------------------------------------------------------------
        String clientID = "ttsrjtbjqw";
        String clientSecret = "etV9ZLeuNvftyEOKgC88jzSndiuGNDnlTMdLpsWt";
        // ------------------------------------------------------------
        //
        String url = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc";

        RestTemplate restTemplate = new RestTemplate();

        // header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", clientID);
        headers.set("X-NCP-APIGW-API-KEY", clientSecret);

        HttpEntity request = new HttpEntity(headers);

        // parameters 설정
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("request", "coordsToaddr");
        parameters.put("coords", location);
        parameters.put("orders", "roadaddr");
        parameters.put("output", "json");

        ResponseEntity<String> response = restTemplate.exchange(
                url + "?" + this.mapToUrlParam(parameters),
                HttpMethod.GET,
                request,
                String.class
        );

        return response;
    }
    
    private static String mapToUrlParam(Map<String, String> params) {
        StringBuffer paramData = new StringBuffer();

        for (Map.Entry<String, String> param : params.entrySet()) {
                if (paramData.length() != 0) {
                    paramData.append('&');
                }
            paramData.append(param.getKey());
            paramData.append('=');
            paramData.append(String.valueOf(param.getValue()));
        }

        return paramData.toString();
    }
}
