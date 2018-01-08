package com.eastcom_sw.poc.dao;

import com.eastcom_sw.frm.core.entity.Page;
import net.sf.json.JSONObject;

import java.util.List;


public interface PocDao{

    /**
     * 终端品牌
     * @param timeType
     * @param time
     * @param areaId
     * @param cityId
     * @param netType
     * @return
     */
    List<JSONObject> terminalBrand(String timeType, String time, String areaId,	String cityId, String netType);

    /**
     * 终端型号
     * @param timeType
     * @param time
     * @param areaId
     * @param cityId
     * @param netType
     * @return
     */
    List<JSONObject> terminalModel(String timeType, String time, String areaId,String cityId, String netType);


    /**
     * 热门终端分析
     * @param level
     * @param time
     * @param areaId
     * @param netType
     * @param brand
     * @param start
     * @param limit
     * @return
     */
    Page terminalHotPage(String level, String time, String areaId,
                         String netType, String brand, int pageNo, int limit);
}
