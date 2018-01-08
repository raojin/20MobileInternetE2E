import modelExtend from 'dva-model-extend'
import { queryTrendData } from 'services/buQualityAnalysisETE/buAnalysisComposition/trendAnalysis/trendAnalyServer';
import { pageModel } from 'models/common/common'
import { config } from 'utils'
const { prefix } = config

//从models/commmon/common/pageModel继承模型
export default modelExtend(pageModel, {
  //模型命名空间
  namespace: 'trendValue',
  //模型初始化状态，由于参数已经在`models/common/common.js`中设置过了，这里不需要设置
  state: {
  },
  //创建订阅方法
  subscriptions:{
    //启动时监听
    setup ({ dispatch }) {
      dispatch({
        type: 'queryTrendData',
        payload: {flag:'one',level:'day',timeId:'20171210',areaId:'851',netType:'4G',queryType:'999999,1,7,15,5,8'}
      });
      // // 监听当前的地址变换,这里使用 箭头函数
      // history.listen(location => {
      //   //触发查询effect
      //   dispatch({
      //     type: `queryOneData`,
      //     payload: {level:'day',timeId:'20171211',areaId:'1766',netType:'4G',queryType:'999999,1,7,15,5,8'}
      //   });
      // })
    },
  },
  //异步操作调用函数模块，这里需要创建的是 Generator函数
  effects: {
    *queryTrendData ({ payload = {} }, { call, put }) {
      //异步调用totalErr方法
      const data = yield call(queryTrendData, payload)
     if (data && data.statusCode == 200) {
        //调用querySuccess的reducer
        yield put({//表示下一步调用哪个方法
          type: 'queryModel',
          payload: {
            list: data
          },
        });
      }
    },
  },
  //reducer已经在pageModel中实现过了，这里就不用写querySuccess了
  reducers:{
    queryModel(state, { payload }) {
      const { list } = payload
      return {
        ...state,
        list
      }
    }
  },
})
