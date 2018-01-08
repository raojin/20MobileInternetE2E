import React from 'react'
import PropTypes from 'prop-types'
//import Echarts from 'echarts' // 完整的ECharts
import Echarts from 'echarts/lib/echarts' // 引入 ECharts 主模块
import 'echarts/lib/component/tooltip' // 引入提示框
import 'echarts/lib/component/toolbox' // 引入工具箱
import 'echarts/lib/component/title' // 引入标题
import 'echarts/lib/component/legend' // 引入图例
import 'echarts/lib/chart/bar' // 引入
import './bar.less'


const IndexBar = ({ dataSource }) => {
  class IndexBar extends React.Component {
    constructor(props) {
      super(props);
      this.ref = {echarts_div1: null};
    }
    getOption() {
      let x=[];
      let y=[];
      let list=dataSource.data;
      for(let n=0;n<list.length;n++){
        x.push(list[n].app_type_name);
        y.push(list[n].flow)
      }
      let option = {
        color: ['#3398DB'],
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
          data:['流量(MB)']
        },
        xAxis: {
          data:x
        },
        yAxis: [{
          type : 'value',
          name:['流量(MB)']
          // '流量(GB)'
        }],
        series: [{
          name: '流量(MB)',
          type : 'bar',
          //barCategoryGap : '100%',
          data: y
        }]
      }
      return option;
    }
    componentDidMount() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = Echarts.init(this.ref.echarts_div1);
      let option = this.getOption();
      myChart.setOption(option);// 绘制图表
    }

    render() {
      return (
        <div className="con"  >
         <div className='table-div2'>
           <div className="bar_cheat"   ref={node1 => (this.ref.echarts_div1 = node1)}>
           </div>
          <div className="clear"></div>
         </div>
        </div>
      );
    }
  }
  return (
    <IndexBar></IndexBar>
  );
}

IndexBar.propTypes = {
  dataSource: PropTypes.object,
}

export default IndexBar

