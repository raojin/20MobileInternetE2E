/* global window */
import classnames from 'classnames'
import lodash from 'lodash'
import config from './config'
import request from './request'
import { color } from './theme'

// 连字符转驼峰
String.prototype.hyphenToHump = function () {
  return this.replace(/-(\w)/g, (...args) => {
    return args[1].toUpperCase()
  })
}

// 驼峰转连字符
String.prototype.humpToHyphen = function () {
  return this.replace(/([A-Z])/g, '-$1').toLowerCase()
}

// 日期格式化
Date.prototype.format = function (format) {
  const o = {
    'M+': this.getMonth() + 1,
    'd+': this.getDate(),
    'h+': this.getHours(),
    'H+': this.getHours(),
    'm+': this.getMinutes(),
    's+': this.getSeconds(),
    'q+': Math.floor((this.getMonth() + 3) / 3),
    S: this.getMilliseconds(),
  }
  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, `${this.getFullYear()}`.substr(4 - RegExp.$1.length))
  }
  for (let k in o) {
    if (new RegExp(`(${k})`).test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : (`00${o[k]}`).substr(`${o[k]}`.length))
    }
  }
  return format
}


/**
 * @param   {String}
 * @return  {String}
 */

const queryURL = (name) => {
  let reg = new RegExp(`(^|&)${name}=([^&]*)(&|$)`, 'i')
  let r = window.location.search.substr(1).match(reg)
  if (r != null) return decodeURI(r[2])
  return null
}

/**
 * 数组内查询
 * @param   {array}      array
 * @param   {String}    id
 * @param   {String}    keyAlias
 * @return  {Array}
 */
const queryArray = (array, key, keyAlias = 'key') => {
  if (!(array instanceof Array)) {
    return null
  }
  const item = array.filter(_ => _[keyAlias] === key)
  if (item.length) {
    return item[0]
  }
  return null
}

/**
 * 数组格式转树状结构
 * @param   {array}     array
 * @param   {String}    id
 * @param   {String}    pid
 * @param   {String}    children
 * @return  {Array}
 */
const arrayToTree = (array, id = 'id', pid = 'pid', children = 'children') => {
  let data = lodash.cloneDeep(array)
  let result = []
  let hash = {}
  data.forEach((item, index) => {
    hash[data[index][id]] = data[index]
  })

  data.forEach((item) => {
    let hashVP = hash[item[pid]]
    if (hashVP) {
      !hashVP[children] && (hashVP[children] = [])
      hashVP[children].push(item)
    } else {
      result.push(item)
    }
  })
  return result
}

const firstLetterSort = (menu, fn = (o)=>{return o;}) => {
  if(!String.prototype.localeCompare)
    return null;
  const arr = lodash.cloneDeep(menu)
  const letters = "abcdefghijklmnopqrstuvwxyz";
  const lettersArr = letters.split('');
  const zh = "阿八嚓哒妸发旮哈*讥咔垃痳拏噢妑七呥扨它**穵夕丫帀".split('');
  const segs = [];
  let curr;
  lettersArr.forEach(function(item,i){
    curr = {letter: item, data:[]};
    arr.forEach(function(item2){
      const data = fn(item2);
      if(letters.indexOf(data)!=-1){
        if(item.localeCompare(data) == 0) {
          curr.data.push(item2);
        }
      }else{
        if("*"!=zh[i]){
          if(zh[i].localeCompare(data, "zh") <= 0 &&  (i+1==zh.length || zh[i+1].localeCompare(data, "zh")==1)) {
            curr.data.push(item2);
          }
        }
      }
    });
    if(curr.data.length) {
      segs.push(curr);
      curr.data.sort(function(a,b){
        return fn(a).localeCompare(fn(b), "zh");
      });
    }
  });
  return segs;
}

module.exports = {
  config,
  request,
  color,
  classnames,
  queryURL,
  queryArray,
  arrayToTree,
  firstLetterSort
}
