import React from 'react'
import PropTypes from 'prop-types'
import "./infoBlock.less";

const InfoBlock = ({ dataSource , type }) => {
  const data = dataSource || {
      title : '总用户数',
      cnt : 3898,
      tb : 12.1,
      hb : 8.2
    };
  const extClass = 'p-b1-div' + (type === 'bad' ? ' t2' : '');
  return (
    <div className={extClass}>
      <div className="p-img fl"></div>
      <div className="p-value fr">
        <div>{data.title}<div>{data.cnt}</div></div>
        <p>
          <span>同比：<span>{data.tb}</span>%</span>
          <span>环比：<span>{data.hb}</span>%</span>
        </p>
      </div>
      <div className="clear"></div>
    </div>
  )
}

InfoBlock.propTypes = {
  dataSource: PropTypes.object,
}

export default InfoBlock
