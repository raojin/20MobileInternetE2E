import React from 'react'
import PropTypes from 'prop-types'
//import Echarts from 'echarts' // 完整的ECharts
import Echarts from 'echarts/lib/echarts' // 引入 ECharts 主模块
import 'echarts/lib/component/tooltip' // 引入提示框
import 'echarts/lib/component/toolbox' // 引入工具箱
import 'echarts/lib/component/title' // 引入标题
import 'echarts/lib/component/legend' // 引入图例
import 'echarts/lib/chart/bar' // 引入柱状图
import 'echarts/lib/chart/line' // 引入折线图
import "./indexEcharts.less";

class IndexEcharts extends React.Component {
  constructor(props) {
    super(props);
    this.ref = { echarts_div : null};
  }
  getOption(data){
    data = data || {data:[]};
    let title = data.title;
    let legend = data.yName;
    !Array.isArray(legend) && (legend = [legend]);
    let dataX = [], series = [];

    data.data.forEach(item => {
      dataX.push(item[data.xProp]);
    })
    data.yProp.forEach((y, i) => {
      series.push({
        name: legend[i],
        type: 'line',
        smooth: true,
        areaStyle: {normal: {}},
        data: data.data.map(item => {return item[y];})
      })
    })
    let option = {
      title: { text: title },
      toolbox: {
        feature: {
          restore: {},
          saveAsImage: {}
        }
      },
      tooltip : {
        trigger: 'axis',
      },
      legend: {
        data: legend
      },
      xAxis: {
        data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
      },
      yAxis: {},
      series: [{
        name: '销量',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20]
      }]
    }
    return option;
  }
  componentDidMount() {
    // 基于准备好的dom，初始化echarts实例
    let myChart = Echarts.init(this.ref.echarts_div);
    let option = this.getOption(this.props.dataSource);
    myChart.setOption(option);// 绘制图表
  }
  render() {
    return (
      <div className="p-e-div">
        <div style={{width: "100%", height: "400px"}} ref={node => (this.ref.echarts_div = node)}></div>
        <div className="clear"></div>
      </div>
    );
  }
}

export default IndexEcharts
