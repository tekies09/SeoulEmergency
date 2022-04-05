<template>
  <div class="wordcloud">
    <b-tabs>
      <b-tab title="단어로 보기">
        <!-- <div id="wordcl"></div> -->
        <!-- <VueTagCloud v-bin:data="words"></VueTagCloud> -->
        <div id="wordcl2"></div>
        <div>클릭시 해당 단어로 뉴스 검색이 진행됩니다.</div>
      </b-tab>
      <b-tab title="차트로 보기">
        <Chartjs />
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import Chartjs from '@/components/Chartjs.vue';
export default {
  components: {
    Chartjs,
  },
  computed: {
    getComputedList() {
      return this.$store.state.wordsList;
    },
  },
  created() {
    this.modWordsList();
  },
  mounted() {
    // this.genLayout();
    this.genLayout2();
  },
  data() {
    return {
      modedWordsList: [],
    };
  },
  methods: {
    modWordsList() {
      var sum = 0;
      for (var wordAndCountforSum of this.getComputedList) {
        sum += wordAndCountforSum.count;
      }
      for (var wordAndCount of this.getComputedList) {
        var temp = {
          word: '',
          count: 0,
        };
        temp.word = wordAndCount.word;
        temp.count = parseInt((wordAndCount.count / sum) * 100);
        this.modedWordsList.push(temp);
      }
    },

    genLayout2() {
      const width = 500;
      const height = 500;
      const cloud = require('d3-cloud');
      cloud()
        .size([width, height])
        .words(this.modedWordsList)
        .rotate(function () {
          return Math.random() * 360;
        })
        .fontSize((d) => d.count + 10)
        .on('end', this.end2)
        .spiral('rectangular')
        .start()
        .stop();
    },
    end2(words) {
      const d3 = require('d3');
      const width = 500;
      const height = 500;

      d3.select('#wordcl2')
        .append('svg')
        .attr('width', width)
        .attr('height', height)
        .append('g')
        .attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')')
        .selectAll('text')
        .data(words)
        .enter()
        .append('text')
        .style('font-family', 'Impact')
        .style('fill-opacity', 0.6)
        .attr('text-anchor', 'middle')
        .style('font-size', (d) => d.count * 5 + 'px')
        .attr('transform', (d) => 'translate(' + [d.x, d.y] + ')')
        .append('a')
        .attr('href', function (d) {
          return 'https://search.naver.com/search.naver?sm=tab_sug.top&where=news&query=' + d.word;
        })
        .attr('target', '_blank')
        .style('text-decoration', 'none')
        .style('fill', function (d) {
          if (parseInt(d.count) > parseInt(20)) return '#dd3333';
          else if (parseInt(d.count) > parseInt(15)) return '#8224e3';
          else if (parseInt(d.count) > parseInt(10)) return '#81d742';
          else if (parseInt(d.count) > parseInt(7)) return '#00c280';
          else if (parseInt(d.count) > parseInt(5)) return '#1e73be';
          else if (parseInt(d.count) > parseInt(3)) return '#dd9933';
          else if (parseInt(d.count) > parseInt(1)) return '#626208';
          else '#025275';
        })
        .text((d) => d.word);
    },

    // funcalert() {
    //   $('#wordcl2').jQcloud(this.words2);
    // },
  },
};
</script>

<style scoped>
img {
  width: 45vw;
  min-width: 500px;
}
#wordcl {
  width: 45vw;
  height: 45vw;
}
a:hover {
  text-decoration: underline;
}
</style>
