package com.SeoulEmergency.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SeoulEmergency.api.service.NewsService;
import com.SeoulEmergency.core.domain.news.NewsList;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins="*", allowedHeaders="*")
@Api(value = "뉴스 리스트 API", tags="뉴스 리스트 API")
public class NewsController extends RootController {

	@Autowired
	private NewsService newsService;
//	private final NewsListRepository newsListRepository;
//
//	@Autowired
//	public NewsController(NewsListRepository newsListRepository) {
//		this.newsListRepository = newsListRepository;
//	}

	@GetMapping("/list")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "찾을 수 없는 페이지입니다."),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
	})
	@Operation(summary = "뉴스 정보 리스트 반환 API", description="모든 뉴스의 제목과 약 100자 가량의 내용 미리보기, 링크를 반환해주는 API입니다.")
	public ResponseEntity<List<NewsList>> showNewsList(
			@RequestParam(value="page", defaultValue = "1") int page,
			@RequestParam(value="limit", defaultValue = "1") int limit
	) {
		// ���� �����ؾ� ��. ���� ��� ���� �� ��������
		List<NewsList> responseDto = newsService.getNewsList(page, limit);
		return ResponseEntity.status(200).body(responseDto);
	}
	
	@GetMapping("/list/page")
	@Operation(summary = "뉴스 정보 리스트 페이징 반환 API", description = "리밋별 페이지만큼 해당되는 뉴스의 제목과 약 100자 가량의 내용 미리보기, 링크를 반환해주는 API입니다.")
	public ResponseEntity<Page<NewsList>> showNewsListPage(
			@RequestParam(value="page", defaultValue = "1") int page,
			@RequestParam(value="limit", defaultValue = "1") int limit
			) {
		Page<NewsList> responseDto = newsService.getNewsPage(page, limit);
		return ResponseEntity.status(200).body(responseDto);
	}
}
