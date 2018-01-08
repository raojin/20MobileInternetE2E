import React from 'react'
import PropTypes from 'prop-types'
import "./rankTable.less";

const RankTable = ({ dataSource , color }) => {

   const data = dataSource || {
   title : 'TOP客户',
   columns : [
   {cname : 'APN' , ename : 'apn'},
   {cname : '用户数(人)' , ename : 'user'},
   {cname : '流量(GB)' , ename : 'flow'}
   ],
   data : [
   {apn : 'CMIOT', user : 2219, flow : 22.46},
   {apn : 'CMIOT', user : 1490, flow : 21.31},
   {apn : 'CMIOT', user : 1067, flow : 19.42},
   {apn : 'CMIOT', user : 953, flow : 13.66}
   ]
   };

  const title = dataSource.title || '';
  const columns = dataSource.columns || [];
  // const data = dataSource.data || [];
  color = color || '#40bb91';
  const extClass = 'p-t-div';

  const getHead = list => list.map(item => {return <th key={item.ename}>{item.cname}</th>;});
  const getRow = list => columns.map(item => {return <td key={item.ename}>{list[item.ename]}</td>});
  const getBody = list => list.map((item, i) => {
    i++;
    let num = i > 9 ? i : '0' + i;
    let extend = i > 3 ? '' : 'p-top';
    return <tr key={i}><td className={extend}>{num}</td>{getRow(item)}</tr>;
  });
  return (
    <div className={extClass}>
      <h5 className="p-title" style={{'backgroundColor' : color}}>{title}</h5>
      <table><tbody><tr><th>排名</th>{getHead(columns)}</tr>{getBody(data)}</tbody></table>
      <div className="clear"></div>
    </div>
  )
}


RankTable.propTypes = {
  dataSource: PropTypes.object,
}

export default RankTable
