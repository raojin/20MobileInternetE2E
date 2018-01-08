import React from 'react'
import PropTypes from 'prop-types'
import { prefix } from 'utils/config'
import { connect } from 'dva'
import { Page } from 'components'
import { getSystemConfig } from 'utils/sysconfig'
import { routerRedux } from 'dva/router'
import IndexGrid from './indexGrid'


const grid = function ({grid}) {

  let data=[];
  //data.push({key: 1,TIME_ID:11111,AREA_NAME:{AREA_NAME:"南昌",AREA_ID:"851"},SUCCESS_RATE:99,TOTAL_NUMBER:54,ERROR_NUMBER:42});

  if(grid.list.length!=0){
    let dataItem = grid.list.list;
    for (let i = 0; i < dataItem.length; i++) {
      let area={AREA_ID:dataItem[i].AREA_ID,AREA_NAME:dataItem[i].AREA_NAME}
      data.push({key: i+1,TIME_ID: dataItem[i].TIME_ID,AREA_NAME:area,SUCCESS_RATE:dataItem[i].SUCCESS_RATE,TOTAL_NUMBER:dataItem[i].TOTAL_NUMBER,ERROR_NUMBER: dataItem[i].ERROR_NUMBER});
    }
  }
  let queryByArea=(area_id)=>{
  alert("地市Id为："+area_id);
  }
  const rankData={
    colNames:[{title: '时间', dataIndex: 'TIME_ID',key: 'TIME_ID',width:100},
      {title: '地市',dataIndex: 'AREA_NAME',key: 'AREA_NAME',width:100, render:area =>
          <span><a onClick={(e) => queryByArea(area.AREA_ID)}>{area.AREA_NAME}</a></span>
      },
      {title: '激活成功率(%)',dataIndex: 'SUCCESS_RATE',key: 'SUCCESS_RATE',width:100},
      {title: '信令总次数(次)',dataIndex: 'TOTAL_NUMBER',key: 'TOTAL_NUMBER',width:100},
      {title: '错误次数(次)',dataIndex: 'ERROR_NUMBER',key: 'ERROR_NUMBER',width:100}
    ],
    pageSize:10,
    total:data.length ,
    defaultCurrent:1,
    data:data

  };

  return (

      <div className="hide1">
        <IndexGrid dataSource={rankData}/>
      </div>

  )
}

grid.propTypes = {

  post: PropTypes.object,
  grid: PropTypes.object,
  dispatch: PropTypes.func,
}

export default connect(({grid}) => ({grid}))(grid)

