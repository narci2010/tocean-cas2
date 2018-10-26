module.exports = {
  proxyList: {
    '/api': {
      target: 'http://localhost:8080/', // 接口域名
      changeOrigin: true, // 是否跨域
      pathRewrite: {
        '^/api': '/' // 需要rewrite重写的
      }
    },
    '/hefei': {
      target: 'http://192.168.1.82:8080/',
      changeOrigin: true,
      pathRewrite: {
        '^/hefei': '/'
      }
    },
    '/wenchao': {
      target: 'http://192.168.1.93:8888/',
      changeOrigin: true,
      pathRewrite: {
        '^/wenchao': '/'
      }
    }
    }
  }

