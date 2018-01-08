
import { request, config } from 'utils'
const { api:{ config:{ def :defConfig}} } = config

export async function totalErr (params) {
  return request({

    url:"/api/v5/index/queryFlowTop?flag=one&level=day&timeId=20171210&netType=4G&queryType=999999%2C1%2C7%2C15%2C5%2C8&areaId=851",
    method: 'POST',
    data: params,
    headers: {"Content-Type":"application/x-www-form-urlencoded"}
  })
}
