import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '../components/HelloWorld'
import EsJdIndex from '../components/page/EsJdIndex'
import EsJDSaveContent from '../components/page/EsJDSaveContent'
import Home from '../components/common/Home.vue'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      component: Home,
      children:[
        {
          path:'/es',
          name:'HelloWorld',
          component:HelloWorld,
        },
        {
          path: '/esIndex',
          name:'EsJdIndex',
          component: EsJdIndex
        },
        {
          path: '/saveEsContent',
          name:'EsJDSaveContent',
          component:EsJDSaveContent
        }
      ]
    },
  ]
})
