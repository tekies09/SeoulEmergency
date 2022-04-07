import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue';

// Import Bootstrap an BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

// Import Naver Map Vue Library
import naver from 'vue-naver-maps';

// Import VueGeolocationApi for Get Current Location
import VueGeolocationApi from 'vue-geolocation-api';

// Import jQcloud for word cloud
// import 'expose-loader?$!expose-loader?jQuery!jquery';
// import jQcloud from './js/jqcloud.min.js';

// Mobile Screen Detection
import VueMobileDetection from 'vue-mobile-detection';

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue);
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin);
// Make VueNaverMap available by Set ClientId
Vue.use(naver, {
  clientID: 'ttsrjtbjqw',
});
// Make VueGeolocationApi available
Vue.use(VueGeolocationApi);

// Vue.use(jQcloud);

Vue.use(VueMobileDetection);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
