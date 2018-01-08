import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'dva'
import { Row} from 'antd'
import { color } from 'utils'
import { Page } from 'components'
import { getSystemConfig } from 'utils/sysconfig'

const bodyStyle = {
  bodyStyle: {
    height: 432,
    background: '#fff',
  },
}

function Dashboard ({ loading }) {
  //系统数据获取数据
  getSystemConfig(data => {
    console.log(data);
  })

  return (
    <Page loading={loading.models.dashboard}>
      <Row gutter={24}>
        <div>主页内容</div>
      </Row>
    </Page>
  )
}

Dashboard.propTypes = {
  loading: PropTypes.object,
}

export default connect(({ loading }) => ({ loading }))(Dashboard)
