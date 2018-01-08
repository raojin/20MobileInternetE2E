/* global window */
/* global document */
/* global location */
import config from "config";
import {EnumRoleType} from "enums";
import queryString from "query-string";
import {firstLetterSort} from "utils";
import {configModel} from "models/common/common";
import {getSystemConfig} from "utils/sysconfig";
const { prefix } = config

export default {
  namespace: 'app',
  state: {
    menuPopoverVisible: false,
    darkTheme: window.localStorage.getItem(`${prefix}darkTheme`) === 'true',
    navOpenKeys: JSON.parse(window.localStorage.getItem(`${prefix}navOpenKeys`)) || [],
    locationPathname: '',
    locationQuery: {},
  },
  subscriptions: {
    setupHistory ({ dispatch, history }) {
      history.listen((location) => {
        dispatch({
          type: 'updateState',
          payload: {
            locationPathname: location.pathname,
            locationQuery: queryString.parse(location.search),
          },
        })
      })
    },
  },
  effects: {

  },
  reducers: {

  },
}
