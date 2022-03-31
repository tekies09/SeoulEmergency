<template>
  <div class="news-list">
    <b-tabs>
      <b-tab title="재난 뉴스">
        <ul class="list">
          <li
            class="news"
            v-for="news in this.getNewsListByPage"
            v-bind:key="news.id"
          >
            <div class="news-left">
              <img
                :src="news.newsImage == null
                  ? require(`@/assets/logo.png`) : news.newsImage"
                class="news-image"
              />
            </div>
            <div class="news-body">
              <a v-bind:href="news.newsLink">
                <p class="news-heading">
                  {{ news.newsTitle }}
                </p>
                <p class="news-date">
                  {{ news.newsDate == null ? new Date() : news.newsDate }}
                </p>
                <p class="news-content">
                  {{ news.newsContent.substring(0, 50) }}….
                </p>
              </a>
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
    },
  },
};
</script>

<style scoped>
.news-list {
  min-height: 540px;
}
.news {
  border-bottom: 1px solid lightgray;
  padding: 10px 0px;
  list-style-type: none;
  display: flex;
  font-size: 14px;
}

.news-heading {
  font-size: 16px;
  margin-bottom: 10px;
}

.news-image {
  width: 6rem;
}

.news-date {
  margin-bottom: 5px;
}

.news-body {
  width: 100%;
  margin: 0 auto;
  text-align: center;
}

a:link {
  text-decoration: none;
  color: #fe6a6a;
}
a:visited {
  color: #8c59b9;
}
</style>
