package com.eastcom_sw.poc.service;

import java.util.List;
import java.util.Map;

import com.eastcom_sw.poc.request.BaseRequest;
import net.sf.json.JSONObject;

public interface PocService{


    /**
     * <p>Title: queryFlowTop</p>
     * <p>Description: 查询业务流量TOP</p>
     * @param baseRequest
     * @return
     */
    List<Map<String,String>> queryFlowTop(BaseRequest baseRequest);





}
