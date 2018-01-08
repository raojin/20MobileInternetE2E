import modelExtend from 'dva-model-extend'
import { totalUser,badUser,userCount,portfolio,topCustomer,topCell,topApn,topNe,targetCheck,keyTrend } from 'services/manager/securityMgr/user';
import { pageModel } from 'models/common/common'
import { config } from 'utils'
const { prefix } = config

//从models/commmon/common/pageModel继承模型
export default modelExtend(pageModel, {
  //模型命名空间
  namespace: 'user',
  //模型初始化状态，由于参数已经在`models/common/common.js`中设置过了，这里不需要设置
  state: {
  },
  //创建订阅方法
  subscriptions:{
    //启动时监听
    setup ({ dispatch, history }) {
      // 监听当前的地址变换,这里使用 箭头函数
      history.listen(location => {
        //触发查询effect
        // dispatch({
        //   type: `totalUser`,
        //   payload: {level:'day',timeId:'20171106',areaId:'851',netType:'6'}
        // });
        // dispatch({
        //   type: `badUser`,
        //   payload: {level:'day',timeId:'20171106',areaId:'851',netType:'6'}
        // });
        // dispatch({
        //   type: `userCount`,
        //   payload: {level:'day',timeId:'20171106',areaId:'851',netType:'6'}
        // });
        // dispatch({
        //   type: `portfolio`,
        //   payload: {level:'day',timeId:'20171106',areaId:'851',netType:'6'}
        // });
        // dispatch({
        //   type: `topCustomer`,
        //   payload: {level:'day',timeId:'20171106',areaId:'851',netType:'6'}
        // });
        // dispatch({
        //   type: `topCell`,
        //   payload: {level:'day',timeId:'20171106',areaId:'851',netType:'6'}
        // });
        // dispatch({
        //   type: `topApn`,
        //   payload: {level:'day',timeId:'20171106',areaId:'851',netType:'6'}
        // });
        // dispatch({
        //   type: `topNe`,
        //   payload: {level:'day',timeId:'20171106',areaId:'851',netType:'6'}
        // });
        dispatch({
          type: `targetCheck`,
          payload: {level:'day',timeId:'20171106',areaId:'851',netType:'6'}
        });
        dispatch({
          type: `keyTrend`,
          payload: {level:'day',timeId:'20171106',areaId:'851',netType:'6'}
        });

      })
    },
  },
  //异步操作调用函数模块，这里需要创建的是 Generator函数
  effects: {
    *totalUser ({ payload = {} }, { call, put }) {
      //异步调用totalUser方法
      const data = yield call(totalUser, payload)
      if (data && data.statusCode == 200) {
        //调用querySuccess的reducer
        yield put({
          type: 'queryModel',
          payload: {
            list: data
          },
        });
      }
    },
    *badUser ({ payload = {} }, { call, put }) {
      //异步调用badUser方法
      const data = yield call(badUser, payload)
      if (data && data.statusCode == 200) {
        //调用badUser的reducer
        yield put({
          type: 'queryModel',
          payload: {
            list: data
          },
        });
      }
    },
    *userCount ({ payload = {} }, { call, put }) {
      //异步调用badUser方法
      const data = yield call(userCount, payload)
      if (data && data.statusCode == 200) {
        //调用badUser的reducer
        yield put({
          type: 'queryModel',
          payload: {
            list: data
          },
        });
      }
    },
    *portfolio ({ payload = {} }, { call, put }) {
      //异步调用badUser方法
      const data = yield call(portfolio, payload)
      if (data && data.statusCode == 200) {
        //调用badUser的reducer
        yield put({
          type: 'queryModel',
          payload: {
            list: data
          },
        });
      }
    },
    *topCustomer ({ payload = {} }, { call, put }) {
      //异步调用badUser方法
      const data = yield call(topCustomer, payload)
      if (data && data.statusCode == 200) {
        //调用badUser的reducer
        yield put({
          type: 'queryModel',
          payload: {
            list: data
          },
        });
      }
    },
    *topCell ({ payload = {} }, { call, put }) {
      //异步调用badUser方法
      const data = yield call(topCell, payload)
      if (data && data.statusCode == 200) {
        //调用badUser的reducer
        yield put({
          type: 'queryModel',
          payload: {
            list: data
          },
        });
      }
    },
    *topApn ({ payload = {} }, { call, put }) {
      //异步调用badUser方法
      const data = yield call(topApn, payload)
      if (data && data.statusCode == 200) {
        //调用badUser的reducer
        yield put({
          type: 'queryModel',
          payload: {
            list: data
          },
        });
      }
    },
    *topNe ({ payload = {} }, { call, put }) {
      //异步调用badUser方法
      const data = yield call(topNe, payload)
      if (data && data.statusCode == 200) {
        //调用badUser的reducer
        yield put({
          type: 'queryModel',
          payload: {
            list: data
          },
        });
      }
    },
    *targetCheck ({ payload = {} }, { call, put }) {
      //异步调用badUser方法
      const data = yield call(targetCheck, payload)
      if (data && data.statusCode == 200) {
        //调用badUser的reducer
        yield put({
          type: 'queryModel',
          payload: {
            list: data
          },
        });
      }
    },
    *keyTrend ({ payload = {} }, { call, put }) {
      //异步调用badUser方法
      const data = yield call(keyTrend, payload)
      if (data && data.statusCode == 200) {
        //调用badUser的reducer
        yield put({
          type: 'queryModel1',
          payload: {
            list1: data
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
    },
    queryModel1(state, { payload }) {
      const { list1 } = payload
      return {
        ...state,
        list1
      }
    },

  },
})
