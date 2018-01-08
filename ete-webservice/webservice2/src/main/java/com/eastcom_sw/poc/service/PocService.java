package com.eastcom_sw.poc.service;

import java.util.List;
import java.util.Map;

import com.eastcom_sw.poc.request.BaseRequest;
import net.sf.json.JSONObject;

public interface PocService{


    /**
     * 热门业务排行榜(流量，用户数)
     * @return
     */
    List<JSONObject> getHotSelRankList(BaseRequest baseRequest);




}
