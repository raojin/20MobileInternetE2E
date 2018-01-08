package com.eastcom_sw.poc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.eastcom_sw.frm.core.dao.jpa.DaoImpl;
import com.eastcom_sw.poc.dao.PocDao;

import net.sf.json.JSONObject;


@Component
public class PocDaoImpl extends DaoImpl implements PocDao{

	/**
	 * 地市趋势分析
	 *
	 * @param lidu
	 * @param time_id
	 * @param area_id
	 * @param city_id
	 * @param network_type
	 * @return
	 */
	@Override
	public List<JSONObject> getTrendAnalysis(String lidu, String time_id,
											 String area_id, String city_id, String network_type) {
		String tableName1 = "DM_PTP_NET_AREA";//"DM_PTP_SE_AREA_L3";
		String tableName2 = "DM_PTP_USER_DIST_AREA";
		if(!city_id.equals("")){
			tableName1 = "DM_PTP_NET_CITY";
			tableName2 = "DM_PTP_UNUM_CITY";
		}
		List paramList = new ArrayList();
		String whereSql1 = "";
		String whereSql2 = "";
		String whereSql3 = "";
		String headPartSql = "";
		String addPartSql = "";
		String orderSql = "GROUP BY T1.TIME_ID ORDER BY T1.TIME_ID";
		String time_hb = "";
		if("15min".equals(lidu)){
			tableName1 += "_15";
			tableName2 += "_15";
			time_id += "";
			time_hb = this.getEachTime(time_id, "15min", -360);
			headPartSql = "TO_CHAR(TO_DATE(T1.TIME_ID,'YYYYMMDDHH24MI'),'MM-DD HH24:MI')";
		}else if("hour".equals(lidu)){
			tableName1 += "_H";
			tableName2 += "_H";
			time_id += "";
			time_hb = this.getEachTime(time_id, "hour", -24);
			headPartSql = "TO_CHAR(TO_DATE(T1.TIME_ID,'YYYYMMDDHH24MI'),'MM-DD HH24')";
		}else if("day".equals(lidu)){
			tableName1 += "_D";
			tableName2 += "_D";
			time_id += "0000";
			time_hb = this.getEachTime(time_id, "day", -20);
			headPartSql = "TO_CHAR(TO_DATE(T1.TIME_ID,'YYYYMMDDHH24MI'),'MM-DD')";
		}else if("month".equals(lidu)){
			tableName1 += "_M";
			tableName2 += "_M";
			time_id += "010000";
			time_hb = this.getEachTime(time_id, "month", -10);
			headPartSql = "TO_CHAR(TO_DATE(T1.TIME_ID,'YYYYMMDDHH24MI'),'YYYY-MM')";
		}else if("week".equals(lidu)){
			tableName1 += "_W";
			tableName2 += "_W";
			time_id += "0000";
			time_hb = this.getEachTime(time_id, "day", -7);
			headPartSql = "M.CN_WEEK ";
			addPartSql = "LEFT JOIN IPMSDW.DW_DM_CO_TIME M ON T1.TIME_ID = M.TIME_ID";
			orderSql = "GROUP BY T1.TIME_ID, M.CN_WEEK ORDER BY T1.TIME_ID";
		}
		whereSql1 = "T1.TIME_ID >  "+ time_hb+" "
				+ "AND T1.TIME_ID <= "+time_id +"";
//		paramList.add(Long.parseLong(time_hb));
//		paramList.add(Long.parseLong(time_id));
		whereSql2 = " AND T1.AREA_ID = " + area_id;
		if(!city_id.equals("")){
			whereSql2+=" AND T1.CITY_ID = "+city_id;
		}
		whereSql3 = " AND T1.RAT = (SELECT ID_ FROM DW_DM_NETYPE_INFO WHERE NETTYPE_NAME = '"+network_type+"')";
		String sql = "SELECT "
				+ headPartSql
				+ " TIME_ID, ROUND((SUM(T1.UL_BYTES) + SUM(T1.DL_BYTES)) / (1024 * 1024 * 1024),2) FLOW,"
				+ " ROUND(SUM(T2.USER_COUNT)/10000,2) USER_COUNT"
				+ " FROM " + tableName1 + " T1 LEFT JOIN " + tableName2 + " T2 ON T1.TIME_ID = T2.TIME_ID"
				+ " AND T1.AREA_ID = T2.AREA_ID AND T1.RAT = T2.RAT " + addPartSql + " WHERE "
				+ whereSql1 + whereSql2 + whereSql3 + orderSql;
		log.info("得到地市趋势分析(ORACLE数据源)sql：" + sql);
		final String[] arr = new String[] {"TIME_ID","FLOW","USER_COUNT"};

		return getList(sql, arr);
	}


	/**
	 * 业务类型TOP分析(一级业务)
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
	public List getBusinessType(String lidu, String time_id, String area_id,
								String city_id, String network_type, String flowOrUsersNum,
								String topN, String en_week,String appTypeIdStr) {
		String tableName1 = "DM_PTP_SE_AREA_L1";
		String tableName2 = "DM_PTP_USER_DIST_APP_L1";
		String tableName3 = "DM_PTP_NET_AREA";
		String tableName4 = "DM_PTP_USER_DIST_AREA";
		if(!city_id.equals("")){
			tableName1 = "DM_PTP_SE_CITY_L1";
			tableName2 = "DM_PTP_UNUM_APP_CITY_L1";
			tableName3 = "DM_PTP_NET_CITY";
			tableName4 = "DM_PTP_UNUM_CITY";
		}
		List paramList = new ArrayList();
		String output_time = "";
		String time_hb = "";
		if  ("15min".equals(lidu)){
			tableName1 += "_15";
			tableName2 += "_15";
			tableName3 += "_15";
			tableName4 += "_15";
			time_id = time_id + "";
			time_hb = this.getEachTime(time_id, "15min", -1);
			if (time_id != null && !"".equals(time_id)) {
				output_time = time_id.substring(0, 4) + "-"
						+ time_id.substring(4, 6) + "-"
						+ time_id.substring(6, 8) + " " + time_id.substring(8, 10) + ":" +time_id.substring(10, 12);
			}
		}else if("hour".equals(lidu)) {
			tableName1 += "_H";
			tableName2 += "_H";
			tableName3 += "_H";
			tableName4 += "_H";
			time_id = time_id + "";
			time_hb = this.getEachTime(time_id, "hour", -1);
			if (time_id != null && !"".equals(time_id)) {
				output_time = time_id.substring(0, 4) + "-"
						+ time_id.substring(4, 6) + "-"
						+ time_id.substring(6, 8) + " " + time_id.substring(8, 10) + ":00";
			}
		} else if("day".equals(lidu)) {
			tableName1 += "_D";
			tableName2 += "_D";
			tableName3 += "_D";
			tableName4 += "_D";
			time_id = time_id + "0000";
			time_hb = this.getEachTime(time_id, "day", -1);
			if (time_id != null && !"".equals(time_id)) {
				output_time = time_id.substring(0, 4) + "-"
						+ time_id.substring(4, 6) + "-"
						+ time_id.substring(6, 8);
			}
		} else if ("month".equals(lidu)) {
			tableName1 += "_M";
			tableName2 += "_M";
			tableName3 += "_M";
			tableName4 += "_M";
			time_id = time_id + "010000";
			time_hb = this.getEachTime(time_id, "month", -1);
			if (time_id != null && !"".equals(time_id)) {
				output_time = time_id.substring(0, 4) + "-"
						+ time_id.substring(4, 6);
			}
		} else {
			tableName1 += "_W";
			tableName2 += "_W";
			tableName3 += "_W";
			tableName4 += "_W";
			time_id = time_id + "0000";
			time_hb = this.getEachTime(time_id, "week", -1);
			if (en_week != null && !"".equals(en_week)) {
				output_time = en_week.substring(0, 4) + "年第"
						+ en_week.substring(4, 6) + "周 ";
			}
		}
		String appTypeIdSql = "";
		if (StringUtils.isNotBlank(appTypeIdStr)) {
			appTypeIdSql = " AND T1.APP_TYPE_ID IN (" + appTypeIdStr + ")";
		}
		String sql = "SELECT DISTINCT '"+ output_time+ "' TIME_ID "+",T5.AREA_ID,T5.AREA_NAME,T5.APP_TYPE_ID, T5.APP_TYPE_NAME,T5.FLOW,T5.FLOW_ZB,T5.USER_COUNT,T5.SERVICE_REQ_CNT,T5.USER_COUNT_ZB," +
				"   ROUND((T5.FLOW-T6.FLOW)*100/NULLIF(T6.FLOW, 0),2) FLOW_HB,"
				+ "ROUND((T5.USER_COUNT-T6.USER_COUNT)*100/NULLIF(T6.USER_COUNT, 0),2) USER_COUNT_HB"
				+ " FROM (SELECT T1.AREA_ID,T1.AREA_NAME,T1.APP_TYPE_ID,T1.APP_TYPE_NAME,"
				+ " ROUND((T1.UL_BYTES + T1.DL_BYTES) / (1024*1024*1024) ,2) AS FLOW,"
				+ " ROUND((T1.UL_BYTES + T1.DL_BYTES) * 100 / NULLIF(T5.FLOW, 0),2) AS FLOW_ZB,"
				+ " ROUND(T2.USER_COUNT/10000 ,2)  AS USER_COUNT ,ROUND(T1.SERVICE_REQ_CNT / 10000, 2) AS SERVICE_REQ_CNT,"
				+ " ROUND(T2.USER_COUNT * 100 / NULLIF(T6.USER_COUNT, 0), 2) AS USER_COUNT_ZB "
				+ " FROM  " + tableName1 + " T1 LEFT JOIN " + tableName2 + " T2 ON T1.TIME_ID = T2.TIME_ID AND T1.AREA_ID = T2.AREA_ID "
				+ " AND T1.APP_TYPE_ID = T2.APP_TYPE_ID  AND T1.RAT = T2.RAT,"
				+ "(SELECT SUM(T3.UL_BYTES + T3.DL_BYTES) AS FLOW FROM " + tableName3 + " T3"
				+ " WHERE T3.TIME_ID = " + time_id + " AND T3.AREA_ID = "+area_id+"";
		if(!city_id.equals("")){
			sql+=" AND T3.CITY_ID = "+city_id+"";
		}
		sql+=" AND T3.RAT = (SELECT ID_ FROM DW_DM_NETYPE_INFO WHERE NETTYPE_NAME = '"+network_type+"') GROUP BY T3.TIME_ID, T3.AREA_ID, T3.RAT) T5,"
				+ "(SELECT sum(T4.USER_COUNT) AS USER_COUNT FROM " + tableName4 + " T4"
				+ " where T4.TIME_ID = " + time_id + " AND T4.AREA_ID = "+area_id+"";
		if(!city_id.equals("")){
			sql+=" AND T4.CITY_ID = "+city_id+"";
		}
		sql+=" AND T4.RAT = (SELECT ID_ FROM DW_DM_NETYPE_INFO WHERE NETTYPE_NAME = '"+network_type+"')) T6"
				+ " WHERE T1.TIME_ID = " + time_id + " AND T1.AREA_ID = " + area_id + "";
		if(!city_id.equals("")){
			sql+=" AND T1.CITY_ID = "+city_id+"";
		}
		sql+=" "+appTypeIdSql+" AND T1.RAT = (SELECT ID_ FROM DW_DM_NETYPE_INFO WHERE NETTYPE_NAME = '"+network_type+"')) T5 LEFT JOIN "
				+ " (SELECT T3.AREA_ID,T3.AREA_NAME, T3.APP_TYPE_ID, T3.APP_TYPE_NAME," +
				"(T3.UL_BYTES + T3.DL_BYTES)/ (1024 * 1024 * 1024) flow,T4.USER_COUNT/10000 USER_COUNT FROM  " + tableName1 + " T3 LEFT JOIN " + tableName2 + " T4" +
				" ON T3.TIME_ID = T4.TIME_ID AND T3.AREA_ID = T4.AREA_ID AND T3.APP_TYPE_ID = T4.APP_TYPE_ID AND T3.RAT = T4.RAT WHERE T3.TIME_ID = " + time_hb
				+ " AND T3.AREA_ID = " + area_id + "";
		if(!city_id.equals("")){
			sql+=" AND T3.CITY_ID = "+city_id+"";
		}
		sql+=" AND T3.RAT = (SELECT ID_ FROM DW_DM_NETYPE_INFO WHERE NETTYPE_NAME = '"+network_type+"')) T6 ON T5.AREA_ID = T6.AREA_ID AND T5.APP_TYPE_ID = T6.APP_TYPE_ID ORDER BY FLOW DESC";
		log.info("业务类型TOP分析(ORACLE数据源)sql：" + sql);
		final String[] arr = new String[] {"time_id","area_id","area_name","app_type_id","app_type_name","flow","flow_zb","user_count","service_req_cnt","user_count_zb","flow_hb","user_count_hb"};
		return getList(sql, arr);
	}



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
	 * @param sel1_id
	 * @param en_week
	 * @return
	 */
	public List getSel3Business(String lidu, String time_id, String area_id,
								String city_id, String network_type, String analysis_type,
								String flowOrUsersNum, String threshold, String topN,
								String sel1_id, String en_week) {
		String tableName1 = "DM_PTP_SE_AREA_L2";
		String tableName1_l3 = "DM_PTP_SE_AREA_L3";
		String tableName2 = "DM_PTP_USER_DIST_APP_L2";
		String tableName3 = "DM_PTP_SE_AREA_L2";
		String tableName4 = "DM_PTP_USER_DIST_APP_L1";
		String tableName5 = "DM_PTP_SE_AREA_L2";
		String tableName6 = "DM_PTP_USER_DIST_AREA";
		String cityWhere= "";
		if(!city_id.equals("")){
			tableName1 = "DM_PTP_SE_CITY_L2";
			tableName1_l3 = "DM_PTP_SE_CITY_L3";
			tableName2 = "DM_PTP_UNUM_APP_CITY_L2";
			tableName3 = "DM_PTP_SE_CITY_L2";
			tableName4 = "DM_PTP_UNUM_APP_CITY_L1";
			tableName5 = "DM_PTP_SE_CITY_L2";
			tableName6 = "DM_PTP_UNUM_CITY";
			cityWhere= " AND T1.CITY_ID = "+city_id+" ";
		}
		String output_time = "";
		String wherePartSql1 = "";
		String wherePartSql2 = "";
		String wherePartSql3 = "";
		String wherePartSql4 = "";
		String wherePartSql5 = "";
		String appPartSql = "";
		if ("15min".equals(lidu)){
			tableName1 += "_15";
			tableName1_l3 += "_15";
			tableName2 += "_15";
			tableName3 += "_15";
			tableName4 += "_15";
			tableName5 += "_15";
			tableName6 += "_15";
			time_id = time_id + "";
			if (time_id != null && !"".equals(time_id)) {
				output_time = time_id.substring(0, 4) + "-"
						+ time_id.substring(4, 6) + "-"
						+ time_id.substring(6, 8) + " " + time_id.substring(8, 10) + ":" +time_id.substring(10, 12);
			}
		} else if("hour".equals(lidu)) {
			tableName1 += "_H";
			tableName1_l3 += "_H";
			tableName2 += "_H";
			tableName3 += "_H";
			tableName4 += "_H";
			tableName5 += "_H";
			tableName6 += "_H";
			time_id = time_id + "";
			if (time_id != null && !"".equals(time_id)) {
				output_time = time_id.substring(0, 4) + "-"
						+ time_id.substring(4, 6) + "-"
						+ time_id.substring(6, 8) + " " + time_id.substring(8, 10) + ":00";
			}
		} else if ("day".equals(lidu)) {
			tableName1 += "_D";
			tableName1_l3 += "_D";
			tableName2 += "_D";
			tableName3 += "_D";
			tableName4 += "_D";
			tableName5 += "_D";
			tableName6 += "_D";
			time_id = time_id + "0000";
			if (time_id != null && !"".equals(time_id)) {
				output_time = time_id.substring(0, 4) + "-"
						+ time_id.substring(4, 6) + "-"
						+ time_id.substring(6, 8);
			}
		} else if ("month".equals(lidu)) {
			tableName1 += "_M";
			tableName1_l3 += "_M";
			tableName2 += "_M";
			tableName3 += "_M";
			tableName4 += "_M";
			tableName5 += "_M";
			tableName6 += "_M";
			time_id = time_id + "010000";
			if (time_id != null && !"".equals(time_id)) {
				output_time = time_id.substring(0, 4) + "-"
						+ time_id.substring(4, 6);
			}
		} else {
			tableName1 += "_W";
			tableName1_l3 += "_W";
			tableName2 += "_W";
			tableName3 += "_W";
			tableName4 += "_W";
			tableName5 += "_W";
			tableName6 += "_W";
			time_id = time_id + "0000";
			if (en_week != null && !"".equals(en_week)) {
				output_time = en_week.substring(0, 4) + "年第"
						+ en_week.substring(4, 6) + "周 ";
			}
		}
		appPartSql += " AND SHOW_FLAG = 1";
		if("4G".equals(network_type)){
			appPartSql += " AND IS_LTE_APPTYPE = 1";
		}else{
			appPartSql += " AND IS_GSMTD_APPTYPE = 1";
		}
		if ("hotBusiness".equals(analysis_type)) {
			appPartSql += " AND IS_HOTSE = 1";
		} else if ("ownBusiness".equals(analysis_type)) {
			appPartSql += " AND IS_OWNSE = 1";
		}
		if ("flow".equals(flowOrUsersNum)) {
			wherePartSql4 = "(T1.UL_BYTES + T1.DL_BYTES) DESC";
			if (threshold != null && !"".equals(threshold)) {
				wherePartSql5 = " AND (T1.UL_BYTES + T1.DL_BYTES) / (1024 * 1024 * 1024)  >= " + threshold;
			}
		} else {
			wherePartSql4 = "(T2.USER_COUNT) DESC";
			if (threshold != null && !"".equals(threshold)) {
				wherePartSql5 = " AND T2.USER_COUNT / 10000 >= " + threshold;
			}
		}

		if (topN != null && !"".equals(topN)) {
			wherePartSql3 = " WHERE ROWNUM<= " + topN;
		}
		String wherePartSql2_2 =" ";
		if (sel1_id != null && !StringUtils.equals("0",sel1_id)  && !"".equals(sel1_id) ) {
			wherePartSql1 = " AND T3.APP_TYPE_ID =  " + sel1_id;
			wherePartSql2_2 = " AND T4.APP_TYPE_ID =  " + sel1_id;
			wherePartSql2 = " AND T1.APP_TYPE_ID =  " + sel1_id;
		}
		String sql =
				"SELECT P.TIME_ID,P.AREA_ID, P.AREA_NAME, " +
						"P.APP_TYPE_ID, P.APP_TYPE_NAME, P.APP_SUBTYPE_MERGE_ID, P.APP_SUBTYPE_MERGE_NAME, " +
						"P.FLOW, P.FLOW_ZB, P.USER_COUNT, p.SERVICE_REQ_CNT, P.USER_COUNT_ZB, P.ALL_FLOW_ZB, P.ALL_USER_COUNT_ZB " +
						"FROM (" +
						"SELECT '"+ output_time+ "' TIME_ID, T1.AREA_ID, T1.AREA_NAME, " +
						"T1.APP_TYPE_ID, T1.APP_TYPE_NAME, T1.APP_SUBTYPE_MERGE_ID, T1.APP_SUBTYPE_MERGE_NAME, " +
						"ROUND((T1.UL_BYTES + T1.DL_BYTES) / (1024*1024*1024) ,2) AS FLOW, " +
						"ROUND((T1.UL_BYTES + T1.DL_BYTES) * 100 / NULLIF(T5.FLOW,0) ,2) AS FLOW_ZB, " +
						"ROUND(T2.USER_COUNT/10000 ,2) AS USER_COUNT , ROUND(T1.SERVICE_REQ_CNT / 10000, 2) AS SERVICE_REQ_CNT, " +
						"ROUND(T2.USER_COUNT * 100 / NULLIF(T6.USER_COUNT,0) ,2) AS USER_COUNT_ZB, " +
						"ROUND((T1.UL_BYTES + T1.DL_BYTES) * 100 / NULLIF(T7.FLOW,0) ,2) AS ALL_FLOW_ZB, " +
						"ROUND(T2.USER_COUNT * 100 / NULLIF(T8.USER_COUNT,0) ,2) AS ALL_USER_COUNT_ZB " +
						"FROM(" +
						"SELECT  T.TIME_ID, T.AREA_ID, T.AREA_NAME, T.APP_TYPE_ID, " +
						"T.APP_TYPE_NAME, T.APP_SUBTYPE_MERGE_ID, T.APP_SUBTYPE_MERGE_NAME,T.UL_BYTES , " +
						"T.DL_BYTES,T.SERVICE_REQ_CNT,T.RAT FROM "+tableName1+" T, " +
						"(SELECT DISTINCT A.APP_TYPE_OWN_ID, A.APP_SUBTYPE_MERGE_ID FROM DW_DM_SE_OWN_APPSUBTYPE A " +
						"WHERE A.APP_SUBTYPE_OWN_ID = A.APP_SUBTYPE_MERGE_ID) A " +//此处两个id相同表示是二级业务类型
						"WHERE A.APP_TYPE_OWN_ID = T.APP_TYPE_ID AND T.APP_SUBTYPE_MERGE_ID = A.APP_SUBTYPE_MERGE_ID " +
						"UNION ALL " +
						"SELECT T.TIME_ID, T.AREA_ID, T.AREA_NAME, T.APP_TYPE_ID, " +
						"T.APP_TYPE_NAME, T.APP_SUBTYPE_ID, T.APP_SUBTYPE_NAME, T.UL_BYTES, T.DL_BYTES,T.SERVICE_REQ_CNT, T.RAT " +
						"FROM "+tableName1_l3+" T " +
						"WHERE NOT EXISTS " +
						"(SELECT 1 FROM " +
						"(SELECT DISTINCT A.APP_TYPE_OWN_ID, A.APP_SUBTYPE_OWN_ID FROM DW_DM_SE_OWN_APPSUBTYPE A) A " +
						"WHERE A.APP_TYPE_OWN_ID = T.APP_TYPE_ID " +
						"AND T.APP_SUBTYPE_ID = A.APP_SUBTYPE_OWN_ID" +
						")" +
						") T1 " +
						"LEFT JOIN " + tableName2 + " T2 " +
						"ON T1.TIME_ID = T2.TIME_ID AND T1.AREA_ID = T2.AREA_ID " +
						"AND T1.APP_TYPE_ID = T2.APP_TYPE_ID  AND T1.RAT = T2.RAT " +
						"AND T1.APP_SUBTYPE_MERGE_ID = T2.APP_SUBTYPE_MERGE_ID " +
						"LEFT JOIN DW_DM_NETYPE_INFO NT ON T1.RAT = NT.ID_ " +
						"JOIN DW_DM_SE_OWN_APPSUBTYPE AT " +
						"ON T1.APP_TYPE_ID=AT.APP_TYPE_OWN_ID AND T1.APP_SUBTYPE_MERGE_ID=AT.APP_SUBTYPE_OWN_ID " +
						"LEFT JOIN" +
						"(" +
						"SELECT T3.APP_TYPE_ID, SUM(T3.UL_BYTES + T3.DL_BYTES) FLOW " +
						"FROM " + tableName3 + " T3 " +
						"JOIN (SELECT DISTINCT T.APP_TYPE_OWN_ID, T.APP_SUBTYPE_MERGE_ID FROM DW_DM_SE_OWN_APPSUBTYPE T WHERE " +
						"T.APP_SUBTYPE_OWN_ID = T.APP_SUBTYPE_MERGE_ID AND T.SHOW_FLAG = 1) AT " +//此处两个id相同表示是二级业务类型
						"ON T3.APP_TYPE_ID = AT.APP_TYPE_OWN_ID AND T3.APP_SUBTYPE_MERGE_ID = AT.APP_SUBTYPE_MERGE_ID " +
						"WHERE T3.TIME_ID = "+time_id+" AND T3.AREA_ID = "+area_id+" "+wherePartSql1+cityWhere.replace("T1", "T3")+
						"AND T3.RAT= " +
						"(SELECT ID_ FROM DW_DM_NETYPE_INFO WHERE NETTYPE_NAME = '"+network_type+"') GROUP BY T3.APP_TYPE_ID" +
						") T5 ON T1.APP_TYPE_ID = T5.APP_TYPE_ID " +
						"LEFT JOIN" +
						"(" +
						"SELECT T4.APP_TYPE_ID, SUM(T4.USER_COUNT) AS USER_COUNT " +
						"FROM " + tableName4 + " T4 " +
						"WHERE T4.TIME_ID = " + time_id+
						" AND T4.AREA_ID = " + area_id + wherePartSql2_2 + cityWhere.replace("T1", "T4")+
						"AND T4.RAT = (SELECT ID_ FROM DW_DM_NETYPE_INFO WHERE NETTYPE_NAME = '"+network_type+"') " +
						"GROUP BY T4.APP_TYPE_ID" +
						") T6 ON T1.APP_TYPE_ID = T6.APP_TYPE_ID, " +
						"(" +
						"SELECT SUM(T3.UL_BYTES + T3.DL_BYTES) FLOW " +
						"FROM " + tableName5 + " T3 " +
						"JOIN (SELECT DISTINCT T.APP_TYPE_OWN_ID,T.APP_SUBTYPE_MERGE_ID FROM DW_DM_SE_OWN_APPSUBTYPE T WHERE " +
						"T.APP_SUBTYPE_OWN_ID = T.APP_SUBTYPE_MERGE_ID AND T.SHOW_FLAG = 1) AT " +//此处两个id相同表示是二级业务类型
						"ON T3.APP_TYPE_ID = AT.APP_TYPE_OWN_ID " +
						"AND T3.APP_SUBTYPE_MERGE_ID = AT.APP_SUBTYPE_MERGE_ID " +
						"WHERE T3.TIME_ID= " + time_id + " AND T3.AREA_ID = "+area_id+cityWhere.replace("T1", "T3")+
						"AND T3.RAT = (SELECT ID_ FROM DW_DM_NETYPE_INFO WHERE NETTYPE_NAME = '"+network_type+"')" +
						") T7, " +
						"(" +
						"SELECT SUM(T4.USER_COUNT) AS USER_COUNT " +
						"FROM " + tableName6 + " T4 " +
						"WHERE T4.TIME_ID = " + time_id + " AND T4.AREA_ID = "+area_id+cityWhere.replace("T1", "T4")+
						"AND T4.RAT = (SELECT ID_ FROM DW_DM_NETYPE_INFO WHERE NETTYPE_NAME = '"+network_type+"')" +
						") T8 " +
						"WHERE T2.USER_COUNT IS NOT NULL AND T1.TIME_ID = " + time_id+cityWhere.replace("T1.", "")+
						"AND T1.AREA_ID = " + area_id + wherePartSql2 + wherePartSql5+
						" AND NT.NETTYPE_NAME = '"+network_type+"'" + appPartSql +
						" ORDER BY " + wherePartSql4 +
						") P" + wherePartSql3;
		log.info(analysis_type+"二级业务TOP分析(ORACLE数据源)sql：" + sql);
		String[] arr = new String[] {"time_id","area_id","area_name","app_type_id","app_type_name","app_subtype_merge_id","app_subtype_merge_name","flow","flow_zb",
				"user_count","service_req_cnt","user_count_zb","all_flow_zb","all_user_count_zb"};

		return getList(sql,arr);
	}

	@Override
	public List getBussinessTrendList(String lidu, String time_id, String area_id, String city_id, String network_type, String sel1_id, String sel3_id, String flowOrUsersNum, String threshold, String en_week, String analysis_type) {

		String tableName1 = " DM_PTP_SE_AREA_L1";
		String tableName2 = " DM_PTP_USER_DIST_APP_L1";
		List paramList = new ArrayList();
		String whereSql1 = "";
		String whereSql2 = "";
		String whereSql3 = "";
		String whereSql4 = "";
		String midSql = "";
		String headPartSql = "";
		String addPartSql = "";
		String time_hb = "";
		if("15min".equals(lidu)){
			tableName1 += "_15";
			tableName2 += "_15";
			time_id += "";
			time_hb = this.getEachTime(time_id, "15min", -360);
			headPartSql = "TO_CHAR(TO_DATE(T1.TIME_ID,'YYYYMMDDHH24MI'),'YYYY-MM-DD HH24:MI')";
		} else if("hour".equals(lidu)){
			tableName1 += "_H";
			tableName2 += "_H";
			time_id += "";
			time_hb = this.getEachTime(time_id, "hour", -24);
			headPartSql = "TO_CHAR(TO_DATE(T1.TIME_ID,'YYYYMMDDHH24MI'),'YYYY-MM-DD HH24')";
		}else if("day".equals(lidu)){
			tableName1 += "_D";
			tableName2 += "_D";
			time_id += "0000";
			time_hb = this.getEachTime(time_id, "day", -7);
			headPartSql = "TO_CHAR(TO_DATE(T1.TIME_ID,'YYYYMMDDHH24MI'),'YYYY-MM-DD')";
		}else if("month".equals(lidu)){
			tableName1 += "_M";
			tableName2 += "_M";
			time_id += "010000";
			time_hb = this.getEachTime(time_id, "month", -10);
			headPartSql = "TO_CHAR(TO_DATE(T1.TIME_ID,'YYYYMMDDHH24MI'),'YYYY-MM')";
		}else if("week".equals(lidu)){
			tableName1 += "_W";
			tableName2 += "_W";
			time_id += "0000";
			time_hb = this.getEachTime(time_id, "day", -7);
			addPartSql = "LEFT JOIN DW_DM_CO_TIME M ON T1.TIME_ID = M.TIME_ID";
		}
		whereSql1 = "T1.TIME_ID >   "+time_hb
				+ "AND T1.TIME_ID <= "+time_id;
		if ("businessType".equals(analysis_type)) {
			if(!StringUtils.equals("0",sel1_id)){
				midSql = " AND T1.APP_TYPE_ID = " + sel1_id;
			}
			whereSql4  = " AND T1.APP_TYPE_ID = T2.APP_TYPE_ID ";
		} else {
			tableName1 = " DM_PTP_SE_AREA_L2_D ";
			tableName2 = " DM_PTP_USER_DIST_APP_L2_D ";
			whereSql4  = " AND T1.APP_TYPE_ID = T2.APP_TYPE_ID AND T1.APP_SUBTYPE_MERGE_ID = T2.APP_SUBTYPE_MERGE_ID ";
			if(!StringUtils.equals("0",sel1_id)){
				midSql = " AND T1.APP_TYPE_ID = " + sel1_id + " AND T1.APP_SUBTYPE_MERGE_ID = " + sel3_id;
			}
		}
		whereSql2 = " AND T1.AREA_ID = " + area_id;
		if ("flow".equals(flowOrUsersNum)) {
			if (threshold != null && !"".equals(threshold)) {
				whereSql3 = " AND (T1.UL_BYTES + T1.DL_BYTES) / (1024 * 1024 * 1024)  >= " + threshold;
			}
		} else {
			if (threshold != null && !"".equals(threshold)) {
				whereSql3 = " AND T2.USER_COUNT / 10000 >= " + threshold;
			}
		}
		String sql = "SELECT " + headPartSql + " TIME_ID,"
				+ "CAST((T1.UL_BYTES + T1.DL_BYTES) / (1024 * 1024 * 1024) AS DECIMAL(32,2)) FLOW,"
				+ "CAST(T2.USER_COUNT / 10000 AS DECIMAL(32,2)) USER_COUNT "
				+ "FROM " + tableName1 + " T1 LEFT JOIN " + tableName2 + " T2 ON T1.TIME_ID = T2.TIME_ID"
				+ " AND T1.AREA_ID = T2.AREA_ID AND T1.RAT = T2.RAT " + whereSql4 + addPartSql + " WHERE  "
				+ whereSql1 + midSql + whereSql2 + whereSql3 + " AND T1.RAT = (SELECT ID_ FROM DW_DM_NETYPE_INFO WHERE NETTYPE_NAME = '"+network_type+"')"
				+ " ORDER BY T1.TIME_ID";
		log.info("得到单个业务类型趋势分析(ORACLE数据源)sql：" + sql);
		String[] arr = new String[] {"time_id","flow","user_count"};
		return getList(sql,arr);
	}

	private List getList(String sql, final String[] arr) {
		List<JSONObject> list = jdbcTemplate.query(sql.toString(), new Object[]{} , new RowMapper<JSONObject>() {
			@Override
			public JSONObject mapRow(ResultSet resultSet, int i) throws SQLException {
				JSONObject json = new JSONObject();
				for(int j=0;j<arr.length;j++) {
					String field = arr[j];
					String value = resultSet.getString(field+"");
					if(value == null) {
						json.accumulate(arr[j], "");
					}else {
						json.accumulate(arr[j], value);
					}
				}

				return json;
			}
		});
		return list;
	}


	/**
	 * 时间处理
	 * @param time_id
	 * @param type
	 * @param num
	 * @return
	 */
	public  String getEachTime(String time_id,String type,int num){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String outputTime = "";
		try {
			Date date=sdf.parse(time_id);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if("hour".equals(type)){
				calendar.add(Calendar.HOUR, num);
			}else if("day".equals(type)){
				calendar.add(Calendar.DATE, num);
			}else if("month".equals(type)){
				calendar.add(Calendar.MONTH, num);
			}
			outputTime = sdf.format(calendar.getTime());
			if("day".equals(type)){
				outputTime = outputTime.substring(0, 8)+"0000";
			}else if("month".equals(type)){
				outputTime = outputTime.substring(0, 6)+"010000";
			}
			return outputTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return outputTime;
	}

}
