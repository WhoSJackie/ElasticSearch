import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/common/Home.vue'
import HelloWorld from '../views/HelloWorld'
import index from '../views/index.vue'
import Layout from '../views/layout/Layout.vue'
import store from "../store";
import NProgress from "nprogress";
import {Message} from "element-ui";

import 'nprogress/nprogress.css'// Progress 进度条样式
import { getToken } from '@/utils/auth' // 验权

const whiteList = ['/login'] // 不重定向白名单
const whiteListActiveList = ['/', '/dashboard', '/404', '/401']
const allList = []

Vue.use(Router);
export const constantRouterMap = [
    { path: '/login', component: () => import('@/views/login/index'), hidden: true },
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
  {
    path: '/picture',
    component: Layout,
    redirect: '/picture/picture',
    name: '图片管理',
    meta: { title: '图片管理', icon: 'edit' },
    children: [
      {
        path: 'pictureSort',
        name: '图片分类管理',
        component: () => import('@/views/picture/pictureSort'),
        meta: { title: '图片分类管理', icon: 'edit' }
      },
      {
        path: 'picture',
        name: '图片管理',
        component: () => import('@/views/picture/picture'),
        meta: { title: '图片管理', icon: 'edit' }
      }
      ]
  }


]
const router =  new Router({
    // mode:'history', // 后端支持可开
    scrollBehavior: ()=>({y:0}),
    routes: constantRouterMap
})

router.beforeEach((to, from, next) => {
  if (allList.length === 0) {
    for (let a = 0; a < constantRouterMap.length; a++) {
      if (constantRouterMap[a].children) {
        let childrenList = constantRouterMap[a].children
        for (let b = 0; b < childrenList.length; b++) {
          allList.push(constantRouterMap[a].path + '/' + childrenList[b].path)
        }
      } else {
        allList.push(constantRouterMap[a].path)
      }
    }
  }

  // 向激活的菜单中添加内容
  const activeList = []
  if (store.getters.menu.sonList) {
    const sonList = store.getters.menu.sonList
    for (let c = 0; c < sonList.length; c++) {
      activeList.push(sonList[c].url)
    }
  }
  console.log(getToken());
  NProgress.start();
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      if (store.getters.roles.length === 0) {
        store.dispatch('GetInfo').then(res => { // 拉取用户信息
          next()
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            Message.error(err || 'Verification failed, please login again')
            next({ path: '/' })
          })
        })

        // store.dispatch('GetMenu').then(res => { // 菜单信息
        //   next()
        // }).catch((err) => {
        //   store.dispatch('FedLogOut').then(() => {
        //     Message.error(err || 'Verification failed, please login again')
        //     next({ path: '/' })
        //   })
        // })
      } else {
        if (whiteListActiveList.indexOf(to.path) !== -1) {
          next()
        } else if (activeList.indexOf(to.path) !== -1) {
          next()
        } else {
          if (allList.indexOf(to.path) !== -1) {
            next({ path: '/401' })
          } else {
            next({ path: '/404' })
          }
        }
      }
    }
  } else {
    // 白名单直接通过
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
})

export default router
