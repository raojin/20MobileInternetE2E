import React from 'react'
import PropTypes from 'prop-types'
import { Switch, Route, Redirect, routerRedux } from 'dva/router'
import dynamic from 'dva/dynamic'
import App from 'routes/common/app'

const { ConnectedRouter } = routerRedux

const Routers = function ({ history, app }) {
  const error = dynamic({
    app,
    component: () => import('./routes/common/error'),
  })
  const routes = [
    {
      path: '/dashboard',//主页
      component: () => import('routes/dashboard/'),
    },
    {
      path: '/model',//demo
      models: () => [import('models/common/user')],
      component: () => import('routes/model/'),
    },
    {
      path: '/trafficFlowOne',//一级业务流量TOP
      component: () => import('./routes/buQualityAnalysisETE/informationView/trafficFlowOne/'),
      models: () => [import('models/buQualityAnalysisETE/informationView/trafficFlowOne/trafficFlowOne')],
    },
    {
      path: '/trafficFlowTwo',//二级业务流量TOP
      component: () => import('./routes/buQualityAnalysisETE/informationView/trafficFlowTwo/'),
      models: () => [import('models/buQualityAnalysisETE/informationView/trafficFlowTwo/trafficFlowTwo')],
    },
    {
      path: '/hotBusinessListGB',//热门业务榜单（流量）
      component: () => import('./routes/buQualityAnalysisETE/rankingList/hotBusinessListGB/'),
      models: () => [import('models/buQualityAnalysisETE/rankingList/hotBusinessListGB/hotblgbData')],
    },
    {
      path: '/hotBusinessListNum',//热门业务榜单（用户数）
      component: () => import('./routes/buQualityAnalysisETE/rankingList/hotBusinessListNum/'),
      models: () => [import('models/buQualityAnalysisETE/rankingList/hotBusinessListNum/hotblnumData')],
    },
    {
      path: '/freeBusinessListGB',//自有业务榜单（流量）
      component: () => import('./routes/buQualityAnalysisETE/rankingList/freeBusinessListGB/'),
      models: () => [import('models/buQualityAnalysisETE/rankingList/freeBusinessListGB/freeblgbData')],
    },
    {
      path: '/hotTerminalBusinessListGB',//热门终端榜单（流量）
      component: () => import('./routes/buQualityAnalysisETE/rankingList/hotTerminalBusinessListGB/'),
      models: () => [import('models/buQualityAnalysisETE/rankingList/hotTerminalBusinessListGB/hotTblGbData')],
    },
    {
      path: '/freeBusinessListNum',//自有业务榜单（用户数）
      component: () => import('./routes/buQualityAnalysisETE/rankingList/freeBusinessListNum/'),
      models: () => [import('models/buQualityAnalysisETE/rankingList/freeBusinessListNum/freeblnumData')],
    },
    {
      path: '/trendAnalysis',//趋势分析
      component: () => import('./routes/buQualityAnalysisETE/buAnalysisComposition/trendAnalysis/'),
      models: () => [import('models/buQualityAnalysisETE/buAnalysisComposition/trendAnalysis/trendmodels')],
    },
    {
      path: '/oneBusinessAnalysis',//一级业务分析
      component: () => import('./routes/buQualityAnalysisETE/buAnalysisComposition/oneBusinessAnalysis/'),
      models: () => [import('models/buQualityAnalysisETE/buAnalysisComposition/oneBusinessAnalysis/oneBusinessAnalysis')],
    },
    {
      path: '/twoBusinessAnalysis',//二级业务分析
      component: () => import('./routes/buQualityAnalysisETE/buAnalysisComposition/twoBusinessAnalysis/'),
      models: () => [import('models/buQualityAnalysisETE/buAnalysisComposition/twoBusinessAnalysis/twoBusinessAnalysis')],
    },
    {
      path: '/ownBusinessAnalysis',//自有业务分析
      component: () => import('./routes/buQualityAnalysisETE/buAnalysisComposition/ownBusinessAnalysis/'),
      models: () => [import('models/buQualityAnalysisETE/buAnalysisComposition/ownBusinessAnalysis/ownBusinessAnalysis')],
    },
    {
      path: '/hotterminalBrand',//TOP10终端品牌
      component: () => import('./routes/buQualityAnalysisETE/terminalAnalysis/hotterminalBrand/'),
      models: () => [import('models/buQualityAnalysisETE/terminalAnalysis/hotterminalBrand/brandModels')],
    },
    {
      path: '/hotterminalModel',//TOP10终端型号
      component: () => import('./routes/buQualityAnalysisETE/terminalAnalysis/hotterminalModel/'),
      models: () => [import('models/buQualityAnalysisETE/terminalAnalysis/hotterminalModel/modelModels')],
    },
    {
      path: '/hotterminalIndex',//终端指标分析
      component: () => import('./routes/buQualityAnalysisETE/terminalAnalysis/hotterminalIndex/'),
      models: () => [import('models/buQualityAnalysisETE/terminalAnalysis/hotterminalIndex/indexModels')],
    }
  ];

  return (
    <ConnectedRouter history={history}>
      <App>
        <Switch>
          <Route exact path="/" render={() => (<Redirect to="/trafficFlowOne" />)} />
          {
            routes.map(({ path, ...dynamics }, key) => (
              <Route key={key}
                exact
                path={path}
                component={dynamic({
                  app,
                  ...dynamics,
                })}
              />
            ))
          }
          <Route component={error} />
        </Switch>
      </App>
    </ConnectedRouter>
  )
}

Routers.propTypes = {
  history: PropTypes.object,
  app: PropTypes.object,
}

export default Routers
