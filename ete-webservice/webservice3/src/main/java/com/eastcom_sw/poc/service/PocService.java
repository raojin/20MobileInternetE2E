package com.eastcom_sw.poc.service;

import java.util.List;
import java.util.Map;

import com.eastcom_sw.poc.request.BaseRequest;
import net.sf.json.JSONObject;

public interface PocService{


    /**
     * 整体趋势分析
     * @return
     */
    List<JSONObject> getTrendAnalysis(BaseRequest baseRequest);

    /**
     * 业务类型TOP
     * @return
     */
    List<JSONObject> getBusinessType(BaseRequest baseRequest);


    /**
     * 业务小类
     * @return
     */
    List<JSONObject> getBusiness(BaseRequest baseRequest);

    /**
     * 得到单个业务类型趋势分析
     * @return
     */
    List<JSONObject> getBussinessTrendList(BaseRequest baseRequest);

}
