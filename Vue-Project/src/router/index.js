import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import VuexDemo from '@/components/VuexDemo'
import Index from '../views/index/index'

Vue.use(Router)

export default new Router({
  routes: [{
      path: '/hello',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/index',
      name: 'Index',
      component: Index
    },
    {
      path: '/',
      name: 'vuexDemo',
      component: VuexDemo
    },
    {
      path: '/vuexDemo',
      name: 'vuexDemo',
      component: VuexDemo
    }
  ]
})
