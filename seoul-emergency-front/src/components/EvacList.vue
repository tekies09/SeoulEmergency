<template>
  <div class="list">
    <!-- 대피소 리스트 사이드바 컴포넌트 -->
    <b-sidebar v-if="$isMobile" id="sidebar-right" right class="desktop-sidebar">
      <!-- 사이드바 내부 대피소 리스트 항목 동적 생성 -->
      <b-list-group>
        <div
          v-for="shelter in this.searchShelterList"
          v-bind:key="shelter.seqNum"
          @click="showModal(shelter.headCount, shelter.seqNum)"
        >
          <b-list-group-item href="#">
            <!-- 대피소명, 시설 번호 -->
            <div class="d-flex justify-content-between">
              <h5 class="mb-1">{{ shelter.name }}</h5>
              <small class="text-muted">No. {{ shelter.seqNum }}</small>
            </div>
            <!-- 대피소 정보 -->
            <div class="mt-3">
              <ul class="text-left">
                <li>주소 : {{ shelter.address }}</li>
                <li>면적 : {{ shelter.equiArea }} ㎡</li>
                <!--
                  <li v-if="shelter.headCount">수용인원 : {{ shelter.headCount }}</li>
                  <li v-if="shelter.type">활용목적 : {{ shelter.type }}</li>
                  <li v-if="shelter.isOpen">개방여부 : {{ shelter.isOpen }}</li>
                  <li v-if="shelter.date">데이터 갱신일자 : {{ shelter.date }}</li>
                  !-->
              </ul>
            </div>
          </b-list-group-item>
        </div>
      </b-list-group>
    </b-sidebar>

    <b-sidebar v-else id="sidebar-right" right class="mobile-sidebar">
      <!-- 사이드바 내부 대피소 리스트 항목 동적 생성 -->
      <b-list-group>
        <div
          v-for="shelter in this.searchShelterList"
          v-bind:key="shelter.seqNum"
          @click="showModal(shelter.headCount, shelter.seqNum)"
        >
          <b-list-group-item href="#">
            <!-- 대피소명, 시설 번호 -->
            <div class="d-flex justify-content-between">
              <h5 class="mb-1">{{ shelter.name }}</h5>
              <small class="text-muted">No. {{ shelter.seqNum }}</small>
            </div>
            <!-- 대피소 정보 -->
            <div class="mt-3">
              <ul class="text-left">
                <li>주소 : {{ shelter.address }}</li>
                <li>면적 : {{ shelter.equiArea }} ㎡</li>
                <!--
                  <li v-if="shelter.headCount">수용인원 : {{ shelter.headCount }}</li>
                  <li v-if="shelter.type">활용목적 : {{ shelter.type }}</li>
                  <li v-if="shelter.isOpen">개방여부 : {{ shelter.isOpen }}</li>
                  <li v-if="shelter.date">데이터 갱신일자 : {{ shelter.date }}</li>
                  !-->
              </ul>
            </div>
          </b-list-group-item>
        </div>
      </b-list-group>
    </b-sidebar>

    <b-modal ref="my-modal" hide-footer title="자세히보기">
      <!-- type 비어잇으면 지금은 지진정보임 -->
      <div v-if="this.searchShelterDetail.earthquakeDetail">
        <li>유형 : 지진/해일 대피소</li>
        <li>이름 : {{ searchShelterDetail.earthquakeDetail.name }}</li>
        <li>주소 : {{ searchShelterDetail.earthquakeDetail.address }}</li>
        <li>면적 : {{ searchShelterDetail.earthquakeDetail.equiArea }}㎡</li>

        <div class="map-link">
          <b-button variant="success" :href="searchNaverMapURL" target="_blank" rel="noopener noreferrer">네이버 지도</b-button>
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
      </div>
      </div>
      <!-- type 비어잇지않으면 지금은 민방위정보임 -->
    </b-modal>
  </div>
</template>

<script>
export default {
  data() {
    return {
      naverMapURL: "https://map.naver.com"
    }
  },
  name: "EvacList",
  computed: {
    // store에 저장된 검색으로 얻은 대피소 리스트 변화를 감지하기 위해 computed로 관리
    searchShelterList() {
      return this.$store.state.searchShelterList;
    },
    searchShelterDetail() {
      return this.$store.state.shelterDetail;
    },
    searchNaverMapURL() {
      this.setNaverMapURL();
      return this.naverMapURL;
    }
  },
  methods: {
    setNaverMapURL() {
      if(this.searchShelterDetail.earthquakeDetail) {
        this.checkMobileType(this.searchShelterDetail.earthquakeDetail.name);
      } else if(this.searchShelterDetail.defenseDetail) {
        this.checkMobileType(this.searchShelterDetail.defenseDetail.name);
      }
      return this.naverMapURL;
    },
    checkMobileType(queryString) {
      const userAgent = navigator.userAgent.toLowerCase();
      if(userAgent.indexOf('android') > -1) {
        this.naverMapURL = `intent://search?query=${queryString}&appname=j6a403.p.ssafy.io#Intent;scheme=nmap;action=android.intent.action.VIEW;category=android.intent.category.BROWSABLE;package=com.nhn.android.nmap;end`;
      } else if (userAgent.indexOf('iphone') > -1) {
        this.naverMapURL = `nmap://search?query=${queryString}&appname=j6a403.p.ssafy.io`;
        this.naverMapURL = this.iosNaverMap(this.naverMapURL);
      } else {
        this.naverMapURL = `https://map.naver.com/search/${queryString}`;
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
  },
};
</script>

<style>
#sidebar-right {
  margin: 23vh auto 0;
  height: 71vh;
  text-align: left;
}

.desktop-sidebar {
  width: 30vw;
}

.mobile-sidebar {
  width: 95vw;
}
</style>
