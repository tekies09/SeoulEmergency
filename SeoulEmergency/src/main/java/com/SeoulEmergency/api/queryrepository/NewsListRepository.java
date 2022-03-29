package com.SeoulEmergency.api.queryrepository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.SeoulEmergency.core.domain.news.NewsList;

public interface NewsListRepository {
	List<NewsList> findNewsListByPage(Pageable pageable);
}
