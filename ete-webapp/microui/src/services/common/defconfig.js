/**
 * Created by liang on 2017/10/27.
 */
import { request, config } from 'utils'
const { api:{ config:{ def :defConfig}} } = config

export async function totalUser (params) {
  return request({
    url: "/api/v3/index/totalUser",
    method: 'post',
    data: params,
  })
}
