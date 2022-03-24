import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    category: "",
    options: [
      { value: "", text: "대피소 종류 선택"},
      { value: "지진", text: "지진"},
      { value: "해일", text: "해일"},
      { value: "민방위", text: "민방위"}
    ]
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
