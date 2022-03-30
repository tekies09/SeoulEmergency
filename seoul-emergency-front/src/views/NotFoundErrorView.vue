<template>
  <div class="outer-box">
    <div class="container">
      <div class="logo">
        <a href="/map"><img src="../assets/logo.png" alt="Seoul Emergency" class="main-img"></a>
      </div>
      <h2 class="mt-6">Seoul Emergency로 대피하기</h2>
    </div>
  </div>
</template>

<script>

</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}

.main-img {
  height: 20vh;
  margin-bottom: 2.5vh;
  transition: 500ms linear;
}

.main-img:hover {
  transform: scale(0.5);
}
</style>