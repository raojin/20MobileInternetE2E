import { request, config } from 'utils'
const { api:{ config:{ def :defConfig}} } = config

export async function queryTwoData (params) {
  return request({
    url:"/api/v5/index/queryFlowTop?flag=:flag&level=:level&timeId=:timeId&netType=:netType&areaId=:areaId&ywType=:ywType",
    method: 'POST',
    data: params,
    headers: {"Content-Type":"application/x-www-form-urlencoded"}
  })
}

