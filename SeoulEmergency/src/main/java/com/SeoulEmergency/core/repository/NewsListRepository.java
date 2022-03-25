package com.SeoulEmergency.core.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.SeoulEmergency.core.domain.news.NewsList;

public interface NewsListRepository extends MongoRepository<NewsList, String> {
	List<NewsList> findAllByOrderByIdDesc();
}
