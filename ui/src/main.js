import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'

import qs from 'qs';
import request from './request'
Vue.prototype.$request = request
Vue.prototype.$qs = qs

import api from './utils/request.js'
Vue.prototype.$api = api

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

import Terminal from 'vue-web-terminal'
import Highlight from './utils/Hightlight'
Vue.use(Highlight)
Vue.use(Terminal,{ highlight: true});

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
