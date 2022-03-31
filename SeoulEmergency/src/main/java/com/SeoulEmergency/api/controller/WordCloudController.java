package com.SeoulEmergency.api.controller;

import com.SeoulEmergency.api.dto.response.WordCloudRes;
import com.SeoulEmergency.api.service.WordCloudService;
import com.SeoulEmergency.core.domain.WordCloud;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  워드클라우드 관련 api
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wordcloud")
public class WordCloudController {

    private final WordCloudService wordCloudService;

    @GetMapping
    @Operation(summary = "워드클라우드 데이터 반환", description = "기사 형태소분석 한 데이터들 반환")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "OK")
    })
    public ResponseEntity<List<WordCloud>> getWordList() throws Exception{


        return ResponseEntity.ok(wordCloudService.getWordList());
    }

}
