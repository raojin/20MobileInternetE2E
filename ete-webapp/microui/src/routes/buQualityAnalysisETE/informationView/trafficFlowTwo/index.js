import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'dva'
import TftBerPieEcharts from './tftBerPieEcharts'

const TrafficFlowTwo = ({ trafficFlowTwo }) => {
  let dataItem = trafficFlowTwo.list.list
  let res = []
  let gridData = []
  let legendData = [], xAxisData = [], yAxisName = [], seriesData = [], pointValue1 = [], pointValue2 = []
  if(dataItem != "" && dataItem != null && dataItem.length !=0){
    console.log(trafficFlowTwo.list.list)
    //let legendData = [], xAxisData = [], yAxisName = [], seriesData = [], pointValue1 = [], pointValue2 = []
    legendData.push("流量(GB)")
    legendData.push("流量占比(%)")
    //yAxisName.push("流量(GB)")
    for (let i = 0; i < dataItem.length; i++) {
      let item = ""
      item = dataItem[i]
      xAxisData.push(item.selName)
      pointValue1.push({
        value: item.flow,
        tooltip: {
          formatter: "{b}<br/>{a}:{c}<br/>" +
          "占比："+(item.allFlowZb==null?"":item.allFlowZb)+"%<br/>" +
          "环比："+item.flowHb+"%"
        },
      })
      pointValue2.push({value:item.allFlowZb,name:item.selName,
        tooltip : {
          formatter: "{b}<br/>{a}:{d}<br/>" +
          "全网流量占比(%):{c}<br/>" +
          "流量(GB)："+(item.flow==null?"":item.flow)+""
        }})
      gridData.push({key: i+1,time: item.time,selName: item.selName,flow: item.flow,allFlowZb: item.allFlowZb,flowHb: item.flowHb,user: item.user});
    }

    const rankData={
      colNames:[
        {title: '业务名称',dataIndex: 'selName',key: 'selName',width:100/*,sorter: (a, b) => a.selName.length - b.selName.length*/},
        {title: '流量',dataIndex: 'flow',key: 'flow',width:100,sorter: (a, b) => a.flow - b.flow},
        {title: '全网占比（%）',dataIndex: 'allFlowZb',key: 'allFlowZb',width:100,sorter: (a, b) => a.allFlowZb - b.allFlowZb},
        {title: '环比（%）',dataIndex: 'flowHb',key: 'flowHb',width:100,sorter: (a, b) => a.flowHb - b.flowHb},
        {title: '用户数（万）',dataIndex: 'user',key: 'user',width:100,sorter: (a, b) => a.user - b.user},
      ],
      pageSize:5,
      total:gridData.length ,
      defaultCurrent:1,
      data:gridData
    }
    if (pointValue1.length>0 || pointValue2.length>0) {
      seriesData.push(pointValue1)
      seriesData.push(pointValue2)

      res.push({"legendData":legendData,"xAxisData":xAxisData,"seriesData":seriesData,"rankData":rankData})
    }
  }else {
    const rankData={
      colNames:[
        {title: '业务',dataIndex: 'selName',key: 'selName',width:100/*,sorter: (a, b) => a.selName.length - b.selName.length*/},
        {title: '流量',dataIndex: 'flow',key: 'flow',width:100,sorter: (a, b) => a.flow - b.flow},
        {title: '全网占比（%）',dataIndex: 'allFlowZb',key: 'allFlowZb',width:100,sorter: (a, b) => a.allFlowZb - b.allFlowZb},
        {title: '环比（%）',dataIndex: 'flowHb',key: 'flowHb',width:100,sorter: (a, b) => a.flowHb - b.flowHb},
        {title: '用户数（万）',dataIndex: 'user',key: 'user',width:100,sorter: (a, b) => a.user - b.user},
      ],
    }
    res.push({"legendData":legendData,"xAxisData":xAxisData,"seriesData":seriesData,"rankData":rankData})
  }

  return (
    <div>
      <div>
        <TftBerPieEcharts dataSource={res}/>
      </div>
    </div>
  )
}


TrafficFlowTwo.propTypes = {
  post: PropTypes.object,
  trafficFlowTwo: PropTypes.object,
  dispatch: PropTypes.func,
}

export default connect(({ trafficFlowTwo }) => ({ trafficFlowTwo }))(TrafficFlowTwo)

