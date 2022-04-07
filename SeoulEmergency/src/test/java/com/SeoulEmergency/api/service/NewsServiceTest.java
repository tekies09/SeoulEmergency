package com.SeoulEmergency.api.service;

import com.SeoulEmergency.core.domain.news.NewsList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class NewsServiceTest {

    @Autowired
    NewsServiceImpl newsServiceImpl;

    @Test
    public void 뉴스_페이징() throws Exception {
        // given
        int PAGE = 0;
        int LIMIT = 4;

        // when
        Page<NewsList> newsPage = newsServiceImpl.getNewsPage(PAGE, LIMIT);

        // then (뉴스 페이지네이션이 잘 동작하는지 확인)
        assertThat(newsPage.getPageable().getPageNumber()).isEqualTo(PAGE);
        assertThat(newsPage.getSize()).isEqualTo(LIMIT);
    }

}