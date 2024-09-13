// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import App from './App.vue'
import Vue from 'vue'
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Prototype from "./utils/prototype"


Vue.use(ElementUI);
Vue.use(Prototype);
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  //需要将store和vue实例进行关联，这里将其传递进去
  store,
  components: { App },
  template: '<App/>'
})

