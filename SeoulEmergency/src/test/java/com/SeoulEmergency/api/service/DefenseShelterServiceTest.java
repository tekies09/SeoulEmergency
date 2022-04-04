package com.SeoulEmergency.api.service;

import com.SeoulEmergency.core.domain.DefenseShelter;
import com.SeoulEmergency.core.domain.DefenseShelterWithDistance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class DefenseShelterServiceTest {

    @Autowired
    DefenseShelterService defenseShelterService;

    @Test
    public void 근처_지진_대피소() throws Exception {
        // given (서울 시청의 위도, 경도)
        Double LONGITUDE = 126.9784147;
        Double LATITUDE = 37.5666805;

        // when
        List<DefenseShelterWithDistance> result = defenseShelterService.getNearDefenseShelters(LONGITUDE, LATITUDE);

        // then (지진 대피소 10개 목록이 잘 들어오는지 확인)
        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    public void 이름_검색() throws Exception {
        // given
        String WORD = "대학교";

        // when
        List<DefenseShelter> result = defenseShelterService.getnameSearchDefenseShelters(WORD);

        // then (검색 결과 대피소의 이름에 검색어가 포함되는지 확인)
        assertThat(result.get(0).getName()).contains(WORD);
    }

    @Test
    public void 존재하지_않는_이름_검색() throws Exception {
        // given
        String WORD = "이런이름의대피소는없다";

        // when
        List<DefenseShelter> result = defenseShelterService.getnameSearchDefenseShelters(WORD);

        // then (검색 결과가 없는지 확인)
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void 지역_검색() throws Exception {
        // given
        String LOCATION = "동대문구 이문동";

        // when
        List<DefenseShelter> result = defenseShelterService.getareaSearchDefenseShelters(LOCATION);


        // then (검색 결과 대피소의 지역에 검색어가 포함되는지 확인)
        for (DefenseShelter defenseShelter : result) {
            assertThat(defenseShelter.getAddress()).contains(LOCATION);
        }
    }

    @Test
    public void 존재하지_않는_지역_검색() throws Exception {
        // given
        String LOCATION = "고담시";

        // when
        List<DefenseShelter> result = defenseShelterService.getareaSearchDefenseShelters(LOCATION);

        // then (검색 결과가 없는지 확인)
        assertThat(result.size()).isEqualTo(0);
    }
}