
import { request, config } from 'utils'
const { api:{ config:{ def :defConfig}} } = config

export async function queryBusinessType (params) {
  return request({
    url:"api/v9/index/queryBusinessType?level=:level&timeId=:timeId&areaId=:areaId&flag=all&netType=:netType&cityId=",
    method: 'POST',
    data: params,
    headers: {"Content-Type":"application/x-www-form-urlencoded; text/html; charset=utf-8"}
  })
}
//level=hour&timeId=201712011000&areaId=851&cityId=&netType=4G&app_type_id=0&app_type=businessTy
export async function queryBussinessTrendList(params) {
  return request({
    url:"api/v9/index/queryBussinessTrendList?level=:level&timeId=:timeId&areaId=:areaId&cityId=&netType=:netType&app_type_id=:app_type_id&app_type=:app_type",
    method: 'POST',
    data: params,
    headers: {"Content-Type":"application/x-www-form-urlencoded; text/html; charset=utf-8"}
  })
}
