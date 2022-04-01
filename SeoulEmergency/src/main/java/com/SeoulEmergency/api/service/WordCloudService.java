package com.SeoulEmergency.api.service;

import com.SeoulEmergency.api.dto.response.WordCloudRes;
import com.SeoulEmergency.core.domain.WordCloud;

import java.util.List;

public interface WordCloudService {
    List<WordCloudRes> getWordList() throws Exception;
}
