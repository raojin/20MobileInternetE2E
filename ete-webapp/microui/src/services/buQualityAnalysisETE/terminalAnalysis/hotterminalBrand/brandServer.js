import { request, config } from 'utils'
const { api:{ config:{ def :defConfig}} } = config

export async function queryBrandData (params) {
  return request({
    url:"/api/v8/index/queryBrandList?level=day&time=20171201&areaId=850&netType=4G",
    method: 'POST',
    data: params,
    headers: {"Content-Type":"application/x-www-form-urlencoded"}
  })
}

