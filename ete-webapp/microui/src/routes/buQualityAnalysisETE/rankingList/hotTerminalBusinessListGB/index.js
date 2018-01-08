import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'dva'
import { Page } from 'components'
import HtblGBGrid from './htblGBGrid'

function HotTerminalBusinessListGB ({ hottblgbData }) {
  let dataItem = hottblgbData.list.list
  let res = [], gridData = [], logoImg = [], keyId = []
  let key = ''
  let rank = [],flu = [],change_num = []
  let style=[]
  if(dataItem != "" && dataItem != null && dataItem.length !=0){
    console.log(hottblgbData.list.list)
    for (let i = 0; i < dataItem.length; i++) {
      let item = ""
      item = dataItem[i]
      logoImg.push("http://10.180.212.87/INAS/static/jslib"+item.LOGO_URL)
      if(i<9){
        key = "0" +(i+1)
        keyId.push(key)
      }else {
        key = i+1
        keyId.push(key)
      }
      rank = item.ROWNUM_
      flu = item.FLU
      change_num = parseInt(flu)-parseInt(rank)
      if(change_num<0){
        style.push({c_num:Math.abs(change_num),icon:"ico_down",font:"red"})
      }else if(change_num==0){
        style.push({c_num:"",icon:"ico_level",font:""})
      }else{
        style.push({c_num:"",icon:"ico_level",font:""})
      }
      /*if(flu == null|| flu == ''){
        style.push({c_num:"",icon:"ico_level",font:""})
      }*/
      gridData.push({keyId: keyId[i],logoImg: logoImg[i],APP_SUBTYPE_NAME: item.APP_SUBTYPE_NAME,APP_TYPE_NAME: item.APP_TYPE_NAME,APP_SUBTYPE:style[i]});
    }
    let querySubtype = (APP_SUBTYPE_NAME) => {
      alert(APP_SUBTYPE_NAME)
    }

    const rankData={

      colNames:[
        {title: '排行',dataIndex: 'keyId',key: 'keyId',width:20,render: (keyId => (
          <b className="ico_num">{keyId}</b>
        ))},
        {title: '业务类型',dataIndex: 'APP_SUBTYPE_NAME',key: 'APP_SUBTYPE_NAME',width:50,render: (APP_SUBTYPE_NAME => (
          <a onClick={(e) => querySubtype(APP_SUBTYPE_NAME)}>
            <div className="tableValue">{APP_SUBTYPE_NAME}</div>
          </a>
        ))},
        {title: '状态',dataIndex: 'APP_SUBTYPE',key: 'APP_SUBTYPE',width:50,render: (APP_SUBTYPE => (
          <div>
            <b className={APP_SUBTYPE.icon}></b>
            <span style={{color:APP_SUBTYPE.font, size:"12px"}} >{APP_SUBTYPE.c_num}</span>
          </div>
        ))}
      ],
      pageSize:10,
      total: 10,
      defaultCurrent:1,
      data:gridData
    }
    res.push({"rankData":rankData})
  }

  return (
    <div >
      <HtblGBGrid dataSource={res}/>
    </div>
  )
}

HotTerminalBusinessListGB.propTypes = {
  hottblgbData: PropTypes.object,
}

export default connect(({ hottblgbData }) => ({ hottblgbData }))(HotTerminalBusinessListGB)
