package com.SeoulEmergency.api.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.SeoulEmergency.core.domain.news.NewsList;

public interface NewsService {
	Page<NewsList> getNewsPage(int page, int limit);
	List<NewsList> getNewsList(int page, int limit);
	long getNewsCount();
}
