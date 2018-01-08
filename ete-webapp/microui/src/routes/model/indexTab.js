import React from 'react'
import { Tabs } from 'antd';
import PropTypes from 'prop-types'
import "./indexTab.less";

const TabPane = Tabs.TabPane;

const IndexTab = ({ dataSource }) => {
  const data = dataSource || [
      {title : '业务类', data : [
        {cname : 'APN业务接入成功率(%)', ename : 'apn_bus_succ_rate', value : 98.22, hb : 0.42, tb : 0.55},
        {cname : 'APN业务接入时长(ms)', ename : 'apn_bus_conn_delay', value : 98.22, hb : 0.42, tb : 0.55},
        {cname : 'APN业务传输速率(kbps)', ename : 'apn_bus_tran_rate', value : 98.22, hb : 0.42, tb : 0.55},
        {cname : 'TCP重传率(%)', ename : 'tcp_upload_rate', value : 98.22, hb : 0.42, tb : 0.55},
        {cname : 'TCP乱序率(%)', ename : 'apn_order_rate', value : 98.22, hb : 0.42, tb : 0.55}
      ]},
      {title : '网络类', data : [
        {cname : 'APN网络接入成功率(%)', ename : 'apn_net_succ_rate', value : 98.22, hb : 0.42, tb : 0.55},
        {cname : 'APN网络接入时延(ms)', ename : 'apn_net_conn_delay', value : 98.22, hb : 0.42, tb : 0.55},
        {cname : 'APN网络切换成功率(%)', ename : 'apn_net_switch_succ_rate', value : 98.22, hb : 0.42, tb : 0.55},
        {cname : 'APN业务传输成功率(%)', ename : 'apn_bus_tran_succ_rate', value : 98.22, hb : 0.42, tb : 0.55}
      ]}
    ];

  class IndexBlock extends React.Component {
    constructor(props) {
      super(props);
      this.ref = { ref_pre : null, ref_next : null};
      let data = props.dataSource || [];
      let count = props.count || 5;
      // 设置 initial state
      this.state = {
        data : data,
        count : count,
        page : props.page || 1,
        pages : Math.ceil(data.length/count),
        records : data.length
      };
      // ES6 类中函数必须手动绑定
      this.handleChange = this.handleChange.bind(this);
      this.prePage = this.prePage.bind(this);
      this.nextPage = this.nextPage.bind(this);
    }
    handleChange(event) {
      this.setState({
        page : event.target.value
      });
    }
    prePage(){
      this.setState({
        page : this.state.page - 1
      });
    }
    nextPage(){
      this.setState({
        page : this.state.page + 1
      });
    }
    // 获取块状元素
    getBlock(list){
      return list.map((item, i) => {
        let extClass = "p-block b-color0" + (i % 5 + 1);
        this.ref[item.ename] = null;
        let start = (this.state.page - 1) * this.state.count;
        let end = this.state.page * this.state.count;
        return <div className={extClass} key={item.ename}
                    style={{display: (i >= start && i < end ? 'block' : 'none')}}>
          <div className="top">
            <p className="b-title">{item.cname}</p>
            <p className="b-value">{item.value}</p>
          </div>
          <p>
            <span>同比：<span>{item.tb}</span>%</span>
            <span>环比：<span>{item.hb}</span>%</span>
          </p>
        </div>
      })
    }
    render() {
      return (
        <div className="p-b-div">
          <div className="p-page-btn p-pre-btn" style={{display : (this.state.page === 1 ? 'none' : 'block')}}
               onClick={this.prePage} ref={node => (this.ref.ref_pre = node)} title="上一页">&lt;</div>
          <div className="p-page-btn p-next-btn" style={{display : (this.state.page === this.state.pages ? 'none' : 'block')}}
               onClick={this.nextPage} ref={node => (this.ref.ref_next = node)} title="下一页">&gt;</div>
          <div className="p-content">{this.getBlock(this.state.data)}</div>
          <div className="clear"></div>
        </div>
      );
    }
  }

  function callback(key) {
    console.log(key);
  }
  // 获取Tab内容
  const getTabPane = list => list.map((item, i) => {
    return <TabPane tab={item.title} key={i+1}><IndexBlock dataSource={item.data}></IndexBlock></TabPane>
  });

  return(
    <Tabs defaultActiveKey="1" onChange={callback}>{getTabPane(data)}</Tabs>);
};

IndexTab.propTypes = {
  //dataSource: PropTypes.object,
}

export default IndexTab
