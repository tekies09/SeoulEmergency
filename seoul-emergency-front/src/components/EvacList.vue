<template>
  <div class="list">
      <!-- 대피소 리스트 사이드바 컴포넌트 -->
      <b-sidebar id="sidebar-right" right>
        <!-- 사이드바 내부 대피소 리스트 항목 동적 생성 -->
        <b-list-group>
          <div
            v-for="shelter in this.searchShelterList"
            v-bind:key="shelter.seqNum">
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
                  <li>면적 : {{ shelter.equiArea }}</li>
                  <li v-if="shelter.headCount">수용인원 : {{ shelter.headCount }}</li>
                  <li v-if="shelter.type">활용목적 : {{ shelter.type }}</li>
                  <li v-if="shelter.isOpen">개방여부 : {{ shelter.isOpen }}</li>
                  <li v-if="shelter.date">데이터 갱신일자 : {{ shelter.date }}</li>
                </ul>
              </div>
            </b-list-group-item>
          </div>
        </b-list-group>
      </b-sidebar>
  </div>
</template>

<script>
export default {
  name: 'EvacList',
  computed: {
    // store에 저장된 검색으로 얻은 대피소 리스트 변화를 감지하기 위해 computed로 관리
    searchShelterList() {
      return this.$store.state.searchShelterList
    },
  },
}
</script>

<style>
#sidebar-right {
  margin: 180px auto 0;
  height: 71vh;
  width: 27vw;
  text-align: left;
}
</style>