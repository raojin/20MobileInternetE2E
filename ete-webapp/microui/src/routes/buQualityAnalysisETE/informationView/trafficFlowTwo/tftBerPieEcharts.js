import React from 'react'
import Echarts from 'echarts/lib/echarts' // 引入 ECharts 主模块
import 'echarts/lib/component/tooltip' // 引入提示框
import 'echarts/lib/component/toolbox' // 引入工具箱
import 'echarts/lib/component/title' // 引入标题
import 'echarts/lib/component/legend' // 引入图例
import 'echarts/lib/chart/pie' // 引入饼图
import 'echarts/lib/chart/bar'
import './tftBerPieEcharts.less'
import { Icon, Table ,Select, Menu, Dropdown, Button, message } from 'antd'
import ReactDOM from 'react-dom'
import { queryOneData } from 'services/buQualityAnalysisETE/informationView/trafficFlowOne/oneDataServer'

class IndexBerPieEcharts extends React.Component {

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
      //color:['#62bdff','#c09ce8','#f5dc5c','#fd6500','#5db75d'],
      toolbox: {
        feature: {
          //dataView: { readOnly: false },
          restore: {},
          saveAsImage: {},
        },
      },
      legend: {
        show : true,
        //data: data.legendData//['流量(GB)','流量占比(%)']
        data:[
          {
            name:'流量(GB)',
            icon:'stack',
            textStyle:{
              fontSize:12,
              color:'#62bdff',
            },
          },
          {
            name:'流量占比(%)',
            icon:'pie',
            textStyle:{
              fontSize:12,
              color:'#c09ce8',
            },
          }
        ]
      },
      grid :{
        y : 40,
        y2 : 30,
      },
      xAxis : [{
        splitLine:{show: true},//去除网格线
        type : 'category',
        boundaryGap : true,
        data : data.xAxisData,//['网页浏览','视频','即时通信','游戏','应用商店'],
        //splitArea : {show : true}//保留网格区域

      }],
      yAxis : [{
        splitLine:{show: true},//去除网格线
        type : 'value',
        name : data.legendData[0],//'流量(GB)',
        splitArea : {show : true},//保留网格区域
        axisLine:{
          textStyle: {
            color: '#62bdff',
          }
        },
      }],
      series : [{
        name: data.legendData[0],//'流量(GB)',
        type: 'bar',
        itemStyle: {
          emphasis: {
            barBorderRadius: 30,
          },
          normal: {
            barBorderRadius:[5],
            color: function(params) {
              // build a color map as your need.
              var colorList = ['#62bdff','#c09ce8','#f5dc5c','#fd6500','#5db75d'];
              return colorList[params.dataIndex]
            },
            label: {
              show: false,
              position: 'top',
            }
          },
        },
        barCategoryGap: '50%',
        data: data.seriesData[0]//[135.0, 100.9, 56.0, 23.2, 5.6]
      },{
        name:'流量占比(%)',
        type:'pie',
        tooltip : {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        itemStyle: {
          normal: {
            color: function(params) {
              // build a color map as your need.
              var colorList = ['#62bdff','#c09ce8','#f5dc5c','#fd6500','#5db75d'];
              return colorList[params.dataIndex]
            },
            label: {
              show: true,
            }
          },
        },
        center: ["70%",120],
        radius : [0, 60],
        data: data.seriesData[1]//[{value:135.0, name:'网页浏览'},{value:100.9, name:'视频'},{value:56.0, name:'即时通信'},{value:5.6, name:'应用商店'}]
      }],
    }
    return option
  }

  componentDidMount() {
    // // 基于准备好的dom，初始化echarts实例
    // let myChart = Echarts.init(this.ref.echarts_div1);
    // let option = this.getOption(this.props.dataSource);
    // myChart.setOption(option);// 绘制图表

  }

  _areaturnToChart(e) {
    var cardBody = ReactDOM.findDOMNode(this.refs.cardBody);
    var gridTable = ReactDOM.findDOMNode(this.refs.gridTable);
    if (cardBody.style.display === 'none') {
      cardBody.style.display = 'block';
      gridTable.style.display = 'none';
    }
  }
  constructor(props) {
    super(props);
    this._areaturnToGrid = this._areaturnToGrid.bind(this)
  }
  _areaturnToGrid(e) {
    var cardBody = ReactDOM.findDOMNode(this.refs.cardBody);
    var gridTable = ReactDOM.findDOMNode(this.refs.gridTable);
    if (gridTable.style.display === 'none') {
      cardBody.style.display = 'none';
      gridTable.style.display = 'block';
    }
  }
  _areaturnToMore = (e) =>  {
    console.log(this);
    alert("开发者正在努力开发中！");
  }
  handleProvinceChange(value) {
    alert(value)
  }


  render() {
    // 基于准备好的dom，初始化echarts实例
    let param={flag:'one',timeId:'20171210',areaId:'851',level:'day',netType:'4G',queryType:'999999,1,7,15,5,8'}
    let  provinceD = []
    let provinceOptions =''
    queryOneData(param).then((list)=>{
      if(list!=null && list!=""){
        let dataItem=list.list;
        for (let i = 0; i < dataItem.length; i++) {
          let item = ""
          item = dataItem[i]
          provinceD.push(item.selName)
        }
      }
    })
    //let provinceOptions = provinceData.map(province => <Option key={province}>{province}</Option>);
    //const provinceData = ['网页浏览','视频','即时通信','游戏','111']
    const provinceData = provinceD
    console.log(provinceData)
    provinceOptions = provinceData.map(province => <Option key={province}>{province}</Option>);
    if(this.refs.echarts_div){
      let myChart = Echarts.init(this.refs.echarts_div);
      let option = this.getOption(this.props.dataSource);
      myChart.setOption(option);// 绘制图表
    }
    let gridData = this.props.dataSource[0]
    let grid = []
    let pagination = []
    let columns = []
    if(gridData != "" && gridData != null && gridData != undefined){
      let datas = gridData.rankData;
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
          );
        },
        formatInfo:(total, activePage) => {
          return (
            <span >共 <i>{total}</i> 条数据</span>
          );
        }
      }
    }

    return (
      <div className="p-e-div">
        <div className="p-e-div-1">
          <div className="di_left">
            <h5 className="fl"> <Icon type="caret-right" style={{ fontSize: 16}}/>&nbsp;&nbsp;一级业务流量TOP
            </h5>
          </div>
          <div className="di_right" class="active">
            <span>
              <Select defaultValue={provinceData[0]} style={{ width: 120 }} onChange={this.handleProvinceChange}>
                {provinceOptions}
              </Select>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <a className="areaturnToChart" onClick={this._areaturnToChart.bind(this)}>
                <Icon type="area-chart" style={{ fontSize: 16, color: '#08c'}} />
              </a>&nbsp;&nbsp;&nbsp;&nbsp;
              <a className="areaturnToGrid" onClick={this._areaturnToGrid}>
                <Icon type="bars" style={{ fontSize: 18, color: '#08c'}} />
              </a>&nbsp;&nbsp;&nbsp;&nbsp;
              <a style={{ fontSize: 13}} onClick={this._areaturnToMore}>
                更多
                <Icon type="double-right" style={{ fontSize: 10, color: '#08c'}}/>
                <i className="fa fa-angle-double-right" name="更多" title="更多"></i>
              </a>
            </span>
            <div className="clear"></div>
          </div>
        </div>
        <div className="p-e-div-2">
          <div ref="gridTable" className="table-div2" style={{width: "100%", height: "350px", display: "none"}}>
            <Table  dataSource={grid} columns={columns} pagination={pagination} />
          </div>
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

export default IndexBerPieEcharts
