import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import createPersistedState from 'vuex-persistedstate';


Vue.use(Vuex)

axios.defaults.headers['Content-Type'] = 'application/json'
const backAxios = axios.create({
  baseURL: '/api'
})

export default new Vuex.Store({
  state: {
    category: "",
    category2: "",
    options: [
      { value: "", text: "대피소 종류 선택"},
      { value: "지진", text: "지진"},
      { value: "해일", text: "해일"},
      { value: "민방위", text: "민방위"}
    ],
    options2: [
      { value: "", text: "검색 옵션"},
      { value: "지역", text: "지역"},
      { value: "이름", text: "이름"}
    ],
    searchShelterList: [],      // 검색 API로 얻은 대피소 리스트
    newsList: [],   // 뉴스 API로 얻은 재난 뉴스 리스트
    isSearch: false,
    shelterDetail:[],
    currentLat: 37.5666805,
    currentLon: 126.9784147
  },
  getters: {
    // 대피소 리스트 getter
    getSearchShelterList(state) {
      return state.searchShelterList
    },
    // 재난 뉴스 리스트 getter
    getDisasterNewsList(state) {
      return state.newsList;
    },
    getCurrentLatitude(state) {
      return state.currentLat;
    },
    getCurrentLongitude(state) {
      return state.currentLon;
    }
  },
  mutations: {
    // 대피소 리스트 setter
    setSearchShelterList(state, searchShelterList) {
      state.searchShelterList = []
      state.searchShelterList = searchShelterList
    },
    // 대피소 분류 setter
    SET_CATEGORY(state, category) {
      state.category = category;
    },
    setDetailShelter(state, shelterDetail) {
      state.shelterDetail = []
      state.shelterDetail = shelterDetail
    },

    SET_NEWS_LIST(state, newsList) {
      state.newsList = newsList
    },
    // 대피소 검색 종류 setter
    SET_CATEGORY2(state, category2) {
      state.category2 = category2;
    },
    // 현재 좌표 setter
    SET_LOCATION_LAT(state, latitude) {
      state.currentLat = latitude;
    },
    SET_LOCATION_LON(state, longitude) {
      state.currentLon = longitude;
    }
  },
  actions: {
    // 지진 옥외 대피소 지역명 검색 axios
    searchEarthquakesByArea({state}, payload) {
      console.log('searchEarthquakesByArea', state, payload);
      const url = `/api/shelters/areasearch/earthquakes/${payload}`
      return backAxios.get(url)
    },
    // 지진 옥외 대피소 이름 검색 axios
    searchEarthquakesByName({state}, payload) {
      console.log('searchEarthquakesByName', state, payload);
      const url = `/api/shelters/namesearch/earthquakes/${payload}`
      return backAxios.get(url)
    },
    // 해일 대피소 지역명 검색 axios
    searchTsunamisByArea({state}, payload) {
      console.log('searchTsunamisByArea', state, payload);
      const url = `/api/shelters/areasearch/tsunamis/${payload}`
      return backAxios.get(url)
    },
    // 해일 대피소 이름 검색 axios
    searchTsunamisByName({state}, payload) {
      console.log('searchTsunamisByName', state, payload);
      const url = `/api/shelters/namesearch/tsunamis/${payload}`
      return backAxios.get(url)
    },
    // 민방위 대피소 지역명 검색 axios
    searchDefensesByArea({state}, payload) {
      console.log('searchDefensesByArea', state, payload);
      const url = `/api/shelters/areasearch/defenses/${payload}`
      return backAxios.get(url)
    },
    // 민방위 대피소 이름 검색 axios
    searchDefensesByName({state}, payload) {
      console.log('searchDefensesByName', state, payload);
      const url = `/api/shelters/namesearch/defenses/${payload}`
      return backAxios.get(url)
    },

    // 거리순 지진 옥외 대피소 검색 axios
    searchNearestEarthquakes({state}, location) {
      console.log('거리순 지진 옥외 대피소 검색', state);
      const url = '/api/shelters/list/earthquakes';
      return backAxios.get(url, {
        params: location,
      });
    },
    // 거리순 해일 대피소 검색 axios
    searchNearestTsunamis({state}, location) {
      console.log('거리순 해일 대피소 검색', state);
      const url = '/api/shelters/list/earthquakes';
      return backAxios.get(url, {
        params: location,
      });
    },
    // 거리순 민방위 대피소 검색 axios
    searchNearestDefenses({state}, location) {
      console.log('거리순 민방위 대피소 검색', state);
      const url = '/api/shelters/list/defenses';
      return backAxios.get(url, {
        params: location,
      });
    },

    // SeqNum으로 지진 대피소검색
    searchEarthquakesBySeq({state}, payload) {
      console.log('searchEarthquakesBySeq', state, payload);
      const url = `/api/shelters/detail/earthquakes/${payload}`
      return backAxios.get(url)
    },
      // SeqNum으로 민방위 대피소검색
    searchDefensesBySeq({state}, payload) {
      console.log('searchDefensesBySeq', state, payload);
      const url = `/api/shelters/detail/defenses/${payload}`
      return backAxios.get(url)
    },

    // 뉴스 리스트 페이지로 불러오기
    getNewsListByPage({ state }, page, limit) {
      console.log('뉴스 리스트 페이지 별 불러오기', state);
      const url = '/api/news/list/';
      return backAxios.get(url, {
        params: {
          page,
          limit
        }
      });
    },

    // 네이버 Reverse Geocoding API axios
    getReverseGeocoding({state}, location) {
      // 민감정보!!!
      // ------------------------------------------------------------
      const clientID = 'ttsrjtbjqw';
      const clientSecret= 'etV9ZLeuNvftyEOKgC88jzSndiuGNDnlTMdLpsWt';
      // ------------------------------------------------------------

      const url = 'https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc';

      const coordinates = location;

      console.log('네이버 Reverse Geocoding', state);
      return axios.get(url, {
        headers: {
          'X-NCP-APIGW-API-KEY-ID': clientID,
          'X-NCP-APIGW-API-KEY': clientSecret
        },
        params: {
          request: 'coordsToaddr',
          coords: coordinates,
          orders: 'roadaddr',
          output: 'json'
        }
      });
    }
  },
  modules: {
  },
  plugins: [
    createPersistedState(),
  ]
})
