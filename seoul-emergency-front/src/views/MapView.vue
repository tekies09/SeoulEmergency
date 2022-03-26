<template>
  <div class="map">
      <MainHeader/>

      <SearchBar/>

      <div class="tags">
        <b-button variant="light">#성북</b-button>
        <b-button variant="light">#강남</b-button>
        <b-button variant="light">#중구</b-button>

        <b-button v-b-toggle.sidebar-right>사이드바</b-button>
      </div>

      <!-- 지도 컴포넌트 -->
      <div class="map-component">
        <MainMap/>
        <!-- <img src="https://via.placeholder.com/900x800?text=Map+Place+Holder" alt="Map Place Holder" class="map-component"> -->
      </div>
      <!-- 대피소 리스트 사이드바 컴포넌트 -->
      <div class="list-component">
        <EvacList/>
      </div>
      
  </div>
</template>

<script>
import MainHeader from '@/components/MainHeader.vue'
import SearchBar from '@/components/SearchBar.vue'
import MainMap from '@/components/MainMap.vue'
import EvacList from '@/components/EvacList.vue'

export default {
  name: 'HomeView',
  components: {
    MainHeader,
    SearchBar,
    MainMap,
    EvacList
  }
}
</script>

<style scoped>
.search {
    margin-top: 5px;
}

.tags {
    margin-top: 5px;
    margin-left: 5px;
    display: flex;
}

.tags button {
    margin-left: 10px;
}

.main-map {
    margin-top: 5px;
}

.map-component {
    width: 95vw;
    height: 68vh;
    margin: 20px 0 0 20px;
}
</style>