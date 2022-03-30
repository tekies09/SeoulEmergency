package com.SeoulEmergency.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.SeoulEmergency.core.domain.news.NewsList;

@Service("NewsService")
public class NewsServiceImpl implements NewsService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Page<NewsList> getNewsPage(int page, int limit) {
		// TODO Auto-generated method stub
		Pageable pageable= PageRequest.of(page, limit);
		Query query = new Query().with(pageable);
		List<NewsList> list = mongoTemplate.find(query, NewsList.class);
		
		return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), NewsList.class)
        );
	}
	
	@Override
	public List<NewsList> getNewsList(int page, int limit) {
		Query query = new Query();
		List<NewsList> list = mongoTemplate.find(query, NewsList.class);
		// 뉴스 내용을 40자 이내로 자르는 건 크롤링 파트에서 맡아도 될 것 같음.
		
		return list;
	}

}
