import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter);


const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};

const routes = [
  {
    path: '/',
    redirect: '/home',
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('@/views/home/index.vue'),
    meta: {title: '首页'},
  },
  {
    path: '*',
    redirect: '/home'
  },
];

const router = new VueRouter({
  mode: 'history',
  linkActiveClass: 'is-active',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior() {
    return {y: 0}
  }
});

router.beforeEach(async (to, from, next) => {
  next()
});


export default router
