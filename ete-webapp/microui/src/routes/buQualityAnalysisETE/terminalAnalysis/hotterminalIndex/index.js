import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'dva'
import HottIndexGrid from './hottIndexGrid'

const HotterminalIndex = ({ indexValue }) => {
  let dataItem = indexValue.list.elements
  let res = []
  let gridData = []
  if(dataItem != "" && dataItem != null && dataItem.length !=0){
    console.log(indexValue.list.elements)
    for (let i = 0; i < dataItem.length; i++) {
      let item = ""
      item = dataItem[i]
      gridData.push({key: i+1,TIME_ID: item.TIME_ID,AREA_NAME: item.AREA_NAME,IMEI_TAC: item.IMEI_TAC,
        TMBRAND: item.TMBRAND,TMMODEL: item.TMMODEL,TMNETTYPE: item.TMNETTYPE,
        TMSUPPORTSYS: item.TMSUPPORTSYS,SUMAVG: item.SUMAVG,SUMCNT: item.SUMCNT,ATT_SUCCRATE: item.ATT_SUCCRATE,
        RAU_SUCCRATE: item.RAU_SUCCRATE,DNS_SUCCRATE: item.DNS_SUCCRATE,TCP_SUCCRATE: item.TCP_SUCCRATE,
        SESSION_SUCCRATE: item.SESSION_SUCCRATE,SESSION_DUR: item.SESSION_DUR,SPEED_DL: item.SPEED_DL
      });
    }
  }
  const rankData={
    colNames:[
      {title: '时间',dataIndex: 'TIME_ID',key: 'TIME_ID',width:100/*,sorter: (a, b) => a.selName.length - b.selName.length*/},
      {title: '地市',dataIndex: 'AREA_NAME',key: 'AREA_NAME',width:100/*,sorter: (a, b) => a.flow - b.flow*/},
      {title: 'IMEI_TAC',dataIndex: 'IMEI_TAC',key: 'IMEI_TAC',width:100},

      {title: '终端品牌',dataIndex: 'TMBRAND',key: 'TMBRAND',width:100},
      {title: '终端型号',dataIndex: 'TMMODEL',key: 'TMMODEL',width:100},
      {title: '终端类型',dataIndex: 'TMNETTYPE',key: 'TMNETTYPE',width:100},

      {title: '终端制式',dataIndex: 'TMSUPPORTSYS',key: 'TMSUPPORTSYS',width:100},
      {title: '流量(GB)',dataIndex: 'SUMAVG',key: 'SUMAVG',width:100},
      {title: '终端数(个)',dataIndex: 'SUMCNT',key: 'SUMCNT',width:100},
      {title: 'Attach成功率(%)',dataIndex: 'ATT_SUCCRATE',key: 'ATT_SUCCRATE',width:100},
      {title: 'TAU成功率(%)',dataIndex: 'RAU_SUCCRATE',key: 'RAU_SUCCRATE',width:100},

      {title: 'DNS成功率(%)',dataIndex: 'DNS_SUCCRATE',key: 'DNS_SUCCRATE',width:100},
      {title: 'TCP成功率(%)',dataIndex: 'TCP_SUCCRATE',key: 'TCP_SUCCRATE',width:100},
      {title: '业务访问成功率(%)',dataIndex: 'SESSION_SUCCRATE',key: 'SESSION_SUCCRATE',width:100},
      {title: '业务访问时延(ms)',dataIndex: 'SESSION_DUR',key: 'SESSION_DUR',width:100},
      {title: '下行速率(kpbs)',dataIndex: 'SPEED_DL',key: 'SPEED_DL',width:100},
    ],
    pageSize:10,
    total:gridData.length ,
    defaultCurrent:1,
    data:gridData
  }
 // res.push({"legendData":legendData,"xAxisData":xAxisData,"seriesData":seriesData,"rankData":rankData})

  return (
    <div>
      <div>
        <HottIndexGrid dataSource={rankData}/>
      </div>
    </div>
  )
}


HotterminalIndex.propTypes = {
  post: PropTypes.object,
  indexValue: PropTypes.object,
  dispatch: PropTypes.func,
}

export default connect(({ indexValue }) => ({ indexValue }))(HotterminalIndex)

