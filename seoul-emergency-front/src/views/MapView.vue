<template>
  <div class="map">
    <MainHeader />

    <SearchBar />

    <!-- 지도 컴포넌트 -->
    <div class="map-component">
      <MainMap />
    </div>
    <!-- 대피소 리스트 사이드바 컴포넌트 -->
    <div class="list-component">
      <EvacList />
    </div>
  </div>
</template>

<script>
import MainHeader from "@/components/MainHeader.vue";
import SearchBar from "@/components/SearchBar.vue";
import MainMap from "@/components/MainMap.vue";
import EvacList from "@/components/EvacList.vue";

export default {
  name: "HomeView",
  components: {
    MainHeader,
    SearchBar,
    MainMap,
    EvacList,
  },
};
</script>

<style scoped>
.search {
  margin-top: 5px;
}

.main-map {
  margin-top: 5px;
}

.map-component {
  width: 95vw;
  height: 68vh;
  margin: 10px 0 0 10px;
}
</style>
