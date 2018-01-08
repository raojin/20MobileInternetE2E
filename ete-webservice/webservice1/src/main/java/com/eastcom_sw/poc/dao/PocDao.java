package com.eastcom_sw.poc.dao;

import net.sf.json.JSONObject;

import java.util.List;


public interface PocDao{
	
    /**
     * <p>Title: querySel1FlowTop</p>
     * <p>Description: 查询一级业务流量TOP</p>
     * @param json
     * @param timeCn
     * @param time
     * @param level
     * @param netType
     * @param ywTypeCombo
     * @param areaId
     * @param appTypeId
     * @return
     * @throws Exception
     *
     */
    List<JSONObject> querySel1FlowTop(JSONObject json,String timeCn,String time, String level, String netType,
                                             String ywTypeCombo, String areaId,String appTypeId) throws Exception;


    /**
     * <p>Title: querySel2FlowTop</p>
     * <p>Description: 查询二级业务流量TOP</p>
     * @param json
     * @param timeCn
     * @param time
     * @param level
     * @param netType
     * @param ywTypeCombo
     * @param areaId
     * @return
     * @throws Exception
     *
     */

    List<JSONObject> querySel2FlowTop(JSONObject json,String timeCn,String time, String level, String netType,
                                             String ywTypeCombo,String areaId) throws Exception ;
}
