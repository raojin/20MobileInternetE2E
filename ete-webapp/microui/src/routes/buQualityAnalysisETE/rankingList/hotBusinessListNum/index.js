import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'dva'
import { color } from 'utils'
import { Page } from 'components'
import HblNumGrid from './hblNumGrid'


function HotBusinessListNum ({ hotblnumData }) {
  let dataItem = hotblnumData.list.list
  let res = [], gridData = [], logoImg = [], keyId = []
  let key = ''
  let rank = [],flu = [],change_num = []
  let style=[]
  let styles=[]
  if(dataItem != "" && dataItem != null && dataItem.length !=0){
    console.log(hotblnumData.list.list)
    for (let i = 0; i < dataItem.length; i++) {
      let item = ""
      item = dataItem[i]
      logoImg.push("http://10.180.212.87/INAS/static/jslib"+item.LOGO_URL)
      styles.push({img:logoImg[i],name: item.APP_SUBTYPE_NAME})
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
      gridData.push({keyId: keyId[i],styles: styles[i],logoImg: logoImg[i],APP_SUBTYPE_NAME: item.APP_SUBTYPE_NAME,APP_TYPE_NAME: item.APP_TYPE_NAME,APP_SUBTYPE:style[i]});
    }
    let queryByApp = (logoImg) => {
      alert(logoImg)
    }
    let querySubtype = (APP_SUBTYPE_NAME) => {
      alert(APP_SUBTYPE_NAME)
    }

    const rankData={

      colNames:[
        {title: '排行',dataIndex: 'keyId',key: 'keyId',width:20,render: (keyId => (
          <b className="ico_num">{keyId}</b>
        ))},
        {title: '业务图标',dataIndex: 'styles',key: 'styles',width:100, render: (styles => (
          <a onClick={(e) => queryByApp(styles)}>
            <img src={styles.img} style={{width:"28px",height:"28px",margin:"5px",cursor:"pointer"}} />
            <div className="tableValue">{styles.name}</div>
          </a>
        ))},
        /*{title: '业务类型',dataIndex: 'APP_SUBTYPE_NAME',key: 'APP_SUBTYPE_NAME',width:20,render: (APP_SUBTYPE_NAME => (
          <a onClick={(e) => querySubtype(APP_SUBTYPE_NAME)}>
            <div className="tableValue">{APP_SUBTYPE_NAME}</div>
          </a>
        ))},*/
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
      <HblNumGrid dataSource={res}/>
    </div>
  )
}

HotBusinessListNum.propTypes = {
  hotblnumData: PropTypes.object,
}

export default connect(({ hotblnumData }) => ({ hotblnumData }))(HotBusinessListNum)
