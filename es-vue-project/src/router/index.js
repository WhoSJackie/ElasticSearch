import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '../views/HelloWorld'
import EsJdIndex from '../views/EsJdIndex'
import EsJDSaveContent from '../views/EsJDSaveContent'
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
