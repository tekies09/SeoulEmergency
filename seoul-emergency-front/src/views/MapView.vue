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

      <div class="main-map">
        <img src="https://via.placeholder.com/900x800?text=Map+Place+Holder" alt="Map Place Holder" class="map-component">
      </div>

      <EvacList/>
  </div>
</template>

<script>
import MainHeader from '@/components/MainHeader.vue'
import SearchBar from '@/components/SearchBar.vue'
import EvacList from '@/components/EvacList.vue'

export default {
  name: 'HomeView',
  components: {
    MainHeader,
    SearchBar,
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
}
</style>