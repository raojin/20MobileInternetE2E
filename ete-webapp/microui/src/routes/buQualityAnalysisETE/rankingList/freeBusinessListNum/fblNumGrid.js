import React from 'react'
import { Table, Icon } from 'antd'
import 'antd/dist/antd.css'
import '../../util/publicCss/publicCss.less'

class FblNumGrid extends React.Component {
  _areaturnToMore = (e) =>  {
    console.log(this);
    alert("开发者正在努力开发中！");
  }
  render() {
    let gridData = this.props.dataSource[0]
    let grid = []
    let pagination = []
    let columns = []
    if(gridData != "" && gridData != null && gridData != undefined){
      let datas = gridData.rankData;
      //console.log(datas);
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
            <h5 className="fl"> <Icon type="caret-right"  style={{ fontSize: 14, color: '#08c'}}/>&nbsp;&nbsp;自有业务榜单(用户数)
            </h5>
          </div>
          <div className="di_right" style={{display: "none"}}>
            <span>
              <a className="areaturnToChart" >
                <Icon type="area-chart" style={{ fontSize: 16, color: '#08c'}} />
              </a>&nbsp;&nbsp;&nbsp;&nbsp;
              <a className="areaturnToGrid" >
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
          <div ref="gridTable" className="table-div2" style={{width: "100%", height: "370px", display: "inline"}}>
            <Table  dataSource={grid} columns={columns} pagination={false} showHeader={false} />
          </div>
          <div className="clear"></div>
        </div>
        <div className="di_right_2">
          <span>
            <a style={{ fontSize: 13}} onClick={this._areaturnToMore}>
              更多榜单
              <Icon type="right" style={{ fontSize: 10, color: '#08c'}}/>
              <i className="fa fa-angle-double-right" name="更多" title="更多"></i>
            </a>
          </span>
        </div>
      </div>
    )
  }
}

export default FblNumGrid
