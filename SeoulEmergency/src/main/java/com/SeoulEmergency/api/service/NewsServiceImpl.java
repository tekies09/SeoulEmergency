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
		Query query = new Query();
		Pageable pageable= PageRequest.of(page, limit);
		List<NewsList> list = mongoTemplate.find(query, NewsList.class);
		
		// 불러올 리스트가 있을 때만
		if(list.size()>0) {
			query.addCriteria(new Criteria().andOperator(list.toArray(new Criteria[0])));
		}
		
		return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, NewsList.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), NewsList.class)
        );
	}
	
	@Override
	public List<NewsList> getNewsList(int page, int limit) {
		Query query = new Query();
		List<NewsList> list = mongoTemplate.find(query, NewsList.class);
		
		// 불러올 리스트가 있을 때만
		if(list.size()>0) {
			query.addCriteria(new Criteria().andOperator(list.toArray(new Criteria[0])));
		}
		
		return list;
	}

}
