import { request, config } from 'utils'
const { api:{ config:{ def :defConfig}} } = config

export async function queryFreeblNumData (params) {
  return request({
    url:"/api/v6/index/queryBusinessTop?level=day&timeId=20171210&areaId=851&netType=4G&field_type=user&app_type=own&logo_type=small&top=10",
    method: 'POST',
    data: params,
    headers: {"Content-Type":"application/x-www-form-urlencoded"}
  })
}

