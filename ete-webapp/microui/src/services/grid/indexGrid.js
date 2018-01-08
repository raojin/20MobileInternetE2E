import React from 'react'
import PropTypes from 'prop-types'
import { Table } from 'antd'
import 'antd/dist/antd.css';
import './indexGrid.less';
import { Icon } from 'antd'

const IndexGrid = ({ dataSource }) => {

  const columns = dataSource.colNames;
  let data=dataSource.data;


  let pagination = {
    total: dataSource.total,//dataSource.total,
    defaultCurrent: dataSource.defaultCurrent,
    pageSize: dataSource.pageSize,
    onChange: (current, pageSize) => {
      this.setState({page:current,size:pageSize});
      this.loadData(current,pageSize);
    },


    showTotal:(total, range) => {

        return (
          <span>{range[0]}-{range[1]}&nbsp;&nbsp;共 <i>{total}</i> 条数据</span>
        );
    },
    // itemRender:(total, pageSize) => {
    //   console.log("ww");
    // },
    // lengthMenu:[5,10,15],
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
      this.state = {};
      // ES6 类中函数必须手动绑定
      //this.onChange = this.onChange.bind(this);
    }
    render() {
      return (
        <div className="con" style={{margin: "20px"}} >
          <div className='table-div1'>
          {/*<h4> <Icon type="bars" />指标趋势</h4>*/}
          </div>
          <div className="fa-file-excel-o"></div>
          <div className='table-div2'>
            <Table  dataSource={data} columns={columns} pagination={pagination} />
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
