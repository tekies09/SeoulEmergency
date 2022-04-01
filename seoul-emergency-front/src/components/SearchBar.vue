<template>
  <div class="search">
    <b-input-group>
      <b-form-select
        v-model="$store.state.category"
        :options="$store.state.options"
        @change="onChange($store.state.category)"
      ></b-form-select>

      <b-form-input type="search" v-model="searchInput"> </b-form-input>

      <b-button variant="light" @click="searchClicked">
        <img src="../assets/search.svg" alt="Search Icon" class="search-icon" />
      </b-button>
    </b-input-group>
  </div>
</template>

<script>
// import MainMap from './MainMap.vue'

export default {
  data() {
    return {
      searchInput: '',
      categoryInput: null,
    };
  },
  methods: {
    onChange(categoryInput) {
      this.$store.state.category = categoryInput;
    },
    // 검색 버튼 클릭
    searchClicked() {
      let categoryInput = this.$store.state.category;
      // 카테고리 선택에 따른 API 호출
      if (categoryInput == '지진') {
        this.searchEarthquakes()
          .then((res) => {
            // API 호출 결과로 얻은 대피소 리스트를 store에 반영
            this.$store.commit('setSearchShelterList', res.data);
          })
          .catch((err) => {
            console.log(err);
          });
      } else if (categoryInput == '해일') {
        this.searchTsunamis()
          .then((res) => {
            // API 호출 결과로 얻은 대피소 리스트를 store에 반영
            this.$store.commit('setSearchShelterList', res.data);
          })
          .catch((err) => {
            console.log(err);
          });
      } else if (categoryInput == '민방위') {
        this.searchDefenses()
          .then((res) => {
            // API 호출 결과로 얻은 대피소 리스트를 store에 반영
            this.$store.commit('setSearchShelterList', res.data);
          })
          .catch((err) => {
            console.log(err);
          });
      }

      this.$store.state.isSearch = true;
    },
    // 지진 대피소 리스트 검색 API 비동기 호출
    async searchEarthquakes() {
      // return await this.$store.dispatch('searchEarthquakesByName', this.searchInput)            // 이름 검색
      return await this.$store.dispatch('searchEarthquakesByArea', this.searchInput); // 지명 검색
    },
    // 해일 대피소 리스트 검색 API 비동기 호출
    async searchTsunamis() {
      // return await this.$store.dispatch('searchTsunamisByName', this.searchInput)               // 이름 검색
      return await this.$store.dispatch('searchTsunamisByArea', this.searchInput); // 지명 검색
    },
    // 민방위 대피소 리스트 검색 API 비동기 호출
    async searchDefenses() {
      // return await this.$store.dispatch('searchDefensesByName', this.searchInput)               // 이름 검색
      return await this.$store.dispatch('searchDefensesByArea', this.searchInput); // 지명 검색
    },
  },
};
</script>

<style>
/* .custom-select {
  height: 44px;
} */

.search-icon {
  height: 30px;
}
</style>
