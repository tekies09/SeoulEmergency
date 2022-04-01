<template>
  <div class="wordcloud">
    <b-tabs>
      <b-tab title="단어로 보기">
        <!-- <div id="wordcl"></div> -->
        <!-- <VueTagCloud v-bin:data="words"></VueTagCloud> -->
        <div id="wordcl2"></div>
      </b-tab>
      <b-tab title="차트로 보기">
        <img src="https://via.placeholder.com/400x300?text=Chart" />
      </b-tab>
      <b-tab title="머시기로 보기">
        <img src="https://via.placeholder.com/400x300?text=Mosigi" />
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
export default {
  computed: {
    getComputedList() {
      console.log(this.$store.state.wordsList);
      return this.$store.state.wordsList;
    },
  },
  mounted() {
    this.genLayout();
    this.genLayout2();
  },
  methods: {
    genLayout() {
      const cloud = require('d3-cloud');
      cloud()
        .words(this.getComputedList)
        .padding(1)
        .fontSize(function (d) {
          return d.count;
        })
        .on('end', this.end)
        .spiral('archimedean')
        .start()
        .stop();
    },
    end(words) {
      const d3 = require('d3');
      const width = 300;
      const height = 300;
      d3.select('#wordcl')
        .append('svg')
        .attr('width', width)
        .attr('height', height)
        .append('g')
        .attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')')
        .selectAll('text')
        .data(words)
        .enter()
        .append('text')
        .style('font-size', (d) => {
          return d.count * 3 + 'px';
        })
        .attr('text-anchor', 'middle')
        .attr('transform', (d) => {
          console.log(d.x + ' ' + d.y);
          return 'translate(' + [d.x, d.y] + ')rotate(' + d.rotate + ')';
        })
        .attr('href', 'http://www.naver.com')
        .text((d) => d.word);
    },

    genLayout2() {
      const width = 500;
      const height = 500;
      const cloud = require('d3-cloud');
      cloud()
        .size([width, height])
        .words(this.getComputedList)
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
        .style('font-family', 'Impact')
        .attr('text-anchor', 'middle')
        .style('font-size', (d) => d.count * 5 + 'px')
        .attr('transform', (d) => 'translate(' + [d.x, d.y] + ')rotate(' + d.rotate + ')')
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
</style>
