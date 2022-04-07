package com.SeoulEmergency.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wordcloud")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class WordCloud {

    @Id
    private int _id;
    //단어
    private String word;
    //횟수
    private int count;

    public WordCloud(String word ,int count){
        this.word=word;
        this.count=count;
    }
}
