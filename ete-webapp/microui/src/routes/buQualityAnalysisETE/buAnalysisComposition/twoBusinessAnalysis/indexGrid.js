import React from 'react'
import PropTypes from 'prop-types'
import { Table,Tooltip } from 'antd'
import 'antd/dist/antd.css';
import './indexGrid.less';

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

      this.expro_excel = (e) =>  {
        alert(6)
      }
      this.change = (e) =>  {
        alert(e)
      }
    }
    render() {

      return (
        <div className='table-div2' style={{width: "100%", height: "350px"}}>
          <br/>
          <Table  dataSource={this.state.data} columns={columns} pagination={this.state.pagination} loading={this.state.loading}/>
          <div className="clear"></div>
        </div>
      )
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
