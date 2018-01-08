const Mock = require('mockjs')
const config = require('../utils/config')
const { apiPrefix } = config

const configdata = Mock.mock({
  'data|1-2': [{
    id : '@id',
    module: 'default',
    'name|1': ['showLetterPanel','mainTheme'],
    'value|1':['true'],
  }]
})
let database = [
  {time: "201712130000", selName: "网页浏览", flow: "8873.43", flowHb: "-0.32", user: "148.04", userZb: "153.19"},
  {time: "201712130000", selName: "视频", flow: "5867.45", flowHb: "12.27", user: "46.06", userZb: "47.67"},
  {time: "201712130000", selName: "视频", flow: "5867.45", flowHb: "12.27", user: "46.06", userZb: "85.16"},
  {time: "201712130000", selName: "游戏", flow: "3060.95", flowHb: "1091.95", user: "22.86", userZb: "23.66"},
  {time: "201712130000", selName: "应用商店", flow: "2880.29", flowHb: "12.79", user: "51.19", userZb: "52.97"},
]

module.exports = {

  [`GET ${apiPrefix}/oneData`] (req, res) {
    res.status(200).json(database)
  },
}
