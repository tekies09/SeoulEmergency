<template>
  <div class="about">
    <MainHeader />
    <b-container>
      <b-row>
        <b-col>
          <b-row>
            <b-col style="width: 100%; text-align: center">
              <NewsList />
              <b-pagination
                v-model="currentPage"
                :total-rows="this.$store.state.newsList.length"
                :per-page="perPage"
                aria-controls="my-table"
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
import MainHeader from "@/components/MainHeader.vue";
import NewsList from "@/components/NewsList.vue";
import WordCloud from "@/components/WordCloud.vue";

export default {
  name: "HomeView",
  components: {
    MainHeader,
    NewsList,
    WordCloud,
  },
  methods: {
    async getNewsListByPage(page, limit) {
      return await this.$store.dispatch("getNewsListByPage", {page, limit});
    },
  },
  computed: {},
  mounted() {
    this.getNewsListByPage(0, 4)
      .then((res) => {
        // console.log("뉴스리스트",res.data.content);
          this.$store.commit("SET_NEWS_LIST", res.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  data() {
    return {
      perPage: 3,
      currentPage: 1,
    }
  }
};
</script>

<style scoped>
.container {
  max-width: 98vw;
}
</style>
