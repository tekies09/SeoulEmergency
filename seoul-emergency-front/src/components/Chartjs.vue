<template>
  <Bar
    :chart-options="chartOptions"
    :chart-data="chartData"
    :chart-id="chartId"
    :dataset-id-key="datasetIdKey"
    :plugins="plugins"
    :css-classes="cssClasses"
    :styles="styles"
    :width="width"
    :height="height"
  />
</template>

<script>
import { Bar } from 'vue-chartjs/legacy';
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

export default {
  name: 'BarChart',
  components: { Bar },
  created() {
    this.getData();
  },
  computed: {
    getWordsData() {
      return this.$store.state.wordsList;
    },
  },
  methods: {
    getData() {
      var label = [];
      var data = [];
      var cnt = 0;
      for (var wordAndCount of this.getWordsData) {
        if (cnt == 10) break;
        label.push(wordAndCount.word);
        data.push(wordAndCount.count);
        cnt++;
      }
      var temp = {
        label: '단어',
        backgroundColor: 'skyblue',
        data: data,
      };
      this.chartData.labels = label;
      this.chartData.datasets.push(temp);
    },
  },
  props: {
    chartId: {
      type: String,
      default: 'bar-chart',
    },
    datasetIdKey: {
      type: String,
      default: 'label',
    },
    cssClasses: {
      default: '',
      type: String,
    },
    styles: {
      type: Object,
      default: () => {},
    },
    plugins: {
      type: Object,
      default: () => {},
    },
  },
  data() {
    return {
      chartData: {
        labels: [],
        datasets: [],
      },
      chartOptions: {
        responsive: false,
      },
      width: '500',
      height: '500',
    };
  },
};
</script>
