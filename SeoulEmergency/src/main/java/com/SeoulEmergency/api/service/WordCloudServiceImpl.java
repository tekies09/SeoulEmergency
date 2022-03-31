package com.SeoulEmergency.api.service;

import com.SeoulEmergency.core.domain.WordCloud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("WordCloudServie")
public class WordCloudServiceImpl implements WordCloudService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<WordCloud> getWordList() throws Exception {

        List<WordCloud> wordCloudList = mongoTemplate.findAll(WordCloud.class,"wordcloud");
        return wordCloudList;
    }
}
