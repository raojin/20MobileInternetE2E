package com.eastcom_sw.poc.dao;

import net.sf.json.JSONObject;

import java.util.List;


public interface PocDao{


    /**
     *
     * @param level
     * @param timeId
     * @param areaId
     * @param cityId
     * @param netType
     * @param field_type
     * @param app_type_id
     * @param app_type
     * @param top
     * @param logo_type
     * @return
     */
    List<JSONObject> getHotSelRankList(String level, String timeId,
                                       String areaId, String cityId, String netType,
                                       String field_type, String app_type_id, String app_type, String top, String logo_type);



}
