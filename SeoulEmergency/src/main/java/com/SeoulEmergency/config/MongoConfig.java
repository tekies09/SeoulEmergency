package com.SeoulEmergency.config;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoConfig {

    @Autowired
    private Environment env;

    @Bean
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(new MongoClientURI(env.getProperty("spring.data.mongodb.uri")));
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

}
