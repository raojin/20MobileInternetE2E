import modelExtend from 'dva-model-extend'
import { query  } from 'services/common/defconfig'
import { prefix } from 'utils/config'

const model = {
  namespace: 'models',
  state: {},
  subscriptions: {},
  effects: {
    *totalUser({payload = {}}, {call, put, select}){
      let data;
      data = yield call(query, payload);
      if (data.data) {
        yield put({
          type: 'totalUser',
          payload: {
            list: data.data
          }
        })
      }
    }
  },
  reducers: {
    totalUser (state, { payload }) {
      const { list } = payload
      return {
        ...state,
        list
      }
    },
  },
}

//整体配置model
const configModel = {
  states: {
    defConfig:{}
  },
  effects : {
    *queryDefConfig({payload}, { put, call, select }){
      const conf = payload.force?"":window.localStorage.getItem(`${prefix}-def-config`);
      let configMap = {};
      if(!conf){
        const data = yield call(query, payload)
        if(data && data.code == 1000){
          data.data.forEach(item => {
            configMap[item.name] = item.value;
          });
          window.localStorage.setItem(`${prefix}-def-config`, JSON.stringify(configMap));
        }
      }else{
        configMap = JSON.parse(conf);
      }
      yield put({
        type:'configSuccess',
        payload: configMap
      })
    },
  },
  reducers :{
    configSuccess (state, { payload }) {
      return {
        ...state,
        defConfig:{
          ...state.defConfig,
          ...payload
        },
      }
    },
  }
}

const pageModel = modelExtend(model, {
  state: {
    list: [],
    pagination: {
      showSizeChanger: true,
      showQuickJumper: true,
      showTotal: total => `Total ${total} Items`,
      current: 1,
      total: 0,
    },
  },

  reducers: {
    querySuccess (state, { payload }) {
      const { list, pagination } = payload
      return {
        ...state,
        list,
        pagination: {
          ...state.pagination,
          ...pagination,
        },
      }
    },
  },
})



module.exports = {
  model,
  pageModel,
  configModel,
}
