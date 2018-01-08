/**
 * Created by liang on 2017/10/27.
 */
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

const database = configdata.data;
module.exports = {
  [`GET ${apiPrefix}/sysconfigs/module/default`] (req, res) {
    res.status(200).json({
      data: database,
      code: 1000
    })
  }
}
