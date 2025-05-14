import { login, logout, getInfo, getMenu } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import store from "../index";
import {getCookie, setCookie} from "../../utils/cookieUtils";

const user = {
  state: {
    // todo: token: getToken(),
    token: '',
    name: '',
    avatar: '',
    roles: [],
    menu: {},
    buttonMap: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_MENU: (state, menu) => {
      state.menu = menu
    },
    SET_BUTTON_MAP: (state, buttonMap) => {
      state.buttonMap = buttonMap
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      const password = userInfo.password.trim()
      const rememberMeFlag = userInfo.rememberMeFlag
      return new Promise((resolve, reject) => {
        let params = new URLSearchParams()
        params.append('username', username)
        params.append('password', password)
        params.append('rememberMeFlag', rememberMeFlag)
        login(params).then(response => {
          const data = response.data.data;
          // 向cookie中设置token
          setToken(data.token);
          // 向store中设置cookie
          commit('SET_TOKEN', data.token);
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取菜单列表
    GetMenu({ commit }) {
      return new Promise((resolve, reject) => {
        getMenu().then(response => {
          const data = response.data.data;
          // 这里对按钮进行一些处理
          let buttonList = data.buttonList
          let map = new Map();
          for(let a=0; a<buttonList.length; a++) {
            map.set(buttonList[a].url, buttonList[a])
          }
          commit('SET_BUTTON_MAP', map)
          commit('SET_MENU', data)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(response => {
          const resp = response.data;
          if (resp.data.roles && resp.data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', resp.data.roles)
          } else {
            reject('登录已过期，请重新登录!')
          }
          commit('SET_NAME', resp.data.name)
          commit('SET_AVATAR', resp.data.avatar)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
