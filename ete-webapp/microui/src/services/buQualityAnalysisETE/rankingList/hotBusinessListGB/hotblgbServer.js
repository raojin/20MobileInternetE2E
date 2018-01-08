import { request, config } from 'utils'
const { api:{ config:{ def :defConfig}} } = config

export async function queryHotData (params) {
  return request({
    url:"/api/v6/index/queryBusinessTop?level=:level&timeId=:timeId&areaId=:areaId&netType=:netType&field_type=:field_type&app_type=:app_type&logo_type=:logo_type&top=:top",
    method: 'POST',
    data: params,
    headers: {"Content-Type":"application/x-www-form-urlencoded"}
  })
}

