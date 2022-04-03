<template>
  <div class="map">
      <!-- 지도 컴포넌트 -->
      <!-- @zoom_changed="onChangeMap" -->
      <naver-maps
        :width="this.width"
        :height="this.height" 
        :mapOptions="this.mapOptions" 
        :initLayers="this.initLayers"
        @load="onLoad"
        @dragend="onChangeMap"
      >
        <!-- 마커 컴포넌트 동적 생성 -->
        <div
          v-for="shelter in this.searchShelterList"
          v-bind:key="shelter.seqNum">
          <naver-marker
            :lat=shelter.location.y
            :lng=shelter.location.x
            @click="showModal(shelter.headCount, shelter.seqNum)" 
            @load="onMarkerLoaded">
          </naver-marker>
        </div>
        <!-- 현재 위치 표시 원 -->
        <naver-circle
          v-if="this.currentLocationActive"
          :lat="this.mapOptions.lat" 
          :lng="this.mapOptions.lng" 
          :radius="20"
          :moreOptions="{
            strokeColor: '#fe6a6a',
            fillColor: '#fe6a6a',
            fillOpacity: 0.7,
          }"
        />
        <!-- 내 주변 대피소 찾기 버튼 -->
        <b-button
          variant="light"
          id="near-shelter-btn"
          @click="onClickNearBtn"
        >
          <svg width="36px" height="36px" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" id="find-icon">
            <circle cx="12" cy="12" r="4"/>
            <path d="M13 4.069V2h-2v2.069A8.01 8.01 0 0 0 4.069 11H2v2h2.069A8.008 8.008 0 0 0 11 19.931V22h2v-2.069A8.007 8.007 0 0 0 19.931 13H22v-2h-2.069A8.008 8.008 0 0 0 13 4.069zM12 18c-3.309 0-6-2.691-6-6s2.691-6 6-6 6 2.691 6 6-2.691 6-6 6z"/>
          </svg>
        </b-button>
      </naver-maps>

      <!-- 모달 창 (추후 리팩토링 필요) -->
      <b-modal ref="my-modal" hide-footer title="자세히보기">
      <!-- type 비어잇으면 지금은 지진정보임 -->
      <div v-if="this.searchShelterDetail.earthquakeDetail">
        <li>유형 : 지진/해일 대피소</li>
        <li>이름 : {{ searchShelterDetail.earthquakeDetail.name }}</li>
        <li>주소 : {{ searchShelterDetail.earthquakeDetail.address }}</li>
        <li>면적 : {{ searchShelterDetail.earthquakeDetail.equiArea }}㎡</li>

        <div class="map-link">
          <b-button variant="success" :href="searchNaverMapURL" target="_blank" rel="noopener noreferrer">네이버 지도</b-button>
          <b-button variant="warning" :href="searchKakaoMapURL" target="_blank" rel="noopener noreferrer">카카오 지도</b-button>
      </div>
      </div>

      <div v-if="this.searchShelterDetail.defenseDetail">
        <li>유형 : 민방위 대피소</li>

        <li>이름 : {{ searchShelterDetail.defenseDetail.name }}</li>
        <li>주소 : {{ searchShelterDetail.defenseDetail.address }}</li>
        <li>면적 : {{ searchShelterDetail.defenseDetail.equiArea }}㎡</li>
        <li>수용인원 : {{ searchShelterDetail.defenseDetail.headCount }}</li>
        <li>
          1인당 수용할수 있는 면적 :
          {{
            calparea(
              searchShelterDetail.defenseDetail.equiArea,
              searchShelterDetail.defenseDetail.headCount
            )
          }}㎡
        </li>
        <li>활용목적 : {{ searchShelterDetail.defenseDetail.type }}</li>
        <li>개방여부 : {{ searchShelterDetail.defenseDetail.isOpen }}</li>
        <li>데이터 갱신일자 : {{ searchShelterDetail.defenseDetail.date }}</li>

        <div class="map-link">
          <b-button variant="success" :href="searchNaverMapURL" target="_blank" rel="noopener noreferrer">네이버 지도</b-button>
          <b-button variant="warning" :href="searchKakaoMapURL" target="_blank" rel="noopener noreferrer">카카오 지도</b-button>
      </div>
      </div>
      <!-- type 비어잇지않으면 지금은 민방위정보임 -->
    </b-modal>
  </div>
</template>

<script>
export default {
  name: 'MainMap',
  data() {
    return {
      // 지도를 위한 정보들
      width: window.innerWidth*0.95,
      height: window.innerHeight*0.68,
      info: false,
      marker: null,
      currentLocationActive: false,
      count: 1,
      map: null,
      isCTT: false,
      initLayers: ['BACKGROUND', 'BACKGROUND_DETAIL', 'POI_KOREAN', 'TRANSIT', 'ENGLISH', 'CHINESE', 'JAPANESE'],
      naverMapURL: "https://map.naver.com",
      kakaoMapURL: "https://map.kakao.com"
    }
  },
  computed: {
    // store에 저장된 검색으로 얻은 대피소 리스트 변화를 감지하기 위해 computed로 관리
    searchShelterList() {
      return this.$store.state.searchShelterList
    },
    // 현위치, 마커 변화를 감지하여 지도 옵션에 반영하기 위해 computed로 관리
    mapOptions() {
      return {
        lat: 37.5666805,             // 초기 중심점 : 서울 시청
        lng: 126.9784147,
        zoom: 14,
        zoomControl: true,
        zoomControlOptions: {position: 'TOP_RIGHT'},
        mapTypeControl: true,
      }
    },
    // 모달용 computed
    searchShelterDetail() {
      return this.$store.state.shelterDetail;
    },
    searchNaverMapURL() {
      this.setNaverMapURL();
      return this.naverMapURL;
    },
    searchKakaoMapURL() {
      this.setKakaoMapURL();
      return this.kakaoMapURL;
    }
  },
  watch: {
    // store의 검색 대피소 리스트에 변화가 생기면 지도 중심점 재지정(값은 변하는데 지도 재랜더링이 안됩니다 ㅜㅜ)
    searchShelterList() {
      if (this.$store.state.isSearch) {
        this.reFocusMap();
      }
    }
  },
  methods: {
    // 모달 용 메서드
    // ------------------------------------------------------------------
    setNaverMapURL() {
      if(this.searchShelterDetail.earthquakeDetail) {
        this.checkNaverMobileType(this.searchShelterDetail.earthquakeDetail.name);
      } else if(this.searchShelterDetail.defenseDetail) {
        this.checkNaverMobileType(this.searchShelterDetail.defenseDetail.name);
      }
      return this.naverMapURL;
    },
    setKakaoMapURL() {
      if(this.searchShelterDetail.earthquakeDetail) {
        this.checkKakaoMobileType(this.searchShelterDetail.earthquakeDetail.name);
      } else if(this.searchShelterDetail.defenseDetail) {
        this.checkKakaoMobileType(this.searchShelterDetail.defenseDetail.name);
      }
      return this.kakaoMapURL;
    },
    checkNaverMobileType(queryString) {
      const userAgent = navigator.userAgent.toLowerCase();
      if(userAgent.indexOf('android') > -1) {
        this.naverMapURL = `intent://search?query=${queryString}&appname=j6a403.p.ssafy.io#Intent;scheme=nmap;action=android.intent.action.VIEW;category=android.intent.category.BROWSABLE;package=com.nhn.android.nmap;end`;
      } else if (userAgent.indexOf('iphone') > -1 || userAgent.indexOf('ipad') > -1) {
        this.naverMapURL = `nmap://search?query=${queryString}&appname=j6a403.p.ssafy.io`;
        this.naverMapURL = this.iosNaverMap(this.naverMapURL);
      } else {
        this.naverMapURL = `https://map.naver.com/search/${queryString}`;
      }
    },
    checkKakaoMobileType(queryString) {
      const userAgent = navigator.userAgent.toLowerCase();
      if(userAgent.indexOf('android') > -1) {
        this.kakaoMapURL = `intent://search?q=${queryString}#Intent;scheme=kakaomap;action=android.intent.action.VIEW;category=android.intent.category.BROWSABLE;package=net.daum.android.map;end`;
      } else if (userAgent.indexOf('iphone') > -1 || userAgent.indexOf('ipad') > -1) {
        this.kakaoMapURL = `kakaomap://search?q=${queryString}`;
        this.kakaoMapURL = this.iosKakaoMap(this.kakaoMapURL);
      } else {
        this.kakaoMapURL = `https://map.kakao.com/link/search/${queryString}`;
      }
    },
    iosNaverMap(URL) {
      let clickedAt = +new Date();
      let returnURL = URL;

      setTimeout(() => {
        if(+new Date() - clickedAt < 2000) {
          returnURL = 'http://itunes.apple.com/app/id311867728?mt=8';
        }
      }, 1500);

      return returnURL;
    },
    iosKakaoMap(URL) {
      let clickedAt = +new Date();
      let returnURL = URL;

      setTimeout(() => {
        if(+new Date() - clickedAt < 2000) {
          returnURL = 'https://itunes.apple.com/app/id304608425?mt=8';
        }
      }, 1500);

      return returnURL;
    },
    calparea(area, headcount) {
      return Math.round((area / headcount) * 100) / 100;
    },
    showModal(type, seqNum) {
      // 모달오픈
      this.$refs["my-modal"].show();

      if (type == null) {
        this.DetailEarthquakes(seqNum)
          .then((res) => {
            // API 호출 결과로 얻은 대피소 정보를 store에 반영
            this.$store.commit("setDetailShelter", res.data);
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        this.DetailDefenses(seqNum)
          .then((res) => {
            // API 호출 결과로 얻은 대피소 정보를 store에 반영
            this.$store.commit("setDetailShelter", res.data);
          })
          .catch((err) => {
            console.log(err);
          });
      }

      console.log(type + " : " + seqNum);
    },
    async DetailEarthquakes(seqNum) {
      // return await this.$store.dispatch('searchEarthquakesByName', this.searchInput)            // 이름 검색
      return await this.$store.dispatch("searchEarthquakesBySeq", seqNum); // 지명 검색
    },
    async DetailDefenses(seqNum) {
      // return await this.$store.dispatch('searchEarthquakesByName', this.searchInput)            // 이름 검색
      return await this.$store.dispatch("searchDefensesBySeq", seqNum); // 지명 검색
    },
    // ------------------------------------------------------------------

    // 지도 로딩
    onLoad(map) {
      this.map = map;
      this.geofind();
    },
    // 마커 로딩
    onMarkerLoaded({marker}) {
      this.marker = marker;
      this.marker.setClickable(true);
    },
    // 마커 클릭
    onMarkerClicked(event) {
      console.log(event)
      this.info = !this.info;
    },
    // 브라우저로부터 현위치 받기
    geofind() {
      let textContent = ''
      if(!("geolocation" in navigator)) {
        textContent = 'Geolocation is not available.';
        return;
      }
      textContent = 'Locating...'
      
      // 현위치 받기 성공했으면 지도 옵션의 중심점 변경(값은 변하는데 지도 재랜더링이 안됩니다 ㅜㅜ)
      navigator.geolocation.getCurrentPosition(pos => {
        this.updateMapCenter(pos.coords.latitude, pos.coords.longitude);

        // 새로운 지도 중심점에 맞춰 지도 이동
        this.map.setCenter(pos.coords.latitude, pos.coords.longitude);

        // 현재 위치를 지도에 표시
        this.currentLocationActive = true;

        // 현재 위치 기준 대피소 목록 조회
        this.findNearestShelters();
      }, err => {
        textContent = err.message;
        console.log(textContent)
      })
    },
    // 마커 변화 시 지도 중심점 변화(값은 변하는데 지도 재랜더링 외않되;)
    reFocusMap() {
      if (this.searchShelterList != null || this.searchShelterList.length > 0) {
        console.log("Set map scope")

        this.setMapScope();
      }
    },
    // 현재 위치에서부터 가까운 대피소 10개를 찾는다.
    findNearestShelters() {
      let categoryInput = this.$store.state.category;
      let location = {
        longitude: this.mapOptions.lng,
        latitude: this.mapOptions.lat,
      }

      if (categoryInput === "지진") {
        this.searchEarthquakeList(location)
          .then((res) => {
            console.log(res.data)
            this.$store.commit('setSearchShelterList', res.data)
          })
          .catch((err) => {
            console.log(err)
          })
      } else if (categoryInput === "해일") {
        this.searchTsunamiList(location)
          .then((res) => {
            this.$store.commit('setSearchShelterList', res.data)
          })
          .catch((err) => {
            console.log(err)
          })
      } else if (categoryInput === "민방위") {
        this.searchDefenseList(location)
          .then((res) => {
            this.$store.commit('setSearchShelterList', res.data)
          })
          .catch((err) => {
            console.log(err)
          })
      }

      this.$store.state.isSearch = false;
    },

    // 지도 드래그 이벤트 or 확대/축소 스크롤 이벤트 발생
    onChangeMap() {
      // 지도 중심 위치 가져오기
      let centerPoint = this.map.getBounds();

      let newLat = (centerPoint._sw._lat + centerPoint._ne._lat) / 2;
      let newLng = (centerPoint._sw._lng + centerPoint._ne._lng) / 2;

      console.log(`위도 : ${newLat} | 경도 : ${newLng}`)

      // 지도 위치 업데이트
      this.updateMapCenter(newLat, newLng);

      // 현재 지도 중심을 기준으로 대피소 검색
      this.findNearestShelters()
    },

    // 지도의 중심 위치를 갱신한다.
    updateMapCenter(lat, lng) {
      this.mapOptions.lat = lat;
      this.mapOptions.lng = lng;
      this.$store.commit('SET_LOCATION_LAT', lat);
      this.$store.commit('SET_LOCATION_LON', lng);
    },

    // '내 주변 대피소 찾기' 버튼 클릭
    onClickNearBtn() {
      // zoom 조정
      this.map.setZoom(14);

      this.geofind();
    },

    // 대피소 리스트에 맞게 지도 범위를 조정한다.
    setMapScope() {
      let minLng = this.searchShelterList[0].location.coordinates[0];
      let maxLng = this.searchShelterList[0].location.coordinates[0];
      let minLat = this.searchShelterList[0].location.coordinates[1];
      let maxLat = this.searchShelterList[0].location.coordinates[1];

      this.searchShelterList.forEach(element => {
        let lng = element.location.coordinates[0];
        let lat = element.location.coordinates[1];

        minLng = Math.min(minLng, lng);
        maxLng = Math.max(maxLng, lng);
        minLat = Math.min(minLat, lat);
        maxLat = Math.max(maxLat, lat);
      });

      console.log(`Scope : (${minLat}, ${minLng}) ~ (${maxLat}, ${maxLng})`);

      let newBound = window.naver.maps.LatLngBounds(
        new window.naver.maps.LatLng(minLat, minLng),
        new window.naver.maps.LatLng(maxLat, maxLng)
      );

      this.map.fitBounds(newBound, {
        margin: 10
      });
    },

    mapResize() {
      const size = new window.naver.maps.Size(window.innerWidth*0.95, window.innerHeight*0.68);
      this.map.setSize(size);
    },

    async searchEarthquakeList(location) {
      return await this.$store.dispatch('searchNearestEarthquakes', location)
    },

    async searchTsunamiList(location) {
      return await this.$store.dispatch('searchNearestTsunamis', location)
    },

    async searchDefenseList(location) {
      return await this.$store.dispatch('searchNearestDefenses', location)
    }
  },
  mounted() {
    window.addEventListener('resize', this.mapResize);
  },
  beforeDestroy() {
    window.addEventListener('resize', this.mapResize);
  },
}
</script>

<style>
#near-shelter-btn {
  position: absolute;
  bottom: 2rem;
  right: 1.4rem;
  padding: 4px;
  z-index: 1000;
  background-color: #fe6a6a;
  opacity: 1;
  transition: 50ms linear; 
}

#near-shelter-btn:hover {
  transform: scale(1.1);
  opacity: 1;
  background-color: #ff4040;
}

#find-icon {
  fill: white;
}

.map-link {
  margin-top: 5px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}
</style>