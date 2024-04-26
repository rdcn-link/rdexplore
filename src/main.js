import Vue from 'vue'
import App from './App.vue'
import router from './router/index.js'
import store from './store'

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(Element,);

import '@/styles/index.scss'

require('./utils/rem.js');

import * as filters from './filters'
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
});


import api from './utils/request.js'
Vue.prototype.$api = api;
import '@/assets/font/font.css'


Vue.config.productionTip = false;
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');
