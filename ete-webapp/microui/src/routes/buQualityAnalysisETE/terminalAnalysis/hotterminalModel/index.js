import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'dva'
import ModelBerEcharts from './modelBerEcharts'

const ModelAnalysis = ({ modelValue }) => {
  let dataItem = modelValue.list.list
  let res = []
  let gridData = []
  if(dataItem != "" && dataItem != null && dataItem.length !=0){
    console.log(modelValue.list.list)
    let legendData = [], xAxisData = [], seriesData = [], pointValue1 = [], pointValue2 = []
    legendData.push("日均流量(GB)")
    //legendData.push("流量占比(%)")
    //yAxisName.push("流量(GB)")
    for (let i = 0; i < dataItem.length; i++) {
      let item = ""
      item = dataItem[i]
      xAxisData.push(item.NAME)
      pointValue1.push(item.FLOW)
      /*if(item.USER_COUNT == null){
        pointValue2.push(1)
      }else{
        pointValue2.push(item.USER_COUNT)
      }*/
    }

    if (pointValue1.length>0 ) {
      seriesData.push(pointValue1)

      res.push({"legendData":legendData,"xAxisData":xAxisData,"seriesData":seriesData})
    }
  }

  return (
    <div>
      <div>
        <ModelBerEcharts dataSource={res}/>
      </div>
    </div>
  )
}


ModelAnalysis.propTypes = {
  post: PropTypes.object,
  modelValue: PropTypes.object,
  dispatch: PropTypes.func,
}

export default connect(({ modelValue }) => ({ modelValue }))(ModelAnalysis)

