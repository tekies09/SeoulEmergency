<template>
  <div class="search">
      <b-input-group>
        <b-form-select v-model="$store.state.category" :options="$store.state.options" @change="onChange($store.state.category)"></b-form-select>

        <b-form-select v-model="$store.state.category2" :options="$store.state.options2" @change="onChange2($store.state.category2)"></b-form-select>

        <b-form-input 
          type="search"
          placeholder="대피소 검색"
          @keyup.enter="searchClicked"
          @keypress="searchKoreanOnly"
          list="seoul-district-list"
          :state="searchInputState"
          aria-describedby="input-live-help input-live-feedback"
          v-model="searchInput">
        </b-form-input>

        <datalist id="seoul-district-list">
          <option v-for="district in districts" :key="district">{{ district }}</option>
        </datalist>

        <b-button 
          variant="outline-secondary"
          @click="searchClicked">
          <img src="../assets/search.svg" alt="Search Icon" class="search-icon">
        </b-button>
      </b-input-group>

      <b-form-invalid-feedback v-if="searchInput.length === 1" id="input-live-feedback">
        한글로 2글자 이상 입력해주세요.
      </b-form-invalid-feedback>
      
      <p>지도 중심점 주소: {{ mapAddress }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchInput: '',
      categoryInput: null,
      categoryInput2: null,
      mapAddress: '서울 시청',
      districts: ['강남구', '강동구', '강북구', '강서구', '관악구', '광진구', '구로구', '금천구', '노원구', '도봉구', '동대문구', '동작구', '마포구', '서대문구', '서초구', '성동구', '성북구', '송파구', '양천구', '영등포구', '용산구', '은평구', '종로구', '중구', '중랑구', 
      '강남구 신사동', '강남구 압구정동', '강남구 논현동', '강남구 청담동', '강남구 삼성동', '강남구 대치동', '강남구 역삼동', '강남구 도곡동', '강남구 개포동', '강남구 일원동', '강남구 수서동', '강남구 세곡동', '강남구 자곡동', '강남구 율현동',
      '강동구 강일동', '강동구 상일동', '강동구 고덕동', '강동구 명일동', '강동구 암사동', '강동구 천호동', '강동구 성내동', '강동구 길동', '강동구 둔촌동',
      '강북구 미아동', '강북구 번동', '강북구 수유동', '강북구 우이동',
      '강서구 염창동', '강서구 등촌동', '강서구 화곡동', '강서구 내발산동', '강서구 가양동', '강서구 마곡동', '강서구 외발산동', '강서구 공항동', '강서구 과해동', '강서구 오곡동', '강서구 오쇠동', '강서구 방화동', '강서구 개화동',
      '관악구 봉천동', '관악구 남현동', '관악구 신림동', 
      '광진구 중곡동', '광진구 능동', '광진구 구의동', '광진구 광장동', '광진구 자양동', '광진구 화양동', '광진구 군자동', 
      '구로구 신도림동', '구로구 구로동', '구로구 가리봉동', '구로구 고척동', '구로구 개봉동', '구로구 오류동', '구로구 천왕동', '구로구 항동', '구로구 온수동', '구로구 궁동',
      '금천구 가산동', '금천구 독산동', '금천구 시흥동', 
      '노원구 월계동', '노원구 공릉동', '노원구 하계동', '노원구 중계동', '노원구 상계동',
      '도봉구 쌍문동', '도봉구 방학동', '도봉구 창동', '도봉구 도봉동',
      '동대문구 신설동', '동대문구 용두동', '동대문구 제기동', '동대문구 전농동', '동대문구 답십리동', '동대문구 장안동', '동대문구 청량리동', '동대문구 회기동', '동대문구 휘경동', '동대문구 이문동',
      '동작구 노량진동', '동작구 본동', '동작구 상도동', '동작구 상도1동', '동작구 흑석동', '동작구 사당동', '동작구 동작동', '동작구 대방동', '동작구 신대방동', 
      '마포구 아현동', '마포구 공덕동', '마포구 염리동', '마포구 신공덕동', '마포구 도화동', '마포구 마포동', '마포구 용강동', '마포구 토정동', '마포구 대흥동', '마포구 신수동', '마포구 노고산동', '마포구 구수동', '마포구 현석동', '마포구 신정동', '마포구 창전동', '마포구 상수동', '마포구 하중동', '마포구 당인동', '마포구 서교동', '마포구 동교동', '마포구 합정동', '마포구 망원동', '마포구 연남동', '마포구 성산동', '마포구 중동', '마포구 상암동', 
      '서대문구 충정로2가', '서대문구 충정로3가', '서대문구 합동', '서대문구 미근동', '서대문구 북아현동', '서대문구 천연동', '서대문구 냉천동', '서대문구 옥천동', '서대문구 영천동', '서대문구 현저동', '서대문구 대신동', '서대문구 대현동', '서대문구 신촌동', '서대문구 봉원동', '서대문구 창천동', '서대문구 연희동', '서대문구 홍제동', '서대문구 홍은동', '서대문구 남가좌동', '서대문구 북가좌동', 
      '서초구 서초동', '서초구 방배동', '서초구 잠원동', '서초구 반포동', '서초구 양재동', '서초구 우면동', '서초구 원지동', '서초구 염곡동', '서초구 신원동', '서초구 내곡동', 
      '성동구 하왕십리동', '성동구 상왕십리동', '성동구 도선동', '성동구 홍익동', '성동구 마장동', '성동구 사근동', '성동구 행당동', '성동구 응봉동', '성동구 금호동1가', '성동구 금호동2가', '성동구 금호동3가', '성동구 금호동4가', '성동구 옥수동', '성동구 성수동1가', '성동구 성수동2가', '성동구 송정동', '성동구 용답동', 
      '성북구 성북동', '성북구 성북동1가', '성북구 돈암동', '성북구 동소문동1가', '성북구 동소문동2가', '성북구 동소문동3가', '성북구 동소문동4가', '성북구 동소문동5가', '성북구 동소문동6가', '성북구 동소문동7가', '성북구 삼선동1가', '성북구 삼선동2가', '성북구 삼선동3가', '성북구 삼선동4가', '성북구 삼선동5가', '성북구 동선동1가', '성북구 동선동2가', '성북구 동선동3가', '성북구 동선동4가', '성북구 동선동5가', '성북구 안암동1가', '성북구 안암동2가', '성북구 안암동3가', '성북구 안암동4가', '성북구 안암동5가', '성북구 보문동1가', '성북구 보문동2가', '성북구 보문동3가', '성북구 보문동4가', '성북구 보문동5가', '성북구 보문동6가', '성북구 보문동7가', '성북구 정릉동', '성북구 길음동', '성북구 종암동', '성북구 하월곡동', '성북구 상월곡동', '성북구 장위동', '성북구 석관동',
      '송파구 잠실동', '송파구 신천동', '송파구 풍납동', '송파구 송파동', '송파구 석촌동', '송파구 삼전동', '송파구 가락동', '송파구 문정동', '송파구 장지동', '송파구 방이동', '송파구 오금동', '송파구 거여동', '송파구 마천동',
      '양천구 신정동', '양천구 목동', '양천구 신월동', 
      '영등포구 영등포동', '영등포구 영등포동1가', '영등포구 영등포동2가', '영등포구 영등포동3가', '영등포구 영등포동4가', '영등포구 영등포동5가', '영등포구 영등포동6가', '영등포구 영등포동7가', '영등포구 영등포동8가', '영등포구 여의도동', '영등포구 당산동1가', '영등포구 당산동2가', '영등포구 당산동3가', '영등포구 당산동4가', '영등포구 당산동5가', '영등포구 당산동6가', '영등포구 당산동', '영등포구 도림동', '영등포구 문래동1가', '영등포구 문래동2가', '영등포구 문래동3가', '영등포구 문래동4가', '영등포구 문래동5가', '영등포구 문래동6가', '영등포구 양평동1가', '영등포구 양평동2가', '영등포구 양평동3가', '영등포구 양평동4가', '영등포구 양평동5가', '영등포구 양평동6가', '영등포구 양화동', '영등포구 신길동', '영등포구 대림동', '영등포구 양평동', 
      '용산구 후암동', '용산구 용산동1가', '용산구 용산동2가', '용산구 용산동3가', '용산구 용산동4가', '용산구 용산동5가', '용산구 용산동6가', '용산구 갈월동', '용산구 남영동', '용산구 동자동', '용산구 서계동', '용산구 청파동1가', '용산구 청파동2가', '용산구 청파동3가', '용산구 원효로1가', '용산구 원효로2가', '용산구 원효로3가', '용산구 원효로4가', '용산구 신창동', '용산구 산천동', '용산구 청암동', '용산구 효창동', '용산구 도원동', '용산구 용문동', '용산구 문배동', '용산구 신계동', '용산구 한강로1가', '용산구 한강로2가', '용산구 한강로3가', '용산구 이촌동', '용산구 이태원동', '용산구 한남동', '용산구 동빙고동', '용산구 서빙고동', '용산구 주성동', '용산구 보광동',
      '은평구 수색동', '은평구 녹번동', '은평구 불광동', '은평구 갈현동', '은평구 구산동', '은평구 대조동', '은평구 응암동', '은평구 역촌동', '은평구 신사동', '은평구 중산동', '은평구 진관동',
      '종로구 청운동', '종로구 신교동', '종로구 궁정동', '종로구 효자동', '종로구 창성동', '종로구 통의동', '종로구 적선동', '종로구 통인동', '종로구 누상동', '종로구 누하동', '종로구 옥인동', '종로구 체부동', '종로구 필운동', '종로구 내자동', '종로구 사직동', '종로구 도렴동', '종로구 당주동', '종로구 내수동', '종로구 세종로', '종로구 신문로1가', '종로구 신문로2가', '종로구 청진동', '종로구 서린동', '종로구 수송동', '종로구 중학동', '종로구 종로1가', '종로구 공평동', '종로구 관훈동', '종로구 견지동', '종로구 와룡동', '종로구 권농동', '종로구 운니동', '종로구 익선동', '종로구 경운동', '종로구 관철동', '종로구 인사동', '종로구 낙원동', '종로구 종로2가', '종로구 팔판동', '종로구 삼청동', '종로구 안국동', '종로구 소격동', '종로구 화동', '종로구 사간동', '종로구 송현동', '종로구 가회동', '종로구 재동', '종로구 계동', '종로구 원서동', '종로구 훈정동', '종로구 묘동', '종로구 봉익동', '종로구 돈의동', '종로구 장사동', '종로구 관수동', '종로구 종로3가', '종로구 인의동', '종로구 예지동', '종로구 원남동', '종로구 연지동', '종로구 종로4가', '종로구 효제동', '종로구 종로5가', '종로구 종로6가', '종로구 이화동', '종로구 연건동', '종로구 충신동', '종로구 동숭동', '종로구 혜화동', '종로구 명륜1가', '종로구 명륜2가', '종로구 명륜3가', '종로구 명륜4가', '종로구 창신동', '종로구 숭인동', '종로구 교남동', '종로구 평동', '종로구 송월동', '종로구 홍파동', '종로구 교북동', '종로구 행촌동', '종로구 구기동', '종로구 평창동', '종로구 부암동', '종로구 홍지동', '종로구 신영동', '종로구 무악동',
      '중구 무교동', '중구 다동', '중구 태평로1가', '중구 을지로1가', '중구 을지로2가', '중구 남대문로1가', '중구 삼각동', '중구 수하동', '중구 장교동', '중구 수표동', '중구 소공동', '중구 남창동', '중구 북창동', '중구 태평로2가', '중구 남대문로2가', '중구 남대문로3가', '중구 남대문로4가', '중구 남대문로5가', '중구 봉래동1가', '중구 봉래동2가', '중구 회현동1가', '중구 회현동2가', '중구 회현동3가', '중구 충무로1가', '중구 충무로2가', '중구 명동1가', '중구 명동2가', '중구 남산동1가', '중구 남산동2가', '중구 남산동3가', '중구 저동1가', '중구 충무로4가', '중구 충무로5가', '중구 인현동2가', '중구 예관동', '중구 묵정동', '중구 필동1가', '중구 필동2가', '중구 필동3가', '중구 남학동', '중구 주자동', '예장동', '중구 장충동1가', '중구 장충동2가', '중구 광희동1가', '중구 광희동2가', '중구 쌍림동', '중구 을지로6가', '중구 을지로7가', '중구 을지로4가', '중구 을지로5가', '중구 주교동', '중구 방산동', '중구 오장동', '중구 을지로3가', '중구 입정동', '중구 산림동', '중구 충무로3가', '중구 초동', '중구 인현동1가', '중구 저동2가', '중구 신당동', '중구 흥인동', '중구 무학동', '중구 황학동', '중구 서소문동', '중구 정동', '중구 순화동', '중구 의주로1가', '중구 충정로1가', '중구 중림동', '중구 의주로2가', '중구 만리동1가', '중구 만리동2가',
      '중랑구 면목동', '중랑구 상봉동', '중랑구 중화동', '중랑구 묵동', '중랑구 망우동', '중랑구 신내동'
      ]
    }
  },
  computed: {
    checkCurrentLocation() {
      return `${this.$store.getters.getCurrentLatitude} | ${this.$store.getters.getCurrentLongitude}`;
    },
    searchInputState() {
      return this.searchInput.length !== 1 ? true : false
    }
  },
  // Reverse Geocoding
  watch: {
    checkCurrentLocation() {
      this.onChangeMapAddress();
    }
  },
  methods: {
    searchKoreanOnly() {
      const pattern = /[a-z0-9]|[ [\]{}()<>?|`~!@#$%^&*-_+=,.;:"'\\]/g;
      this.searchInput = this.searchInput.replace(pattern, '');
    },
    onChangeMapAddress() {
      this.fillMapCenterAddress()
      .then((res) => {
        // geocoding 데이터가 없는 경우 예외 처리
        if (res.data.results.length > 0) {
          this.mapAddress = `${res.data.results[0].region.area1.name} ${res.data.results[0].region.area2.name} ${res.data.results[0].region.area3.name} ${res.data.results[0].region.area4.name}`;
        }
        console.log('주소 : ', this.mapAddress)
      })
      .catch((err) => {
        console.log(err);
      })
    },
    onChange(categoryInput) {
      this.$store.state.category = categoryInput;
    },
    onChange2(categoryInput2) {
      this.$store.state.category2 = categoryInput2;
    },
    // 검색 버튼 클릭
    searchClicked() {
      if (this.searchInput.length > 1) {
        let categoryInput = this.$store.state.category;
        let categoryInput2 = this.$store.state.category2;

        // 카테고리 선택에 따른 API 호출
        if (categoryInput === "지진" && categoryInput2 === "지역") {
          this.searchEarthquakesByArea()
          .then((res) => {
            // API 호출 결과로 얻은 대피소 리스트를 store에 반영
            this.$store.commit('setSearchShelterList', res.data)
          })
          .catch((err) => {
            console.log(err)
          })
        } else if (categoryInput === "해일" && categoryInput2 === "지역") {
          this.searchTsunamisByArea()
          .then((res) => {
            // API 호출 결과로 얻은 대피소 리스트를 store에 반영
            this.$store.commit('setSearchShelterList', res.data)
          })
          .catch((err) => {
            console.log(err)
          })
        } else if (categoryInput === "민방위" && categoryInput2 === "지역") {
          this.searchDefensesByArea()
          .then((res) => {
            // API 호출 결과로 얻은 대피소 리스트를 store에 반영
            this.$store.commit('setSearchShelterList', res.data)
          })
          .catch((err) => {
            console.log(err)
          })
        } else if (categoryInput === "지진" && categoryInput2 === "이름") {
          this.searchEarthquakesByName()
          .then((res) => {
            // API 호출 결과로 얻은 대피소 리스트를 store에 반영
            this.$store.commit('setSearchShelterList', res.data)
          })
          .catch((err) => {
            console.log(err)
          })
        } else if (categoryInput === "해일" && categoryInput2 === "이름") {
          this.searchTsunamisByName()
          .then((res) => {
            // API 호출 결과로 얻은 대피소 리스트를 store에 반영
            this.$store.commit('setSearchShelterList', res.data)
          })
          .catch((err) => {
            console.log(err)
          })
        } else if (categoryInput == "민방위" && categoryInput2 === "이름") {
          this.searchDefensesByName()
          .then((res) => {
            // API 호출 결과로 얻은 대피소 리스트를 store에 반영
            this.$store.commit('setSearchShelterList', res.data)
          })
          .catch((err) => {
            console.log(err)
          })
        }

        this.$store.state.isSearch = true;
      }
    },

    // 지진 대피소 리스트 검색 API 비동기 호출
    async searchEarthquakesByArea() {
      return await this.$store.dispatch('searchEarthquakesByArea', this.searchInput)               // 지명 검색
    },
    // 해일 대피소 리스트 검색 API 비동기 호출
    async searchTsunamisByArea() {
      return await this.$store.dispatch('searchTsunamisByArea', this.searchInput)                  // 지명 검색
    },
    // 민방위 대피소 리스트 검색 API 비동기 호출
    async searchDefensesByArea() {
      return await this.$store.dispatch('searchDefensesByArea', this.searchInput)                  // 지명 검색
    },
    // 지진 대피소 리스트 검색 API 비동기 호출
    async searchEarthquakesByName() {
      return await this.$store.dispatch('searchEarthquakesByName', this.searchInput)            // 이름 검색
    },
    // 해일 대피소 리스트 검색 API 비동기 호출
    async searchTsunamisByName() {
      return await this.$store.dispatch('searchTsunamisByName', this.searchInput)               // 이름 검색
    },
    // 민방위 대피소 리스트 검색 API 비동기 호출
    async searchDefensesByName() {
      return await this.$store.dispatch('searchDefensesByName', this.searchInput)               // 이름 검색
    },
    // Reverse Geocoding 네이버 API 비동기 호출
    async fillMapCenterAddress() {
      return await this.$store.dispatch('getReverseGeocoding', this.$store.state.currentLon + ',' +  this.$store.state.currentLat);
    }
  }
}
</script>

<style scoped>
/* .custom-select {
  height: 44px;
} */

.search-icon {
  height: 30px;
}

.form-control {
  border: 1px solid #6c757d;
}

p {
  margin-top: 5px;
}

.invalid-feedback {
  display: inline;
}
</style>