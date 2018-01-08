import React from 'react'
import PropTypes from 'prop-types'
import { prefix } from 'utils/config'
import { connect } from 'dva'
import { Page } from 'components'
import { getSystemConfig } from 'utils/sysconfig'
import { Tooltip,Icon, Select,Input,Button  } from 'antd'
import IndexGrid from './indexGrid'
import IndexBar from './bar'
import IndexLine from './line'
import ReactDOM from 'react-dom'
import './index.less'

const twoBusinessAnalysis = function ({twoBusinessAnalysis}) {
  let data=[];

  if(twoBusinessAnalysis.list.length!=0){
    let dataItem = twoBusinessAnalysis.list.list;
    for (let i = 0; i < dataItem.length; i++) {
      data.push({key: i+1,time_id: dataItem[i].time_id,area_name:dataItem[i].area_name,
        app_type_name:dataItem[i].app_type_name,
        flow:dataItem[i].flow,user_count: dataItem[i].user_count,
        flow_zb:dataItem[i].flow_zb,user_count_zb: dataItem[i].user_count_zb,
        app_type_id:dataItem[i].app_type_id,area_id:dataItem[i].area_id
      });
    }
  }

  const rankData={
    colNames:[{title: '时间', dataIndex: 'time_id',key: 'time_id',width:100},
      {title: '地市',dataIndex: 'area_name',key: 'area_name',width:100},
      {title: '业务类型',dataIndex: 'app_type_name',key: 'app_type_name',width:100},
      {title: '流量(GB)',dataIndex: 'flow',key: 'flow',width:150},
      {title: '用户数(万)',dataIndex: 'user_count',key: 'user_count',width:100},
      {title: '流量占比(%)',dataIndex: 'flow_zb',key: 'flow_zb',width:150},
      {title: '用户数占比(%)',dataIndex: 'user_count_zb',key: 'user_count_zb',width:150},
    ],
    pageSize:10,
    total:twoBusinessAnalysis.list.total ,
    defaultCurrent:twoBusinessAnalysis.list.pageNo,
    data:data
  };


  class Index extends React.Component{

    constructor(props) {
      super(props);
      this.app_type_id=1;
      this.index='flow';
      this.fz='';
      this.top=10;
    }
    expro_excel = (e) =>  {
      alert("daochu")
    }
    change = (e) =>  {
      //alert(e)
      let grid= ReactDOM.findDOMNode(this.refs.grid);
      let line= ReactDOM.findDOMNode(this.refs.line);
      let bar= ReactDOM.findDOMNode(this.refs.bar);
      grid.style.display="none";
      line.style.display="none";
      bar.style.display="none";
      if(e=="grid"){
        grid.style.display="block";
      }
      if(e=="line"){
        line.style.display="block";
      }
      if(e=="bar"){
        bar.style.display="block";
      }
    }
    app_change = (value) =>  {
      this.app_type_id=value;
    }
    index_change = (value) =>  {
      this.index=value;
    }
    app_click = (e) =>  {
      this.fz=document.getElementById("fz").value;
      this.top=document.getElementById("top").value;
      let prams={app_type_id:this.app_type_id,index:this.index,fz:this.fz,top:this.top}
      console.log(prams)
    }
    render() {
      return (
        <div className="p-e-div">
          <div className="p-e-div-1">
            <div className="p-e-div-div">
              <h5 className="fl"> <Icon type="bars" style={{ fontSize: 24,color: '#0085d0'}}/>&nbsp;&nbsp;二级业务
              </h5>
            </div>
            <div className="p-e-div-div2">
              <Select  defaultValue="1" style={{ width: 100,'margin-left':20}} onChange={(value) => this.app_change(value)}>
                <Select.Option value="1">即时通信</Select.Option>
                <Select.Option value="7">应用下载</Select.Option>
                <Select.Option value="15">网页浏览</Select.Option>
                <Select.Option value="5">视频</Select.Option>
                <Select.Option value="8">游戏</Select.Option>
              </Select>
              <Select defaultValue="flow" style={{ width: 100,'margin-left':20}} onChange={(value) => this.index_change(value)}>
                <Select.Option value="flow">流量(GB)</Select.Option>
                <Select.Option value="user">用户数(万)</Select.Option>
              </Select>
              <span style={{'margin-left':20,width:'100px !important'}}> 门限阀值:</span>
              <Input id='fz' style={{'margin-left':5,width:'100px'}}/>
              <span style={{'margin-left':20,width:'100px !important'}}> top:</span>
              <Input id='top' style={{'margin-left':5,width:'100px'}}/>
              <Button type="primary"  style={{'margin-left':20}} onClick={(e) => this.app_click(e)}>刷新</Button>
            </div>
            <div className="p-e-div-div3"></div>
            <div className="p_di_right">
              <span>
                <a className="fileExcel" onClick={(e) => this.change("grid")} title="切换柱图">
                  <Icon type="bars" style={{ fontSize: 16, color: '#08c'}} />
                </a>&nbsp;&nbsp;
                <a className="areaturnToGrid" onClick={(e) => this.change("line")} title="切换柱线图">
                  <Icon type="line-chart" style={{ fontSize: 16, color: '#08c'}} />
                </a>&nbsp;&nbsp;
                <a className="areaturnToGrid" onClick={(e) => this.change("bar")} title="切换">
                  <Icon type="bar-chart" style={{ fontSize: 16, color: '#08c'}} />
                </a>&nbsp;&nbsp;
                <a className="fr" onClick={(e) => this.expro_excel()} title="导出">
                  <Icon type="file-excel" style={{ fontSize: 16, color: '#08c'}} />
                </a>
              </span>
            </div>
          </div>
          <div className="p-e-div-2">
            <div className="p-e-div-2-1" ref="grid" style={{display: "block"}}><IndexGrid dataSource={rankData}/></div>
            <div className="p-e-div-2-1" ref="bar" style={{display: "none"}}><IndexBar dataSource={rankData}></IndexBar></div>
            <div className="p-e-div-2-1" ref="line" style={{display: "none"}}><IndexLine  dataSource={rankData}></IndexLine></div>

            <div className="clear"></div>
          </div>
        </div>
      )
    }
  }
  return (
    <Index></Index>
  );
}

twoBusinessAnalysis.propTypes = {

  post: PropTypes.object,
  twoBusinessAnalysis: PropTypes.object,
  dispatch: PropTypes.func,
}

export default connect(({twoBusinessAnalysis}) => ({twoBusinessAnalysis}))(twoBusinessAnalysis)

