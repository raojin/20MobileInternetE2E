package com.eastcom_sw.poc.dao;

import net.sf.json.JSONObject;

import java.util.List;


public interface PocDao{



    /**
     * 地市趋势分析
     * @param lidu
     * @param time_id
     * @param area_id
     * @param city_id
     * @param network_type
     * @return
     */
    List<JSONObject> getTrendAnalysis(String lidu, String time_id,
                                             String area_id, String city_id,String network_type);



    /**
     * 业务类型TOP分析
     * @param lidu
     * @param time_id
     * @param area_id
     * @param city_id
     * @param network_type
     * @param flowOrUsersNum
     * @param topN
     * @param en_week
     * @param appTypeIdStr
     * @return
     */
    List getBusinessType(String lidu, String time_id, String area_id,
                                String city_id, String network_type, String flowOrUsersNum,
                                String topN,String en_week, String appTypeIdStr);


    /**
     * 二级业务TOP分析
     * @param lidu
     * @param time_id
     * @param area_id
     * @param city_id
     * @param network_type
     * @param analysis_type
     * @param flowOrUsersNum
     * @param threshold
     * @param topN
     * @param user_id
     * @param en_week
     * @return
     */
    List getSel3Business(String lidu, String time_id, String area_id,
                                String city_id, String network_type, String analysis_type,
                                String flowOrUsersNum, String threshold, String topN,
                                String user_id, String en_week);


    /**
     * 得到单个业务类型趋势分析
     * @param lidu
     * @param time_id
     * @param area_id
     * @param city_id
     * @param sel1_id
     * @param sel3_id
     * @param flowOrUsersNum
     * @param en_week
     * @param analysis_type
     * @return
     */
    List getBussinessTrendList(String lidu, String time_id,
                                                  String area_id, String city_id, String network_type, String sel1_id, String sel3_id,
                                                  String flowOrUsersNum, String threshold, String en_week,
                                                  String analysis_type);


}
