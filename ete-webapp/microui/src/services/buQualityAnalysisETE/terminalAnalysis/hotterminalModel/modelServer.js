import { request, config } from 'utils'
const { api:{ config:{ def :defConfig}} } = config

export async function queryModelData (params) {
  return request({
    url:"/api/v8/index/queryModelList?level=day&time=20171224&areaId=1766&netType=4G&brand=&page=1&limit=10",
    method: 'POST',
    data: params,
    headers: {"Content-Type":"application/x-www-form-urlencoded"}
  })
}

