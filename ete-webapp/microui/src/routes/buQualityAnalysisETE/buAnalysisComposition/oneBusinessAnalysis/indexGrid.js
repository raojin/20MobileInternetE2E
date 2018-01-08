import React from 'react'
import PropTypes from 'prop-types'
import { Table,Tooltip } from 'antd'
import 'antd/dist/antd.css';
import './indexGrid.less';
import { queryBusinessType} from 'services/buQualityAnalysisETE/buAnalysisComposition/oneBusinessAnalysis/oneBusinessAnalysisServer';

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
        loading:true,
      };
      this.state.pagination.onChange= (current, pageSize) => {

        this.setState({
          loading:true
        });
        let param={level:'day',timeId:'20171210',areaId:'851',netType:'4G'}
        queryBusinessType(param).then((list)=>{
          let dataItem=list.list;
          let data=[];
          for (let i = 0; i < dataItem.length; i++) {
            data.push({key: i+1,time_id: dataItem[i].time_id,area_name:dataItem[i].area_name,
              app_type_name:dataItem[i].app_type_name,
              flow:dataItem[i].flow,user_count: dataItem[i].user_count,
              flow_zb:dataItem[i].flow_zb,user_count_zb: dataItem[i].user_count_zb
            });
          }
          this.setState({
            data:data,
            pagination:this.state.pagination,
            loading:false
          })
        })
      }
      this.expro_excel = (e) =>  {
        alert(6)
      }
      this.change = (e) =>  {
        alert(e)
      }
    }
    componentDidMount(){
      console.log(dataSource)
      if(dataSource.su.length>0){
        console.log("in")
        this.state.loading=false;
        this.setState({
          loading:false
        });
      }
    }

    render() {

      return (
        <div className='table-div2'  style={{width: "100%", height: "350px"}}>
          <br/>
          <Table dataSource={this.state.data} columns={columns} pagination={this.state.pagination} loading={this.state.loading}/>
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
