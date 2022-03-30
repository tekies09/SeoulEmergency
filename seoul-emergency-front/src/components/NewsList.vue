<template>
  <div class="news-list">
    <b-tabs>
      <b-tab title="재난 뉴스">
        <ul class="list">
          <li class="news" v-for="news in this.getNewsListByPage"
          v-bind:key="news.id">
            <div class="news-left">
              <a href="#" target="_blank">
                <img src="https://via.placeholder.com/120x140?text=News" />
              </a>
            </div>
            <div class="news-body">
              <h4 class="news-heading">
                <a v-bind:href="news.newsLink">
                  {{news.newsTitle}}
                </a>
              </h4>
              <h5>{{news.newsDate == null ? new Date() : news.newsDate}}</h5>
              <p>{{news.newsContent.substring(0, 70)}}....</p>
            </div>
          </li>
        </ul>
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
export default {
  name: "NewsList",
  computed: {
    getNewsListByPage() {
      return this.$store.state.newsList;
    }
  },
};
</script>

<style scoped>
.news {
  border-bottom: 1px solid lightgray;
  padding-top: 10px;
  list-style-type: none;
  display: flex;
}
</style>
