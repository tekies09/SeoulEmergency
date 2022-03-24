<template>
  <div class="about">
    <MainHeader/>
    <b-container>
      <b-row>
        <b-col><NewsList/></b-col>
        <b-col><WordCloud/></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
// @ is an alias to /src
import MainHeader from '@/components/MainHeader.vue'
import NewsList from '@/components/NewsList.vue'
import WordCloud from '@/components/WordCloud.vue'

export default {
  name: 'HomeView',
  components: {
    MainHeader,
    NewsList,
    WordCloud
  }
}
</script>

<style scoped>
.container {
  max-width: 98vw;
}
</style>