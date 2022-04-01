<template>
  <div class="about">
    <MainHeader />
    <b-container>
      <b-row>
        <b-col>
          <b-row>
            <b-col style="width: 100%">
              <NewsList />
              <b-pagination
                v-model="currentPage"
                :total-rows="this.$store.state.newsListCount"
                :per-page="4"
                align="center"
              ></b-pagination>
            </b-col>
          </b-row>
        </b-col>
        <b-col><WordCloud /></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
// @ is an alias to /src
import MainHeader from '@/components/MainHeader.vue';
import NewsList from '@/components/NewsList.vue';
import WordCloud from '@/components/WordCloud.vue';

export default {
  name: 'HomeView',
  components: {
    MainHeader,
    NewsList,
    WordCloud,
  },
  methods: {
    async getNewsListByPage(page, limit) {
      return await this.$store.dispatch('getNewsListByPage', { page, limit });
    },
    async getNewsListCount() {
      return await this.$store.dispatch('getAllNewsCount');
    },
    pageClick: function (button, page) {
      this.currentPage = page;
      this.getNoticeListByPage(page);
    },
    async getWordsList() {
      return await this.$store.dispatch('getWordsList');
    },
  },
  watch: {
    // ! currentPage 변수가 바뀌면 뉴스 리스트를 다시 불러오는 식으로 페이지네이션 해결!
    currentPage() {
      this.getNewsListByPage(this.currentPage - 1, 4)
        .then((res) => {
          // console.log("뉴스리스트",res.data.content);
          this.$store.commit('SET_NEWS_LIST', res.data.content);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    this.getNewsListByPage(this.currentPage - 1, 4)
      .then((res) => {
        // console.log("뉴스리스트",res.data.content);
        this.$store.commit('SET_NEWS_LIST', res.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
    this.getNewsListCount().then((res) => {
      // console.log(res.data);
      this.$store.commit('SET_NEWS_LIST_COUNT', res.data);
    });

    this.getWordsList()
      .then((res) => {
        // console.log(res.data);
        this.$store.commit('SET_WORDS_LIST', res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  data() {
    return {
      currentPage: 1,
    };
  },
};
</script>

<style scoped>
.container {
  max-width: 98vw;
}
</style>
