import React from 'react'
import Echarts from 'echarts/lib/echarts' // 引入 ECharts 主模块
import 'echarts/lib/component/tooltip' // 引入提示框
import 'echarts/lib/component/toolbox' // 引入工具箱
import 'echarts/lib/component/title' // 引入标题
import 'echarts/lib/component/legend' // 引入图例
import 'echarts/lib/chart/line' // 引入饼图
import 'echarts/lib/chart/bar'
import './modelBerEcharts.less'
import { Icon } from 'antd'
import ReactDOM from 'react-dom'

class ModelBerEcharts extends React.Component {

  getOption(data){
    /*if(data != null && data != "" && data.length != 0){
      var noDatas= ReactDOM.findDOMNode(this.refs.noDatas);
      noDatas.style.display = 'none';
    }*/
    data = data[0]
    let option = {
      tooltip : {//提示内容
        trigger : 'item',
        formatter : '{b}<br/>{a}:{c}'
      },
      color:['#62bdff','#c09ce8','#f5dc5c','#fd6500','#5db75d'],
      toolbox: {
        feature: {
          //dataView: { readOnly: false },
          restore: {},
          saveAsImage: {},
          textStyle: {
            color: '#48b',
            backgroundColor: '#48b',
            borderColor: '#48b',
          },
        },
      },
      legend: {
        data:data.seriesName/*['日均流量(GB)']*/
      },
      grid :{
        x: 40,
        x2: 100,
        y2: 150,
        left: '7%',
        right: '7%',
        top: '15%',
        bottom: '15%',
      },
      xAxis : [{
        //设置轴线的属性
        axisLine:{
          lineStyle:{
            color:'#008acd',
            width:'2'
          },
        },
        splitLine:{show: true},//去除网格线
        type : 'category',
        boundaryGap : true,
        data :  data.xAxisData,/*['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],*/
        axisLabel: {
          interval:0,
          rotate:45,//倾斜度 -90 至 90 默认为0
          margin:2,
          textStyle: {
            color: '#000',//坐标值的具体的颜色
          },
        },
      }],
      yAxis : [{
        splitLine:{show: true},//去除网格线
        type : 'value',
        name : data.seriesName,/*'流量(GB)',*/
        splitArea : {show : true},//保留网格区域
        //设置轴线的属性
        axisLine:{
          lineStyle:{
            color:'#008acd',
            width:'2'
          },
        },
        axisLabel: {
          textStyle: {
            color: '#000',//坐标值的具体的颜色
          },
        },
      }],
      series : [{
        name:data.seriesName,/*'流量(GB)',*/
        type:'bar',
        barCategoryGap: '50%',
        itemStyle: {
          emphasis: {
            barBorderRadius: 30,
          },
          normal: {
            barBorderRadius:[5],
          },
        },
        data:data.seriesData[0],/*[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],*/
      }]
    }
    return option
  }

  componentDidMount() {
    // // 基于准备好的dom，初始化echarts实例
    // let myChart = Echarts.init(this.ref.echarts_div1);
    // let option = this.getOption(this.props.dataSource);
    // myChart.setOption(option);// 绘制图表

  }
  render() {
    // 基于准备好的dom，初始化echarts实例
    if(this.refs.echarts_div){
      let myChart = Echarts.init(this.refs.echarts_div);
      let option = this.getOption(this.props.dataSource);
      myChart.setOption(option);// 绘制图表
    }
    return (
      <div className="p-e-div">
        <div className="p-e-div-1">
          <div className="di_left">
            <h5 className="fl"> <Icon type="bars" style={{ fontSize: 22,color: '#0085d0'}}/>&nbsp;&nbsp;终端型号TOP10
            </h5>
          </div>
          <div className="di_right" class="active">
            <div className="clear"></div>
          </div>
        </div>
        <div className="p-e-div-2">
          <div ref="cardBody" style={{display: "inline"}}>
            {/*<div ref="noDatas" className="fa-file-excel-o">暂无数据</div>*/}
            <div className="pie_cheat" style={{width: "100%", height: "350px"}} ref="echarts_div"></div>
          </div>
          <div className="clear"></div>
        </div>
      </div>
    )
  }
}

export default ModelBerEcharts
