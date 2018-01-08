import React from 'react'
import Echarts from 'echarts/lib/echarts' // 引入 ECharts 主模块
import 'echarts/lib/component/tooltip' // 引入提示框
import 'echarts/lib/component/toolbox' // 引入工具箱
import 'echarts/lib/component/title' // 引入标题
import 'echarts/lib/component/legend' // 引入图例
import 'echarts/lib/chart/pie' // 引入饼图
import 'echarts/lib/chart/bar'
import './hottIndexGrid.less'
import { Icon, Table, Input, Button   } from 'antd'
import ReactDOM from 'react-dom'
const Search = Input.Search;

class HottIndexGrid extends React.Component {
  componentDidMount() {
    // // 基于准备好的dom，初始化echarts实例
    // let myChart = Echarts.init(this.ref.echarts_div1);
    // let option = this.getOption(this.props.dataSource);
    // myChart.setOption(option);// 绘制图表
  }
  expro_excel = (e) =>  {
    alert("12345")
  }

  render() {
    let gridData = this.props.dataSource
    let grid = []
    let pagination = []
    let columns = ''

    if(gridData != "" && gridData != null && gridData != undefined){
      let datas = gridData;
      console.log(datas);
      columns = datas.colNames;
      grid = datas.data;
      pagination = {
        total: datas.total,//dataSource.total,
        defaultCurrent: datas.defaultCurrent,
        pageSize: datas.pageSize,
        onChange: (current, pageSize) => {
          this.setState({page:current,size:pageSize});
          this.loadData(current,pageSize);
        },
        showTotal:(total, range) => {
          return (
            <span>{range[0]}-{range[1]}&nbsp;&nbsp;共 <i>{total}</i> 条数据</span>
          )
        },
        formatInfo:(total, activePage) => {
          return (
            <span >共 <i>{total}</i> 条数据</span>
          )
        },
      }
    }
    return (
      <div className="p-e-div">
        <div className="p-e-div-1">
          <div className="p-e-div-div">
            <h5 className="fl"> <Icon type="appstore" style={{ fontSize: 22,color: '#0085d0'}}/>&nbsp;&nbsp;热门终端
            </h5>
          </div>
          <div className="p-e-div-div2"><Input placeholder="请输入终端品牌" /></div>
          <div className="p-e-div-div3">&nbsp;&nbsp;<Button type="primary">查询</Button></div>
          <div className="p_di_right">
            <span>
              <a className="fileExcel" onClick={this.expro_excel.bind(this)} title="导出">
                <Icon type="file-excel" style={{ fontSize: 16, color: '#08c'}} />
              </a>
            </span>
          </div>
        </div>
        <div className="p-e-div-2">
          <div className="p-e-div-2-1">
            <div ref="gridTable" className="table-div2" style={{width: "100%", height: "350px", display: "inline"}}>
              <br/>
              <Table  dataSource={grid} columns={columns} pagination={pagination} scroll={{x:2300}}/>
            </div>
          </div>
         {/* <div ref="cardBody" style={{display: "inline"}}>
            <div ref="noDatas" className="fa-file-excel-o">暂无数据</div>
            <div className="pie_cheat" style={{width: "100%", height: "350px"}} ref="echarts_div"></div>
          </div>*/}
          <div className="clear"></div>
        </div>
      </div>
    )
  }
}

export default HottIndexGrid
