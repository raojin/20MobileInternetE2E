import { request, config } from 'utils'
const { api:{ config:{ def :defConfig}} } = config

export async function queryBusiness (params) {
  return request({
    url:"api/v9/index/queryBusinessType?level=:level&timeId=:timeId&areaId=:areaId&netType=:netType&field_type=:field_type&app_type_id=:app_type_id&app_type=:app_type",
    method: 'POST',
    data: params,
    headers: {"Content-Type":"application/x-www-form-urlencoded; text/html; charset=utf-8"}
  })
}
