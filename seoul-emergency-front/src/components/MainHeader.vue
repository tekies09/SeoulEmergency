<template>
  <div class="header">
    <b-navbar variant="faded" type="light">
      <b-navbar-brand to="/map">
        <img
          src="../assets/logo.png"
          class="d-inline-block align-top logo-img"
          alt="Seoul Emergency"
        />
      </b-navbar-brand>
      <b-navbar-nav>
        <b-nav-item
          v-if="this.$route.name !== 'map'"
          to="/map"
          class="header-button"
        >
          <img src="../assets/map.svg" alt="Map" class="map-img" />
          <span>지도</span>
        </b-nav-item>
        <b-nav-item
          v-if="this.$route.name !== 'news'"
          to="/news"
          class="header-button"
        >
          <img src="../assets/news.svg" alt="News" class="news-img" />
          <span>뉴스</span>
        </b-nav-item>
      </b-navbar-nav>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: "MainHeader",
};
</script>

<style scoped>
.logo-img {
  min-width: 40px;
  width: 5vmin;
  margin-left: 47.5vw;
  transition: 100ms linear;
}

.logo-img:hover {
  transform: scale(1.2);
}

.header-button {
  display: block;
  background-color: #fff;
  text-align: center;
  cursor: pointer;
  color: #333;
  margin-right: 8px;
  border-radius: 8px;
  transition: all 0.9s color 0.3s ease-out;
}
.header-button:hover {
  background-color: lightgrey;
}

.navbar-nav {
  margin-left: auto;
}

.navbar-nav span {
  position: relative;
  top: 0.5rem;
  margin-left: 5px;
  font-size: 1.2rem;
  color: #fe6a6a;
}

.map-img {
  min-height: 30px;
  height: 2vmin;
}

.news-img {
  min-height: 30px;
  height: 2vmin;
}
</style>
