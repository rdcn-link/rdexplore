const path = require('path');
function resolve(dir) {
  return path.join(__dirname, dir)
}

// 引入等比适配插件
const px2rem = require('postcss-px2rem');

// 配置基本大小,基准大小 baseSize，需要和rem.js中相同
const postcss = px2rem({
  remUnit: 16
});

module.exports = {
  publicPath: process.env.VUE_APP_PUBLICPATH,
  configureWebpack: (config) => {
    if (process.env.NODE_ENV === 'production') {
      config.optimization.minimizer[0].options.terserOptions.compress.warnings = false;
      config.optimization.minimizer[0].options.terserOptions.compress.drop_console = true;
      config.optimization.minimizer[0].options.terserOptions.compress.drop_debugger = true;
      config.optimization.minimizer[0].options.terserOptions.compress.pure_funcs = ['console.log'];
    }
  },
  chainWebpack: (config) => {
    config.resolve.alias.set('@', resolve('src'))
  },
  css: {
    loaderOptions: {
      sass: {
        prependData: `@import "@/styles/mixin.scss";`
      },
      postcss: {
        plugins: [
          postcss
        ]
      }

    }
  },
  devServer: {
    open: true,
    overlay: {
      warnings: false,
      errors: true
    },
    proxy: {
      '/api': {
        target: '',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  },
  productionSourceMap: process.env.NODE_ENV !== 'production',
  lintOnSave: false,
};
