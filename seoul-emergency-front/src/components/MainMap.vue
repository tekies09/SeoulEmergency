<template>
  <div class="map">
      <!-- 지도 컴포넌트 -->
      <naver-maps
        :width="this.width"
        :height="this.height" 
        :mapOptions="this.mapOptions" 
        :initLayers="this.initLayers"
        @load="onLoad">
        <!-- 마커 컴포넌트 동적 생성 -->
        <div
          v-for="shelter in this.searchShelterList"
          v-bind:key="shelter.seqNum">
          <naver-marker
            :lat=shelter.location.y
            :lng=shelter.location.x
            @click="onMarkerClicked" 
            @load="onMarkerLoaded">
          </naver-marker>
        </div>
      </naver-maps>
  </div>
</template>

<script>
export default {
  name: 'MainMap',
  data() {
    return {
      // 지도를 위한 정보들
      width: 1200,
      height: 600,
      info: false,
      marker: null,
      count: 1,
      map: null,
      isCTT: false,
      initLayers: ['BACKGROUND', 'BACKGROUND_DETAIL', 'POI_KOREAN', 'TRANSIT', 'ENGLISH', 'CHINESE', 'JAPANESE']
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
        zoom: 12,
        zoomControl: true,
        zoomControlOptions: {position: 'TOP_RIGHT'},
        mapTypeControl: true,
      }
    },
  },
  watch:{
    // store의 검색 대피소 리스트에 변화가 생기면 지도 중심점 재지정(값은 변하는데 지도 재랜더링이 안됩니다 ㅜㅜ)
    searchShelterList() {
      this.reFocusMap()
    }
  },
  methods: {
    // 지도 로딩
    onLoad(map) {
      this.map = map;
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
      var textContent = ''
      if(!("geolocation" in navigator)) {
        textContent = 'Geolocation is not available.';
        return;
      }
      textContent = 'Locating...'
      
      // 현위치 받기 성공했으면 지도 옵션의 중심점 변경(값은 변하는데 지도 재랜더링이 안됩니다 ㅜㅜ)
      navigator.geolocation.getCurrentPosition(pos => {
        this.mapOptions.lat = pos.coords.latitude;
        this.mapOptions.lng = pos.coords.longitude;
        console.log(this.mapOptions)
      }, err => {
        textContent = err.message;
        console.log(textContent)
      })
    },
    // 마커 변화 시 지도 중심점 변화(값은 변하는데 지도 재랜더링 외않되;)
    reFocusMap() {
      if (this.searchShelterList != null || this.searchShelterList.length > 0) {
        console.log("refocus")
        this.mapOptions.lat = this.searchShelterList[0].location.y;
        this.mapOptions.lng = this.searchShelterList[0].location.x;
        console.log(this.mapOptions)
      }
    }
  },
  // 컴포넌트 마운트되면 브라우저로부터 현위치 받기
  mounted() {
    this.geofind()
  }
}
</script>

<style>

</style>