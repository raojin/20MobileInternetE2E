import React from 'react'
import PropTypes from 'prop-types'
import { prefix } from 'utils/config'
import { connect } from 'dva'
import { Page } from 'components'
import { getSystemConfig } from 'utils/sysconfig'
import InfoBlock from './infoBlock'
import IndexBlock from './indexBlock'
import RankTable from './rankTable'
import IndexTab from './indexTab'
import IndexEcharts from './indexEcharts'

const Model = function ({ location, dispatch, user, loading }) {
  //系统数据获取数据
  getSystemConfig(data => {

  })

  const data1 = {};
  data1.title = '总用户数';
  data1.cnt = user.list.TOTAL_USER_CNT;
  data1.tb = user.list.TOTAL_USER_CNT_TB;
  data1.hb = user.list.TOTAL_USER_CNT_HB;

  const data2 = {};
  data2.title = '质差用户数';
  data2.cnt = user.list.BAD_USER_CNT;
  data2.tb = user.list.BAD_USER_CNT_TB;
  data2.hb = user.list.BAD_USER_CNT_HB;

  const data3 = {
    title : '用户数',
    title_1 : '激活用户数(户)',
    title_2 : '活跃用户数(户)'
  };
  data3.cnt_1 = user.list.ACTIVATED_USER_CNT;
  data3.tb_1 = user.list.ACTIVATED_USER_CNT_TB;
  data3.hb_1 = user.list.ACTIVATED_USER_CNT_HB;
  data3.cnt_2 = user.list.ACTIVE_USER_CNT;
  data3.tb_2 = user.list.ACTIVE_USER_CNT_TB;
  data3.hb_2 = user.list.ACTIVE_USER_CNT_HB;

  const data4 = {
    title : '业务量',
    title_1 : '流量(GB)',
    title_2 : '地址利用率(%)'
  };
  data4.cnt_1 = user.list.FLOW;
  data4.tb_1 = user.list.FLOW_TB;
  data4.hb_1 = user.list.FLOW_HB;
  data4.cnt_2 = user.list.ADDR_USER_RATE;
  data4.tb_2 = user.list.ADDR_USER_RATE_TB;
  data4.hb_2 = user.list.ADDR_USER_RATE_HB;


  const rankData1 = {
    title : 'TOP客户',
    columns : [
      {cname : 'APN' , ename : 'DIM_NAME'},
      {cname : '用户数(人)' , ename : 'DIM_VALUE1'},
      {cname : '流量(GB)' , ename : 'DIM_VALUE2'}
    ]
  };
  rankData1.data = user.list.list;

  const rankData2 = {
    title : 'TOP小区',
    columns : [
      {cname : '小区名称' , ename : 'DIM_NAME'},
      {cname : '活跃用户数(人)' , ename : 'DIM_VALUE1'},
    ]
  };
  rankData2.data = user.list.list;

  const rankData3 = {
    title : 'TOP APN',
    columns : [
      {cname : 'APN' , ename : 'DIM_NAME'},
      {cname : '活跃用户数(人)' , ename : 'DIM_VALUE1'}
    ]
  };
  rankData3.data = user.list.list;

  const rankData4 = {
    title : 'TOP网元',
    columns : [
      {cname : '网元IP' , ename : 'DIM_NAME'},
      {cname : '活跃用户数(人)' , ename : 'DIM_VALUE1'},
    ]
  };
  rankData4.data = user.list.list;

  const tabData = [
      {title : '业务类', data : [
        {cname : 'APN业务接入成功率(%)', ename : 'SERVICE_ACCESS_SUCC_RATE', value : user.list.SERVICE_ACCESS_SUCC_RATE, hb : user.list.SERVICE_ACCESS_SUCC_RATE_HB, tb : user.list.SERVICE_ACCESS_SUCC_RATE_TB},
        {cname : 'APN业务接入时长(ms)', ename : 'SERVICE_ACCESS_DURATION', value : user.list.SERVICE_ACCESS_DURATION, hb : user.list.SERVICE_ACCESS_DURATION_HB, tb : user.list.SERVICE_ACCESS_DURATION_TB},
        {cname : 'APN业务传输速率(kbps)', ename : 'SERVICE_TRANSFER_RATE', value : user.list.SERVICE_TRANSFER_RATE, hb : user.list.SERVICE_TRANSFER_RATE_HB, tb : user.list.SERVICE_TRANSFER_RATE_TB},
        {cname : 'TCP重传率(%)', ename : 'TCP_RETRAN_RATE', value : user.list.TCP_RETRAN_RATE, hb : user.list.TCP_RETRAN_RATE_HB, tb : user.list.TCP_RETRAN_RATE_TB},
        {cname : 'TCP乱序率(%)', ename : 'TCP_OUTOFORDER_RATE', value : user.list.TCP_OUTOFORDER_RATE, hb : user.list.TCP_OUTOFORDER_RATE_HB, tb : user.list.TCP_OUTOFORDER_RATE_TB}
      ]},
      {title : '网络类', data : [
        {cname : 'APN网络接入成功率(%)', ename : 'NETWORK_ACCESS_SUCC_RATE', value : user.list.NETWORK_ACCESS_SUCC_RATE, hb : user.list.NETWORK_ACCESS_SUCC_RATE_HB, tb : user.list.NETWORK_ACCESS_SUCC_RATE_TB},
        {cname : 'APN网络接入时延(ms)', ename : 'NETWORK_ACCESS_DURATION', value : user.list.NETWORK_ACCESS_DURATION, hb : user.list.NETWORK_ACCESS_DURATION_HB, tb : user.list.NETWORK_ACCESS_DURATION_TB},
        {cname : 'APN网络切换成功率(%)', ename : 'NETWORK_SWITCH_SUCC_RATE', value : user.list.NETWORK_SWITCH_SUCC_RATE, hb : user.list.NETWORK_SWITCH_SUCC_RATE_HB, tb : user.list.NETWORK_SWITCH_SUCC_RATE_TB},
        {cname : 'APN业务传输成功率(%)', ename : 'SERVICE_TRANSFER_SUCC_RATE', value : user.list.SERVICE_TRANSFER_SUCC_RATE, hb : user.list.SERVICE_TRANSFER_SUCC_RATE_HB, tb : user.list.SERVICE_TRANSFER_SUCC_RATE_TB},
      ]}
    ];

  let echartsData = {
    title : 'APN业务接入成功率(%)',
    xProp : 'TIME_ID',
    yProp : ['SERVICE_ACCESS_SUCC_RATE'],
    yName : ['APN业务接入成功率(%)'],
    data : []
  }
  echartsData.data = [];//user.list1.list;


  return (
    <Page loading={loading.models.model}>
      <div className="hide1">
        <InfoBlock dataSource={data1}/>
        <InfoBlock dataSource={data2} type="bad"/>
      </div>
      <div className="hide1">
        <IndexBlock dataSource={data3}/>
        <IndexBlock dataSource={data4} type="business"/>
      </div>
      <div className="hide1">
        <RankTable dataSource={rankData1}/>
        <RankTable dataSource={rankData2} color="#85c3fc"/>
        <RankTable dataSource={rankData3}/>
        <RankTable dataSource={rankData4} color="#85c3fc"/>
      </div>
      <div className="hide1">
        <IndexTab dataSource={tabData}/>
      </div>
      <div className="hide1">
        <IndexEcharts dataSource={echartsData}/>
      </div>
    </Page>
  )
}

Model.propTypes = {
  loading: PropTypes.object,
}

export default connect(({user, loading}) => ({user, loading}))(Model)

