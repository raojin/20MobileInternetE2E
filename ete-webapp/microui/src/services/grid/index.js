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

  if(grid.list.length!=0){
    let dataItem = grid.list.list;
    for (let i = 0; i < dataItem.length; i++) {
      data.push({key: i+1,time: dataItem[i].time,area:dataItem[i].selName,suc:dataItem[i].flow,count:dataItem[i].user,err_count: dataItem[i].userZb});
    }
    console.log(data)
  }

  const rankData={
    colNames:[{title: '时间', dataIndex: 'time',key: 'time',width:100},
      {title: '地市',dataIndex: 'area',key: 'area',width:100, render:text =>
          <a>{text}</a>,
      },
      {title: '激活成功率(%)',dataIndex: 'suc',key: 'suc',width:100},
      {title: '信令总次数(次)',dataIndex: 'count',key: 'count',width:100},
      {title: '错误次数(次)',dataIndex: 'err_count',key: 'err_count',width:100},
    ],
    pageSize:5,
    total: 11,
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

