package com.SeoulEmergency.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordCloudRes {

    private String word;
    private int count;

    public WordCloudRes(String word, int count){
        this.word=word;
        this.count=count;
    }
}
