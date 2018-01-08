package com.eastcom_sw.poc.service;

import java.util.List;

import com.eastcom_sw.frm.core.entity.Page;
import com.eastcom_sw.poc.request.BaseRequest;
import net.sf.json.JSONObject;

public interface PocService{


    /**
     * 热门终端
     * @return
     */
    Page getHostList(BaseRequest baseRequest);

    /**
     * 品牌TOP
     * @param baseRequest
     * @return
     */
    List<JSONObject> getBrandList(BaseRequest baseRequest);

    /**
     * 终端TOP
     * @param baseRequest
     * @return
     */
    List<JSONObject> getModelList(BaseRequest baseRequest);


}
