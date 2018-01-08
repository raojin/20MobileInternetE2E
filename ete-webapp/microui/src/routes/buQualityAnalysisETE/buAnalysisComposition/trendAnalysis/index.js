import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'dva'
import TrendBerLineEcharts from './trendBerLineEcharts'

const TrendAnalysis = ({ trendValue }) => {
  let dataItem = trendValue.list.list
  let res = []
  let gridData = []
  if(dataItem != "" && dataItem != null && dataItem.length !=0){
    console.log(trendValue.list.list)
    let legendData = [], xAxisData = [], seriesData = [], pointValue1 = [], pointValue2 = []
    legendData.push("流量(GB)")
    legendData.push("流量占比(%)")
    //yAxisName.push("流量(GB)")
    for (let i = 0; i < dataItem.length; i++) {
      let item = ""
      item = dataItem[i]
      xAxisData.push(item.TIME_ID)
      pointValue1.push(item.FLOW)
      if(item.USER_COUNT == null){
        pointValue2.push(1)
      }else{
        pointValue2.push(item.USER_COUNT)
      }
    }

    if (pointValue1.length>0 || pointValue2.length>0) {
      seriesData.push(pointValue1)
      seriesData.push(pointValue2)

      res.push({"legendData":legendData,"xAxisData":xAxisData,"seriesData":seriesData})
    }
  }

  return (
    <div>
      <div>
        <TrendBerLineEcharts dataSource={res}/>
      </div>
    </div>
  )
}


TrendAnalysis.propTypes = {
  post: PropTypes.object,
  trendValue: PropTypes.object,
  dispatch: PropTypes.func,
}

export default connect(({ trendValue }) => ({ trendValue }))(TrendAnalysis)

