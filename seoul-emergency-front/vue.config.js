const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8080,
    open: true,
    proxy: {
      '/api' : {
        target: 'http://j6a403.p.ssafy.io',
        changeOrigin: true,
      }
    },
  }
})
