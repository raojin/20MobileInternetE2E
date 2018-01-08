import React from 'react'
import Echarts from 'echarts/lib/echarts' // 引入 ECharts 主模块
import 'echarts/lib/component/tooltip' // 引入提示框
import 'echarts/lib/component/toolbox' // 引入工具箱
import 'echarts/lib/component/title' // 引入标题
import 'echarts/lib/component/legend' // 引入图例
import 'echarts/lib/chart/line' // 引入饼图
import 'echarts/lib/chart/bar'
import './trendBerLineEcharts.less'
import { Icon } from 'antd'
import ReactDOM from 'react-dom'

class IndexBerLineEcharts extends React.Component {

  getOption(data){
    if(data != null && data != "" && data.length != 0){
      var noDatas= ReactDOM.findDOMNode(this.refs.noDatas);
      noDatas.style.display = 'none';
    }
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
        data:/*values.seriesName*/['流量(GB)','用户数(万)']
      },
      grid :{
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
        /*axisLabel: {
          textStyle: {
            color: '#000',//坐标值的具体的颜色
          },
        },*/
        splitLine:{show: true},//去除网格线
        type : 'category',
        boundaryGap : true,
        data :  data.xAxisData,/*['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],*/
        axisLabel: {
          textStyle: {
            color: '#000',//坐标值的具体的颜色
          },
          formatter: function(value){
            var re = /2{1}[0-9]{3}[-]{1}[0-1]{1}[0-9]{1}[-]{1}[0-3]{1}[0-9]{1}/;
            var re2 = /2{1}[0-9]{3}[-]{1}[0-1]{1}[0-9]{1}[-]{1}[0-3]{1}[0-9]{1}[ ]{1}[0-2]{1}[0-9]{1}:00/;
            /*if (re2.test(value)){
              var _arr = value.split(' ');
              return _arr[1];
            }else if (re.test(value)){
              var _arr = value.split('-');
              return _arr[0]+"\n"+_arr[1]+"-"+_arr[2];
            }*/
            return value;
          },
        },
      }],
      yAxis : [{
        splitLine:{show: true},//去除网格线
        type : 'value',
        name : /*values.seriesName[0]*/'流量(GB)',
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
      },{
        splitLine:{show: true},//去除网格线
        type : 'value',
        scale : true,
        //min : _min,
        name : /*values.seriesName[1]*/'用户数(万)',
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
        name:/*values.seriesName[0],*/'流量(GB)',
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
        /*itemStyle: {
                  normal: {
                      color: function(params) {
                          // build a color map as your need.
                          return zrColor.getColor(params.dataIndex);
                      }
                  }
              },*/
        data:data.seriesData[0],/*[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],*/
      },{
        name:/*values.seriesName[1],*/'用户数(万)',
        type:'line',
        yAxisIndex: 1,
        data:data.seriesData[1],/*[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2],*/
        itemStyle : {
          normal: {
            label : {show: false, position: 'top',
              textStyle : {
                fontWeight : 'bold',
              },
            },
          },
        },
       // markLine : _fazhi
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
            <h5 className="fl"> <Icon type="line-chart" style={{ fontSize: 22,color: '#0085d0'}}/>&nbsp;&nbsp;趋势图
            </h5>
          </div>
          <div className="di_right" class="active">
            <div className="clear"></div>
          </div>
        </div>
        <div className="p-e-div-2">
          <div ref="cardBody" style={{display: "inline"}}>
            <div ref="noDatas" className="fa-file-excel-o">暂无数据</div>
            <div className="pie_cheat" style={{width: "100%", height: "350px"}} ref="echarts_div"></div>
          </div>
          <div className="clear"></div>
        </div>
      </div>
    )
  }
}

export default IndexBerLineEcharts
