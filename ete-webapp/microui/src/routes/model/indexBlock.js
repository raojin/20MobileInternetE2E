import React from 'react'
import PropTypes from 'prop-types'
import "./indexBlock.less";

const IndexBlock = ({ dataSource , type }) => {
  const data = dataSource || {
      title : '用户数',
      title_1 : '激活用户数(户)',
      cnt_1 : 2783,
      tb_1 : 6.02,
      hb_1 : 3.04,
      title_2 : '活跃用户数(户)',
      cnt_2 : 3138,
      tb_2 : -0.93,
      hb_2 : 2.66
    };
  const extClass = 'p-b2-div' + (type === 'business' ? ' t2' : '');

  return (
    <div className={extClass}>
      <span className="p-title">{data.title}</span>
      <div className="p-content">
        <div className="p-top">
          <div className="img p-img-1 fl"/>
          <div className="fr">{data.title_1}<div>{data.cnt_1}</div></div>
          <div className="clear"></div>
        </div>
        <p>
          <span>同比：<span>{data.tb_1}</span>%</span>
          <span>环比：<span>{data.hb_1}</span>%</span>
        </p>
      </div>
      <div className="p-content">
        <div className="p-top">
          <div className="img p-img-2 fl"/>
          <div className="fr">{data.title_2}<div>{data.cnt_2}</div></div>
          <div className="clear"></div>
        </div>
        <p>
          <span>同比：<span>{data.tb_2}</span>%</span>
          <span>环比：<span>{data.hb_2}</span>%</span>
        </p>
      </div>
    </div>
  )
}

IndexBlock.propTypes = {
  dataSource: PropTypes.object,
}

export default IndexBlock
