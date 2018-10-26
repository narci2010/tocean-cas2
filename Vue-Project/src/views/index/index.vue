<template>
  <div>
    {{msg}}</div>
</template>

<script>
export default {
  name: 'index',
  data() {
    return {
      msg: 'Welcome to Your Vue.js App',
      superinfo: {},
      loginForm: {
        username: 'narci',
        password: '123456'
      },
      loading: false,
      registerUrl: 'http://localhost:8080/getCurrentUser',
      loginUrl: 'http://localhost:8080/login',
      logoutUrl: 'http://localhost:8080/logout',
      createUserURL: 'http://localhost:8080/zsuSysUser/create',
      registerUrlP: '/api/getCurrentUser',
      loginUrlP: '/api/login',
      logoutUrlP: '/api/logout',
      createUserURLP: '/api/zsuSysUser/create',
      user: {
        username: 'narci',
        password: '123456',
        roleIds: '1',
        locked: 0
      },
      cors: ''

    }
  },
  mounted: function() {
    this.$nextTick(function() {
      // this.cors = this.getCookie('XSRF-TOKEN')
      // this.logout()
      // this.handleLogin()
      // this.test()
      // this.createUser()
      // this.createUser2()
    })
  },
  methods: {
    logout() {
      this.$axios({
        method: 'get',
        url: this.logoutUrl,
        dataType: 'json',
        withCredentials: true
      }).then(response => {
        // this.$router.push({ path: '/hello' })
        console.log(response.data)
      }).catch(error => {
        console.log(error)
      })
    },
    // 登录
    handleLogin() {
      // axios.get('http://dev.xxxxxxxxxxxxxxx',{withCredentials:true}).then
      this.$axios({
        method: 'post',
        url: this.loginUrl,
        dataType: 'json',
        withCredentials: true,
        data: this.loginForm,
        // 把对象转换成传统form data形式
        transformRequest: [function(data) {
          let ret = ''
          for (let it in data) {
            ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
          }
          return ret
        }]
      }).then(response => {
        // this.$router.push({ path: '/hello' })
        // 将JSON对象转化为JSON字符
        // const last=obj.toJSONString()
        // const last=JSON.stringify(obj)
        // 将字符串转json对象
        // const result = JSON.parse(response.data)
        console.log(response.data)
      }).catch(error => {
        console.log(error)
      })
    },
    test() {
      this.$axios.get(this.registerUrl, this.loginForm, { withCredentials: true, headers: { 'X-XSRF-Token': this.cors } })
        .then(function(response) {
          console.log(response)
        })
        .catch(function(error) {
          console.log(error)
        })
    },
    test2() {
      this.$axios({
        method: 'get',
        url: this.registerUrlP,
        dataType: 'json',
        withCredentials: true
      }).then(response => {
        // this.$router.push({ path: '/hello' })
        console.log(response.data)
      }).catch(error => {
        console.log(error)
      })
    },
    createUser() {
      this.$axios.post(this.createUserURLP, this.user, {
        transformRequest: [function(data) {
          let ret = ''
          for (let it in data) {
            ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
          }
          return ret
        }]
      })
        .then(function(response) {
          console.log(response)
        })
        .catch(function(error) {
          console.log(error)
        })
    },
    createUser2() {
      this.$axios.post(this.createUserURLP, this.user)
        .then(function(response) {
          console.log(response)
        })
        .catch(function(error) {
          console.log(error)
        })
    },
    getCookie(name) {
      const v = window.document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)')
      return v ? v[2] : null
    }
  }
}
</script>