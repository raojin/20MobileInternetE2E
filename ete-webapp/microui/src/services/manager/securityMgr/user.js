//引用配置JS
import { request, config } from 'utils';
//对象的解构赋值
const { api:{ user:{ users:allUsers }} } = config

//创建异步函数
export async function totalUser (params) {
  //通过request工具类调用post请求
  return request({
    url: "/api/v3/index/totalUser?level=day&timeId=20171106&areaId=851&netType=6",
    method: 'post',
    data: params,
  })
}
export async function badUser (params) {
  //通过request工具类调用post请求
  return request({
    url: "/api/v3/index/badUser?level=day&timeId=20171106&areaId=851&netType=6",
    method: 'post',
    data: params,
  })
}
export async function userCount (params) {
  //通过request工具类调用post请求
  return request({
    url: "/api/v3/index/userCount?level=day&timeId=20171106&areaId=851&netType=6",
    method: 'post',
    data: params,
  })
}
export async function portfolio (params) {
  //通过request工具类调用post请求
  return request({
    url: "/api/v3/index/portfolio?level=day&timeId=20171106&areaId=851&netType=6",
    method: 'post',
    data: params,
  })
}
export async function topCustomer (params) {
  //通过request工具类调用post请求
  return request({
    url: "/api/v3/index/topCustomer?level=day&timeId=20171106&areaId=851&flag=CU_APN&indexKey=ACTIVE_USER_CNT&netType=6",
    method: 'post',
    data: params,
  })
}
export async function topCell (params) {
  //通过request工具类调用post请求
  return request({
    url: "/api/v3/index/topCell?level=day&timeId=20171106&areaId=851&flag=CELL&indexKey=ACTIVE_USER_CNT&netType=6",
    method: 'post',
    data: params,
  })
}
export async function topApn (params) {
  //通过request工具类调用post请求
  return request({
    url: "/api/v3/index/topApn?level=day&timeId=20171106&areaId=851&flag=APN&indexKey=ACTIVE_USER_CNT&netType=6",
    method: 'post',
    data: params,
  })
}
export async function topNe (params) {
  //通过request工具类调用post请求
  return request({
    url: "/api/v3/index/topNe?level=day&timeId=20171106&areaId=851&flag=NE&indexKey=ACTIVE_USER_CNT&netType=6",
    method: 'post',
    data: params,
  })
}
export async function targetCheck (params) {
  //通过request工具类调用post请求
  return request({
    url: "/api/v3/index/targetCheck?level=day&timeId=20171106&areaId=851&netType=6",
    method: 'post',
    data: params,
  })
}
export async function keyTrend (params) {
  //通过request工具类调用post请求
  return request({
    url: "/api/v3/index/keyTrend?level=day&timeId=20171106&areaId=851&netType=6",
    method: 'post',
    data: params,
  })
}

