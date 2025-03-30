import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/common/Home.vue'
import HelloWorld from '../views/HelloWorld'
import index from '../views/index.vue'
import Layout from '../views/layout/Layout.vue'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      component: Layout,
      redirect: '/index',
      name:'首頁',
      children:[
        {
          path:'/index',
          name:'index',
          component:index,
        },
        // {
        //   path: '/esIndex',
        //   name:'EsJdIndex',
        //   component: EsJdIndex
        // },
        // {
        //   path: '/saveEsContent',
        //   name:'EsJDSaveContent',
        //   component:EsJDSaveContent
        // }
      ]
    },
    {
      path: '/blog',
      component: Layout,
      redirect: '/blog/blog',
      name: '博客管理',
      meta: { title: '博客管理', icon: 'edit' },
      children: [
        {
          path: 'blog',
          name: '博客管理',
          component: () => import('@/views/blog/blog'),
          meta: { title: '博客管理', icon: 'edit' }
        },
        // todo:后续添加
        // {
        //   path: 'blogTag',
        //   name: '标签管理',
        //   component: () => import('@/views/blog/blogTag'),
        //   meta: { title: '标签管理', icon: 'tag' }
        // },
        // {
        //   path: 'blogSort',
        //   name: '分类管理',
        //   component: () => import('@/views/blog/blogSort'),
        //   meta: { title: '分类管理', icon: 'sort' }
        // },
        // {
        //   path: 'blogRecommend',
        //   name: '推荐管理',
        //   component: () => import('@/views/blog/blogRecommend'),
        //   meta: { title: '推荐管理', icon: 'sort' }
        // },
        // {
        //   path: 'collect',
        //   name: '收藏管理',
        //   component: () => import('@/views/blog/collect'),
        //   meta: { title: '收藏管理', icon: 'table' }
        // },
        // {
        //   path: 'subject',
        //   name: '专题管理',
        //   component: () => import('@/views/blog/subject'),
        //   meta: { title: '专题管理', icon: 'table' }
        // },
        // {
        //   path: 'subjectItem',
        //   name: '专题元素管理',
        //   component: () => import('@/views/blog/subjectItem'),
        //   meta: { title: '专题元素管理', icon: 'table' }
        // }
      ]
    },
  ]
})
