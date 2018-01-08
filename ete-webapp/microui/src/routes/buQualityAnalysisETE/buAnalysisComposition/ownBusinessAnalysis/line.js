import { Tabs } from 'antd';
import Echarts from 'echarts/lib/echarts' // 引入 ECharts 主模块
import 'echarts/lib/component/tooltip' // 引入提示框
import 'echarts/lib/component/toolbox' // 引入工具箱
import 'echarts/lib/component/title' // 引入标题
import 'echarts/lib/component/legend' // 引入图例
import 'echarts/lib/chart/bar' // 引入
import 'echarts/lib/chart/line' // 引入
import './bar.less';
import { queryBussinessTrendList} from 'services/buQualityAnalysisETE/buAnalysisComposition/ownBusinessAnalysis/ownBusinessAnalysisServer';

const IndexLine = ({ dataSource }) => {
  const TabPane = Tabs.TabPane;
  class IndexLine extends React.Component {
    constructor(props) {
      super(props);
      this.ref = {
        echarts_div1: null,
        echarts_div2: null,
        echarts_div3: null,
        echarts_div4: null,
        echarts_div5: null,
        echarts_div6: null,
        echarts_div7: null,
        echarts_div8: null,
        echarts_div9: null,
        echarts_div10: null
      };
    }

    getOption(x,y1,y2) {


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
          data:['流量(MB)',"用户数(万)"]
        },
        xAxis: {
          data:x
        },
        yAxis: [{
          type : 'value',
          name:['流量(MB)']
          // '流量(GB)'
        },{
          type : 'value',
          name:['用户数(万)']
        }],
        series: [{
          name: '流量(MB)',
          type : 'bar',
          data: y1
        },{
          name: '用户数(万)',
          type : 'line',
          yAxisIndex: 1,//设置右侧的y轴
          data: y2
        }]
      }
      return option;
    }
    componentDidMount() {
      // 基于准备好的dom，初始化echarts实例
      // let x=["即时通信", "视频", "其他", "社交", "应用商店", "浏览器", "新闻资讯", "网络购物", "音频", "网络出行"];
      // let y1=[5, 20, 36, 10, 10, 20, 100, 160, 60, 72];
      // let y2=[15, 120, 36, 10, 10, 20, 100, 160, 60, 72];
      // let myChart = Echarts.init(this.ref.echarts_div1);
      // let option = this.getOption(x,y1,y2);
      // myChart.setOption(option);// 绘制图表
      let param={level:'hour',timeId:'201712011000',areaId:851,netType:'4G',app_type_id:0,app_type:'businessType'}
      let chart ;
      queryBussinessTrendList(param).then((list)=>{
        let x_=[];
        let y1_=[];
        let y2_=[];
        let data=list.list;
        for(let n=0;n<data.length;n++){
          x_.push(data[n].time_id);
          y1_.push(data[n].flow);
          y2_.push(data[n].user_count);
        }
        chart = Echarts.init(this.ref.echarts_div1)
        let option = this.getOption(x_,y1_,y2_);
        chart.setOption(option);// 绘制图表
      })
    }
    callback = (key) =>  {
      let app_type_id=dataSource.data[key-1].app_type_id;
      let app_type_name=dataSource.data[key-1].app_type_name;
      let area_id=dataSource.data[key-1].area_id;
      let param={level:'hour',timeId:'201712011000',areaId:area_id,netType:'4G',app_type_id:app_type_id,app_type:'businessType'}
      let chart ;
      queryBussinessTrendList(param).then((list)=>{
        let x_=[];
        let y1_=[];
        let y2_=[];
        let data=list.list;
        for(let n=0;n<data.length;n++){
          x_.push(data[n].time_id);
          y1_.push(data[n].flow);
          y2_.push(data[n].user_count);
        }
        if(key==1){chart = Echarts.init(this.ref.echarts_div1)}
        if(key==2){chart = Echarts.init(this.ref.echarts_div2)}
        if(key==3){chart = Echarts.init(this.ref.echarts_div3)}
        if(key==4){chart = Echarts.init(this.ref.echarts_div4)}
        if(key==5){chart = Echarts.init(this.ref.echarts_div5)}
        if(key==6){chart = Echarts.init(this.ref.echarts_div6)}
        if(key==7){chart = Echarts.init(this.ref.echarts_div7)}
        if(key==8){chart = Echarts.init(this.ref.echarts_div8)}
        if(key==9){chart = Echarts.init(this.ref.echarts_div9)}
        if(key==10){chart = Echarts.init(this.ref.echarts_div10)}
        console.log(data)
        let option = this.getOption(x_,y1_,y2_);
        chart.setOption(option);// 绘制图表

      })
    }
    render() {

      if(dataSource.data.length==0){
       return <div>暂无数据</div>
      }
      return (
        <div className="con"  >
         <div className='table-div2'>
           <Tabs defaultActiveKey="1" onChange={(key) => this.callback(key)}>
             <TabPane tab={dataSource.data[0].app_type_name} key="1">
               <div className="bar_cheat"   ref={node1 => (this.ref.echarts_div1 = node1)}></div>
             </TabPane>
             <TabPane tab={dataSource.data[1].app_type_name} key="2">
               <div className="bar_cheat"   ref={node2 => (this.ref.echarts_div2 = node2)}></div>
             </TabPane>
             <TabPane tab={dataSource.data[2].app_type_name} key="3">
               <div className="bar_cheat"   ref={node3 => (this.ref.echarts_div3 = node3)}></div>
             </TabPane>
             <TabPane tab={dataSource.data[3].app_type_name} key="4">
               <div className="bar_cheat"   ref={node4 => (this.ref.echarts_div4 = node4)}></div>
             </TabPane>
             <TabPane tab={dataSource.data[4].app_type_name} key="5">
               <div className="bar_cheat"   ref={node5 => (this.ref.echarts_div5 = node5)}></div>
             </TabPane>
             <TabPane tab={dataSource.data[5].app_type_name} key="6">
               <div className="bar_cheat"   ref={node6 => (this.ref.echarts_div6 = node6)}></div>
             </TabPane>
             <TabPane tab={dataSource.data[6].app_type_name} key="7">
               <div className="bar_cheat"   ref={node7 => (this.ref.echarts_div7 = node7)}></div>
             </TabPane>
             <TabPane tab={dataSource.data[7].app_type_name} key="8">
               <div className="bar_cheat"   ref={node8 => (this.ref.echarts_div8 = node8)}></div>
             </TabPane>
             <TabPane tab={dataSource.data[8].app_type_name} key="9">
               <div className="bar_cheat"   ref={node9 => (this.ref.echarts_div9 = node9)}></div>
             </TabPane>
             <TabPane tab={dataSource.data[9].app_type_name} key="10">
               <div className="bar_cheat"   ref={node10 => (this.ref.echarts_div10 = node10)}></div>
             </TabPane>
           </Tabs>
          <div className="clear"></div>
         </div>
        </div>
      );
    }
  }
  return (
    <IndexLine></IndexLine>
  );
}



export default IndexLine

