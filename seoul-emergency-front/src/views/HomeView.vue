<template>
  <div class="home">
    <div class="logo">
      <img src="../assets/logo.png" alt="Seoul Emergency">
      <p>서울 이머전시</p>
    </div>
    <p class="help">대피소 종류를 선택하세요</p>
    <div class="categories">
      <b-button @click="clickCategoryBtn('지진')" variant="outline-dark" size="lg" to="/map">지진</b-button>
      <b-button @click="clickCategoryBtn('해일')" variant="outline-dark" size="lg" to="/map">해일</b-button>
      <b-button @click="clickCategoryBtn('민방위')" variant="outline-dark" size="lg" to="/map">민방위</b-button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    clickCategoryBtn(category) {
      this.$store.commit('SET_CATEGORY', category);
    }
  }
}
</script>

<style scoped>
.logo {
  margin-top: 25vh;
}

.logo img {
  min-width: 50px;
  min-height: 50px;
  width: 20vmin;
  height: 20vmin;
}

.logo p {
  font-size: 2.5rem;
  margin-top: 3vh;
  color: #fe6a6a;
  font-weight: 300;
}

.help {
  margin-top: 5vh;
  font-size: 1.4rem;
  font-weight: 300;
  color: #495057;
}

.categories {
  margin-top: 2vh;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}

.categories a {
  font-size: 2rem;
  margin-left: 5vw;
  margin-right: 5vw;
  box-shadow: 1px 1px 3px 0px grey;
}
</style>