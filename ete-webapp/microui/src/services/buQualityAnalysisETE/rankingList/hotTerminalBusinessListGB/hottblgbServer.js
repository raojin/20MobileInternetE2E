import { request, config } from 'utils'
const { api:{ config:{ def :defConfig}} } = config

export async function queryHotTblGbData (params) {
  return request({
    url:"/api/v6/index/queryBusinessTop?level=day&timeId=20171201&areaId=851&netType=4G&field_type=flow&app_type=tm&logo_type=small&top=10",
    method: 'POST',
    data: params,
    headers: {"Content-Type":"application/x-www-form-urlencoded"}
  })
}

