<template>
  <div class="header">
    <b-navbar variant="faded" type="light">
      <b-navbar-brand>
        <img src="../assets/logo.png" class="d-inline-block align-top logo-img" alt="Seoul Emergency">
      </b-navbar-brand>
      <b-navbar-nav>
        <b-nav-item v-if="this.$route.name !== 'map'" to="/map">
          <img src="../assets/map.svg" alt="Map" class="map-img">
          <span>지도</span>
        </b-nav-item>
        <b-nav-item v-if="this.$route.name !== 'news'" to="/news">
          <img src="../assets/news.svg" alt="News" class="news-img">
          <span>뉴스</span>
        </b-nav-item>
      </b-navbar-nav>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: 'MainHeader'
}
</script>

<style scoped>
.logo-img {
  min-width: 40px;
  width: 5vmin;
  margin-left: 47.5vw;
}

.navbar-nav {
  margin-left: auto;
}

.navbar-nav:hover {
  box-shadow: 1px 1px 3px 0px grey;
}

.navbar-nav span {
  position: relative;
  top: 0.5rem;
  margin-left: 5px;
  font-size: 1.5rem;
  color: #fe6a6a;
}

.map-img {
  min-height: 35px;
  height: 3vmin;
}

.news-img {
  min-height: 35px;
  height: 3vmin;
}
</style>