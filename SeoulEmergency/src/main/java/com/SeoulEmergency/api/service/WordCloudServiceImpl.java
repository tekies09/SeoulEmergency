package com.SeoulEmergency.api.service;

import com.SeoulEmergency.api.dto.response.WordCloudRes;
import com.SeoulEmergency.core.domain.WordCloud;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("WordCloudServie")
public class WordCloudServiceImpl implements WordCloudService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<WordCloudRes> getWordList() throws Exception {

        Query q = new Query();
        q.with(Sort.by(Sort.Order.desc("count")));


        List<WordCloud> wordCloudList = mongoTemplate.find(q.limit(20),WordCloud.class);

//        int sum=0;
//        for(WordCloud wordCloud : wordCloudList){
//            sum+=wordCloud.getCount();
//        }
        List<WordCloudRes> result= new ArrayList<>();

        for(WordCloud wordCloud : wordCloudList) {
//            double rate = (double)wordCloud.getCount()/(double)sum;

            WordCloudRes temp = new WordCloudRes(wordCloud.getWord(),wordCloud.getCount());
            result.add(temp);
        }


        return result;
    }
}
