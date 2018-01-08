const path = require('path')
const { version } = require('./package.json')

const svgSpriteDirs = [
  path.resolve(__dirname, 'src/svg/'),
  require.resolve('antd').replace(/index\.js$/, '')
]

export default {
  entry: 'src/index.js',
  svgSpriteLoaderDirs: svgSpriteDirs,
  theme: "./theme.config.js",
  publicPath: `/${version}/`,
  outputPath: `./dist/${version}`,
  // 接口代理示例
  proxy: {
    "/api/v1/weather": {
      "target": "https://api.seniverse.com/",
      "changeOrigin": true,
      "pathRewrite": { "^/api/v1/weather": "/v3/weather" }
    },
    "/api/v2": {
      "target": "http://10.8.132.162:5100",
      "pathRewrite": { "^/api/v2" : "/api/v1" }
    },
    "/api/v3": {
      "target": "http://10.8.132.162:5100",
      "pathRewrite": { "^/api/v3" : "" }
    },
    "/api/v4": {
      "target": "http://10.8.132.163:31102",
        "pathRewrite": { "^/api/v4" : "" }
    },
    "/api/v5": {//一二级业务流量top
    "target": "http://10.8.132.163:31011",
    "pathRewrite": { "^/api/v5" : "" }
    },
    "/api/v6": {//榜单
      "target": "http://10.8.132.163:31017",
      //"target": "http://172.16.132.205:7077",
      "pathRewrite": { "^/api/v6" : "" }
    },
    "/api/v7": {
      "target": "http://10.8.132.163:31013",
      "pathRewrite": { "^/api/v7" : "" }
    },
    "/api/v8": {//热门终端分析
      "target": "http://10.8.132.163:31014",
      "pathRewrite": { "^/api/v8" : "" }
    },
    "/api/v9": {//业务构成分析
      "target": "http://10.8.132.163:31013",
      "pathRewrite": { "^/api/v9" : "" }
    }
},
  env: {
    development: {
      extraBabelPlugins: [
        "dva-hmr",
        "transform-runtime",
        [
          "import", {
            "libraryName": "antd",
            "style": true
          }
        ]
      ]
    },
    production: {
      extraBabelPlugins: [
        "transform-runtime",
        [
          "import", {
            "libraryName": "antd",
            "style": true
          }
        ]
      ]
    }
  },
  dllPlugin: {
    exclude: ["babel-runtime", "roadhog", "cross-env"],
    include: ["dva/router", "dva/saga", "dva/fetch"]
  }
}
