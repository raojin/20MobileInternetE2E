import React from 'react'
import PropTypes from 'prop-types'
import { Table,Tooltip } from 'antd'
import 'antd/dist/antd.css';
import './indexGrid.less';
import {totalErr } from 'services/grid/gridServer'

import { Icon } from 'antd'

const IndexGrid = ({ dataSource }) => {

  const columns = dataSource.colNames;
  let pagination_ = {
    total: dataSource.total,
    defaultCurrent: dataSource.defaultCurrent,
    pageSize: dataSource.pageSize,
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

  class IndexBlock extends React.Component {

    constructor(props) {
      super(props);
      this.ref = {ref_pre: null, ref_next: null};
      // 设置 initial state
      this.state = {
        data: dataSource.data,
        pagination: pagination_,
        loading:false

      };

      this.state.pagination.onChange= (current, pageSize) => {
        this.setState({
          loading:true
        });

          let param={flag:'4G',level:'day',timeId:'201712100000',areaId:'851'}
          totalErr(param).then((list)=>{
            //this.state.pagination.total=20;
            let dataItem=list.list;
            let data=[];
            for (let i = 0; i < dataItem.length; i++) {
              data.push({key: i+1,TIME_ID: dataItem[i].TIME_ID,AREA_NAME:dataItem[i].AREA_NAME,SUCCESS_RATE:dataItem[i].SUCCESS_RATE,TOTAL_NUMBER:dataItem[i].TOTAL_NUMBER,ERROR_NUMBER: dataItem[i].ERROR_NUMBER});
            }
            this.setState({
              data:data,
              pagination:this.state.pagination,
              loading:false
            });
          })
        }

      this.expro_excel=(e)=>{
        alert("daochu")
      }
    }
    render() {
      return (
        <div className="con"  >
          <div className='table-div1'>
            <h3><Icon type="appstore" style={{ fontSize:20, color: '#08c' }} />&nbsp;<span className >激活成功率分析</span>
              <Tooltip placement="bottom" title="导出">
                <Icon className='file-excel-fr' type="file-excel"  onClick={this.expro_excel}/>
              </Tooltip>
            </h3>
          </div>
          <div className="fa-file-excel-o"></div>
          <div className='table-div2'>
            <Table  dataSource={this.state.data} columns={columns} pagination={this.state.pagination} loading={this.state.loading} />
            <div className="clear"></div>
          </div>
        </div>
      );
    }
  }
  return (
    <IndexBlock></IndexBlock>
  );
};
IndexGrid.propTypes = {
  dataSource: PropTypes.object,
}

export default IndexGrid
