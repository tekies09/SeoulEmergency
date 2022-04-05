<template>
  <div class="map">
    <MainHeader />

    <SearchBar />

    <div class="tags">
      <b-button v-b-toggle.sidebar-right.sidebar-no-header id="show-sidebar"
        >대피소 목록 보기</b-button
      >
    </div>

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

.tags {
  margin-top: 5px;
  margin-left: 5px;
  position: absolute;
  left: 4%;
  top: 160px;
  z-index: 1000;
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
  margin: 10px 0 0 10px;
}

#show-sidebar {
  margin-top: 5px;
  border-color: white;
  background-color: #fe6a6a;
}

#show-sidebar:hover {
  background-color: #ff4040;
}
</style>
