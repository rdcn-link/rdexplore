const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({



  devServer: {
    proxy: {
      '/api': {
        target: '',
        changeOrigin: true,
        ws: true, // 是否启用websockets
        secure: false, // 使用的是http协议则设置为false，https协议则设置为true
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  },

  // parallel: false,

  lintOnSave: false,   // 取消 eslint 验证
  transpileDependencies: true
})
