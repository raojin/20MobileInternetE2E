/**
 * Created by liang on 2017/10/30.
 */
import { totalUser as query } from 'services/common/defconfig'
import { prefix } from 'utils/config'

const system_config = {
  'config-init': false
};

const callDefConfigSvc = (fn, name) => {
  query().then((data)=>{
      if(data && data.code == 1000){
        data.data.forEach(item => {
          system_config[item.name] = item.value;
        });
        window.localStorage.setItem(`${prefix}-def-config`, JSON.stringify(system_config));
      }
      if(fn){
        fn(name?system_config[name]:system_config);
      }
    }, error=>{
    console.log(`error [${error}]`)
  });
}

/**
 * @param callbackFn {Func} 访问成功回调函数
 * @param name 回调函数参数项  null为所有
 * @param force 是否强制刷新 null不刷新
 */
const getSystemConfig = (callbackFn, name, force) => {
  if(!callbackFn || !callbackFn instanceof Function){
    console.log(`获取系统数据维护失败：首参数必须是函数`);
    return;
  }
  const conf = force?"":window.localStorage.getItem(`${prefix}-def-config`);
  if(!conf){
    callDefConfigSvc(callbackFn, name);
    return ;
  }else{
    Object.assign(system_config, JSON.parse(conf));
  }
  if(callbackFn)callbackFn(name?system_config[name]:system_config);
}

module.exports = {
  getSystemConfig
}
