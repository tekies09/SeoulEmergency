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
            <!-- <div class="news-left">
              <a href="#" target="_blank">
                <img src="https://via.placeholder.com/120x120?text=News" />
              </a>
            </div> -->
            <a v-bind:href="news.newsLink">
              <div class="news-body">
                <p class="news-heading">
                  {{ news.newsTitle }}
                </p>
                <p class="news-date">
                  {{ news.newsDate == null ? new Date() : news.newsDate }}
                </p>
                <p class="news-content">
                  {{ news.newsContent.substring(0, 50) }}….
                </p>
              </div>
            </a>
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
.news {
  border-bottom: 1px solid lightgray;
  padding-top: 10px;
  list-style-type: none;
  display: flex;
  font-size: 14px;
}

.news-heading {
  font-size: 16px;
  margin-bottom: 10px;
}

.news-date {
  margin-bottom: 5px;
}

.news-body {
  width: 100%;
}

a:link {
  text-decoration: none;
  color: #fe6a6a;
}
a:visited {
  color: #8c59b9;
}
</style>
