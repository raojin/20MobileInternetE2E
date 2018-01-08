import { request, config } from 'utils'
const { api:{ config:{ def :defConfig}} } = config

export async function queryTrendData (params) {
  return request({
    url:"/api/v7/index/queryTrendAnalysis?level=day&timeId=20171210&areaId=851&netType=4G",
    method: 'POST',
    data: params,
    headers: {"Content-Type":"application/x-www-form-urlencoded"}
  })
}

