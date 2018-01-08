import { request, config } from 'utils'
const { api:{ config:{ def :defConfig}} } = config

export async function queryOneData (params) {
  return request({
    url:"/api/v5/index/queryFlowTop?flag=:flag&level=:level&timeId=:timeId&netType=:netType&queryType=:queryType&areaId=:areaId",
    method: 'POST',
    data: params,
    headers: {"Content-Type":"application/x-www-form-urlencoded;text/html; charset=utf-8"}
  })
}

