package com.eastcom_sw.poc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.eastcom_sw.frm.core.dao.jpa.DaoImpl;
import com.eastcom_sw.poc.dao.PocDao;

import net.sf.json.JSONObject;


@Component
public class PocDaoImpl extends DaoImpl implements PocDao{
	@Override
	public List<JSONObject> getHotSelRankList(String level, String timeId, String areaId, String cityId, String netType, final String field_type, String app_type_id, String app_type, String top, String logo_type) {
		String tableName1 = "DM_PTP_SE_AREA_L3";
		String tableName2 = "DM_PTP_USER_DIST_APP_L2";
		String tableName3 = "DM_PTP_USER_DIST_APP_L1";
		String tableName4 = "DM_PTP_NET_TM";
		String jsonField = "";
		String time_hb = "";
		String wherePartSql1 = "";
		String wherePartSql2 = "";
		String seTableName = " DW_DM_SE_OWN_APPSUBTYPE ";
		String timeFm = "'',";
		if("15min".equals(level)){
			tableName1 += "_15";
			tableName2 += "_15";
			tableName3 += "_15";
			tableName4 += "_15";
			timeId = timeId + "";
			time_hb = this.getEachTime(timeId, "15min", -1);
			timeFm = "TO_CHAR(TO_DATE("+timeId+",'YYYYMMDDHH24MI'),'YYYY-MM-DD HH24:MI') AS TIME_ID,";
		}else if  ("hour".equals(level)) {
			tableName1 += "_H";
			tableName2 += "_H";
			tableName3 += "_H";
			tableName4 += "_H";
			timeId = timeId + "";
			time_hb = this.getEachTime(timeId, "hour", -1);
			timeFm = "TO_CHAR(TO_DATE("+timeId+",'YYYYMMDDHH24MI'),'YYYY-MM-DD HH24:MI') AS TIME_ID,";
		} else if ("day".equals(level)) {
			tableName1 += "_D";
			tableName2 += "_D";
			tableName3 += "_D";
			tableName4 += "_D";
			timeId = timeId + "0000";
			time_hb = this.getEachTime(timeId, "day", -1);
			timeFm = "TO_CHAR(TO_DATE("+timeId+",'YYYYMMDDHH24MI'),'YYYY-MM-DD') AS TIME_ID,";
		} else if ("month".equals(level)) {
			tableName1 += "_M";
			tableName2 += "_M";
			tableName3 += "_M";
			tableName4 += "_M";
			timeId = timeId + "010000";
			time_hb = this.getEachTime(timeId, "month", -1);
			timeFm = "TO_CHAR(TO_DATE("+timeId+",'YYYYMMDDHH24MI'),'YYYY-MM') AS TIME_ID,";
		} else if ("week".equals(level)) {
			tableName1 += "_W";
			tableName2 += "_W";
			tableName3 += "_W";
			tableName4 += "_W";
			timeId = timeId + "0000";
			time_hb = this.getEachTime(timeId, "week", -7);
			timeFm = "TO_CHAR(TO_DATE("+timeId+",'YYYYMMDDHH24MI'),'YYYY-MM-DD') AS TIME_ID,";
		}
		String tableName1_l3 = "(SELECT T.TIME_ID,T.AREA_ID,T.AREA_NAME,T.APP_TYPE_ID,T.APP_TYPE_NAME,"
				+ " T.APP_SUBTYPE_ID,T.APP_SUBTYPE_NAME,T.UL_BYTES,T.DL_BYTES,T.RAT, T.HTTP_REQ_CNT FROM "+tableName1+" T WHERE NOT EXISTS "
				+ "(SELECT 1 FROM (SELECT DISTINCT A.APP_TYPE_OWN_ID,A.APP_SUBTYPE_OWN_ID "
				+ "FROM DW_DM_SE_REL_MERGE_APPSUBTYPE A) A WHERE A.APP_TYPE_OWN_ID = T.APP_TYPE_ID AND T.APP_SUBTYPE_ID = A.APP_SUBTYPE_OWN_ID)"
				+ " union all SELECT T.TIME_ID,T.AREA_ID,T.AREA_NAME,T.APP_TYPE_ID,T.APP_TYPE_NAME,"
				+ "T.APP_SUBTYPE_MERGE_ID, T.APP_SUBTYPE_MERGE_NAME,T.UL_BYTES,T.DL_BYTES,T.RAT, T.HTTP_REQ_CNT FROM "+tableName1.replace("L3", "L2")+" T,(SELECT DISTINCT A.APP_TYPE_OWN_ID, A.APP_SUBTYPE_MERGE_ID"
				+ " FROM DW_DM_SE_REL_MERGE_APPSUBTYPE A) A WHERE A.APP_TYPE_OWN_ID = T.APP_TYPE_ID AND T.APP_SUBTYPE_MERGE_ID = A.APP_SUBTYPE_MERGE_ID )";

		String midPart = "";
		if(!app_type.equals("tm")&& org.apache.commons.lang.StringUtils.isNotBlank(app_type_id)){
			midPart = " AND APP_TYPE_ID="+app_type_id;
		}
		wherePartSql1 = " where time_id="+timeId+" AND AREA_ID="+areaId+midPart+" AND NT.NETTYPE_NAME = '"+netType+"'";
		wherePartSql2 = " where time_id="+time_hb+" AND AREA_ID="+areaId+midPart+" AND NT.NETTYPE_NAME = '"+netType+"'";
		String sql = "";
		String appPartSql = "";
		String logo_field = "SETYPE_LOGO_URL";
		if("small".equals(logo_type)){
			logo_field = "SETYPE_SMALL_LOGO_URL";
		}else if("big".equals(logo_type)){
			logo_field = "SETYPE_BIG_LOGO_URL";
		}
		if(app_type.equals("hot")){
			appPartSql = " AND IS_HOTSE = 1 ";
		}else if(app_type.equals("own")){
			appPartSql = " AND IS_OWNSE = 1 ";
		}
		appPartSql += " AND SHOW_FLAG = 1 AND IS_FOCUS = 1 ";
		if("4G".equals(netType)){
			appPartSql += " AND IS_LTE_APPTYPE = 1";
		}else{
			appPartSql += " AND IS_GSMTD_APPTYPE = 1";
		}
		if ("flow".equals(field_type)) {
			jsonField = "flow,flow_zb,";
			sql = "select * from (SELECT "+timeFm+"'"+netType+"' AS NET_TYPE,"
					+ "TT1.AREA_NAME,TT1.APP_TYPE_ID,TT1.APP_TYPE_NAME,TT1.APP_SUBTYPE_ID,TT1.APP_SUBTYPE_NAME,ROUND(TT1.udl_bytes,2) AS FLOW,"
					+ " ROUND(TT1.udl_bytes*100.0/NULLIF(TT1.toal_udl_bytes,0),2) as flow_zb," +
//					"TT1."+logo_field+" AS LOGO_URL," +
					"CASE TT1.SETYPE_SMALL_LOGO_URL WHEN  NULL THEN ' ' ELSE  TT1.SETYPE_SMALL_LOGO_URL  END AS LOGO_URL,"+
					"TT1.today_RN AS ROWNUM_,TT3.last_RN AS FLU"
					+ " FROM "
					+ " (SELECT T1.AREA_NAME,T1.APP_TYPE_ID,T1.APP_TYPE_NAME,T1.APP_SUBTYPE_ID,T1.APP_SUBTYPE_NAME,T1."+logo_field+",T1.udl_bytes,T2.toal_udl_bytes ,"
					+ " row_number() over (order by T1.udl_bytes DESC,T1.APP_SUBTYPE_NAME DESC) as today_RN FROM "
					+ " ( SELECT DISTINCT AREA_ID,AREA_NAME,APP_TYPE_ID,APP_TYPE_NAME,T.APP_SUBTYPE_ID,T.APP_SUBTYPE_NAME,D."+logo_field+",(UL_BYTES+DL_BYTES)*1.0/1024/1024/1024 as udl_bytes"
					+ " FROM "+tableName1_l3+" T LEFT JOIN DW_DM_NETYPE_INFO NT ON T.RAT=NT.ID_ LEFT JOIN "+seTableName+" D ON T.APP_TYPE_ID = D.APP_TYPE_OWN_ID AND T.APP_SUBTYPE_ID = D.APP_SUBTYPE_OWN_ID "+ wherePartSql1
					+ appPartSql
					+ " ) T1,"
					+ " (SELECT AREA_NAME,sum(UL_BYTES+DL_BYTES)*1.0/1024/1024/1024  as toal_udl_bytes FROM "+tableName1+" T LEFT JOIN DW_DM_NETYPE_INFO NT ON T.RAT=NT.ID_ LEFT JOIN "+seTableName+" D ON T.APP_TYPE_ID = D.APP_TYPE_OWN_ID AND T.APP_SUBTYPE_ID = D.APP_SUBTYPE_OWN_ID "+wherePartSql1
					+ appPartSql
					+ " GROUP BY AREA_NAME) T2"
					+ " where T1.AREA_NAME=T2.AREA_NAME"
					+ " ) TT1"
					+ " left outer join "
					+ " (SELECT T3.AREA_NAME,T3.APP_TYPE_NAME,T3.APP_SUBTYPE_NAME,T3.udl_bytes,row_number() over (order by T3.udl_bytes DESC ,T3.APP_SUBTYPE_NAME DESC) as last_RN FROM "
					+ " ( SELECT AREA_NAME,APP_TYPE_NAME,T.APP_SUBTYPE_NAME,(UL_BYTES+DL_BYTES)*1.0/1024/1024/1024 as udl_bytes"
					+ " FROM "+tableName1_l3+" T LEFT JOIN DW_DM_NETYPE_INFO NT ON T.RAT=NT.ID_ LEFT JOIN "+seTableName+" D ON T.APP_TYPE_ID = D.APP_TYPE_OWN_ID AND T.APP_SUBTYPE_ID = D.APP_SUBTYPE_OWN_ID "+ wherePartSql2
					+ appPartSql
					+ " ) T3"
					+ " )TT3"
					+ "  ON TT1.APP_TYPE_NAME=TT3.APP_TYPE_NAME AND TT1.APP_SUBTYPE_NAME=TT3.APP_SUBTYPE_NAME order by TT1.today_RN) where rownum<="+top;
		} else if ("user".equals(field_type)) {
			jsonField = "user_count,user_count_zb,";
			sql = "select * from (SELECT "+timeFm+"'"+netType+"' AS NET_TYPE,"
					+ "TT1.AREA_NAME,TT1.APP_TYPE_ID,TT1.APP_TYPE_NAME,TT1.APP_SUBTYPE_MERGE_ID AS APP_SUBTYPE_ID,TT1.APP_SUBTYPE_MERGE_NAME AS APP_SUBTYPE_NAME,CAST(TT1.USER_COUNT/10000 AS DECIMAL(32,2)) AS USER_COUNT,"
					+ " CAST(TT1.USER_COUNT*100.0/NULLIF(TT1.T_USER_COUNT,0) AS DECIMAL(32,2)) AS user_count_zb,TT1."+logo_field+" AS LOGO_URL,TT1.today_RN AS ROWNUM_,TT2.last_RN AS FLU"
					+ " FROM"
					+ " (SELECT TU1.AREA_NAME,TU1.APP_TYPE_ID,TU1.APP_TYPE_NAME,TU1.APP_SUBTYPE_MERGE_ID,TU1.APP_SUBTYPE_MERGE_NAME,TU1."+logo_field+",TU1.USER_COUNT,"
					+ " TU2.USER_COUNT AS T_USER_COUNT,row_number() over (order by TU1.USER_COUNT DESC,TU1.APP_SUBTYPE_MERGE_NAME DESC) AS today_RN FROM"
					+ " (SELECT DISTINCT AREA_NAME, APP_TYPE_ID,APP_TYPE_NAME,T.APP_SUBTYPE_MERGE_ID,T.APP_SUBTYPE_MERGE_NAME,D."+logo_field+",USER_COUNT"
					+ " FROM "+tableName2+" T LEFT JOIN DW_DM_NETYPE_INFO NT ON T.RAT=NT.ID_ LEFT JOIN "+seTableName+" D ON T.APP_TYPE_ID = D.APP_TYPE_OWN_ID AND T.APP_SUBTYPE_MERGE_ID = D.APP_SUBTYPE_OWN_ID "+wherePartSql1
					+ appPartSql
					+ ") TU1,"
					+ " (SELECT AREA_NAME,SUM(USER_COUNT) USER_COUNT FROM "+tableName2 + " T LEFT JOIN DW_DM_NETYPE_INFO NT ON T.RAT=NT.ID_ LEFT JOIN "+seTableName+" D ON T.APP_TYPE_ID = D.APP_TYPE_OWN_ID AND T.APP_SUBTYPE_MERGE_ID = D.APP_SUBTYPE_OWN_ID " +wherePartSql1
					+ appPartSql
					+" GROUP BY AREA_NAME ) TU2"
					+ " where TU1.AREA_NAME=TU2.AREA_NAME  "
					+ " ) TT1"
					+ " left outer join "
					+ " (SELECT TU1.AREA_NAME,TU1.APP_TYPE_NAME,TU1.APP_SUBTYPE_MERGE_NAME,TU1.USER_COUNT,row_number() over (order by TU1.USER_COUNT DESC,TU1.APP_SUBTYPE_MERGE_NAME DESC ) as last_RN FROM "
					+ " (SELECT AREA_NAME, APP_TYPE_NAME,T.APP_SUBTYPE_MERGE_NAME,USER_COUNT"
					+ " FROM "+ tableName2+" T LEFT JOIN DW_DM_NETYPE_INFO NT ON T.RAT=NT.ID_ LEFT JOIN "+seTableName+" D ON T.APP_TYPE_ID = D.APP_TYPE_OWN_ID AND T.APP_SUBTYPE_MERGE_ID = D.APP_SUBTYPE_OWN_ID "+wherePartSql2
					+ appPartSql
					+ ") TU1 order by TU1.USER_COUNT DESC"
					+ "  )TT2"
					+ " ON TT1.APP_TYPE_NAME=TT2.APP_TYPE_NAME  AND TT1.APP_SUBTYPE_MERGE_NAME=TT2.APP_SUBTYPE_MERGE_NAME order by TT1.today_RN) where rownum<="+top;
		} else if ("request".equals(field_type)) {
			jsonField = "req_count,req_count_zb,";
			sql = "select * from (SELECT "+timeFm+"'"+netType+"' AS NET_TYPE,"
					+ "TT1.AREA_NAME,TT1.APP_TYPE_ID,TT1.APP_TYPE_NAME,TT1.APP_SUBTYPE_MERGE_ID,TT1.APP_SUBTYPE_MERGE_NAME,CAST(TT1.HTTP_REQ_CNT/10000 AS DECIMAL(32,2)) AS HTTP_REQ_CNT,"
					+ " CAST(TT1.HTTP_REQ_CNT*100.0/NULLIF(TT1.T_HTTP_REQ_CNT,0) AS DECIMAL(32,2)) AS HTTP_REQ_CNT_zb,TT1."+logo_field+"  AS LOGO_URL,TT1.today_RN AS ROWNUM_,TT2.last_RN AS FLU"
					+ " FROM"
					+ " (SELECT TU1.AREA_NAME,TU1.APP_TYPE_ID,TU1.APP_TYPE_NAME,TU1.APP_SUBTYPE_MERGE_ID,TU1.APP_SUBTYPE_MERGE_NAME,TU1."+logo_field+",TU1.HTTP_REQ_CNT,"
					+ " TU2.HTTP_REQ_CNT AS T_HTTP_REQ_CNT,row_number() over (order by TU1.HTTP_REQ_CNT DESC,TU1.APP_SUBTYPE_MERGE_NAME DESC ) AS today_RN FROM"
					+ " (SELECT DISTINCT AREA_NAME,APP_TYPE_ID, APP_TYPE_NAME,T.APP_SUBTYPE_ID APP_SUBTYPE_MERGE_ID,T.APP_SUBTYPE_NAME APP_SUBTYPE_MERGE_NAME,D."+logo_field+",HTTP_REQ_CNT"
					+ " FROM "+tableName1_l3+" T LEFT JOIN DW_DM_NETYPE_INFO NT ON T.RAT=NT.ID_ LEFT JOIN "+seTableName+" D ON T.APP_TYPE_ID = D.APP_TYPE_OWN_ID  AND T.APP_SUBTYPE_ID = D.APP_SUBTYPE_OWN_ID "+wherePartSql1
					+ appPartSql
					+ ") TU1,"
					+ " (SELECT AREA_NAME,SUM(HTTP_REQ_CNT) AS HTTP_REQ_CNT FROM "+tableName1+" T LEFT JOIN DW_DM_NETYPE_INFO NT ON T.RAT=NT.ID_ LEFT JOIN "+seTableName+" D ON T.APP_TYPE_ID = D.APP_TYPE_OWN_ID  AND T.APP_SUBTYPE_ID = D.APP_SUBTYPE_OWN_ID "+wherePartSql1
					+ appPartSql
					+"  GROUP BY AREA_NAME) TU2"
					+ " where TU1.AREA_NAME=TU2.AREA_NAME "
					+ " ) TT1"
					+ " left outer join "
					+ " (SELECT TU1.AREA_NAME,TU1.APP_TYPE_NAME,TU1.APP_SUBTYPE_MERGE_NAME,TU1.HTTP_REQ_CNT,row_number() over (order by TU1.HTTP_REQ_CNT DESC ,TU1.APP_SUBTYPE_MERGE_NAME DESC) as last_RN FROM "
					+ " (SELECT AREA_NAME, APP_TYPE_NAME,T.APP_SUBTYPE_NAME APP_SUBTYPE_MERGE_NAME,HTTP_REQ_CNT"
					+ " FROM "+ tableName1_l3+" T LEFT JOIN DW_DM_NETYPE_INFO NT ON T.RAT=NT.ID_ LEFT JOIN "+seTableName+" D ON T.APP_TYPE_ID = D.APP_TYPE_OWN_ID  AND T.APP_SUBTYPE_ID = D.APP_SUBTYPE_OWN_ID "+wherePartSql2
					+ appPartSql
					+ ") TU1 order by TU1.HTTP_REQ_CNT DESC"
					+ "  )TT2"
					+ " ON TT1.APP_TYPE_NAME=TT2.APP_TYPE_NAME  AND TT1.APP_SUBTYPE_MERGE_NAME=TT2.APP_SUBTYPE_MERGE_NAME order by TT1.today_RN) where rownum<="+top;
		}

		if(app_type.equals("tm")){
			String t_sql=
					"SELECT T.AREA_NAME, T.TERMINAL_BRAND, T.TERMINAL_MODEL, " +
							"SUM(UL_BYTES+DL_BYTES)*1.0/1024/1024/1024 AS UDL_BYTES " +
							"FROM "+tableName4+" T LEFT JOIN DW_DM_NETYPE_INFO NT ON T.RAT=NT.ID_ " +
							wherePartSql1 +" AND T.TERMINAL_MODEL IS NOT NULL " +
							"GROUP BY T.TIME_ID,T.TERMINAL_BRAND,T.TERMINAL_MODEL,T.AREA_ID,T.AREA_NAME,T.RAT";
			//贵州的终端型号需要取合并字段：表DW_DM_CU_TM_INFO的TERMINAL_MERGE_NAME字段,因此需要关联此表
			/*String t_sql = "SELECT T.AREA_NAME, T.TERMINAL_BRAND, TM.TERMINAL_MERGE_NAME TERMINAL_MODEL, " +
							"SUM(UL_BYTES+DL_BYTES)*1.0/1024/1024/1024 AS UDL_BYTES " +
							"FROM "+tableName4+" T LEFT JOIN DW_DM_NETYPE_INFO NT ON T.RAT=NT.ID_ " +
							"LEFT JOIN DW_DM_CU_TM_INFO TM " +
							"ON T.IMEI_TAC=TM.IMEI_TAC "+
							wherePartSql1 +" AND TM.TERMINAL_MERGE_NAME IS NOT NULL " +
							"GROUP BY T.TIME_ID,T.TERMINAL_BRAND,TM.TERMINAL_MERGE_NAME,T.AREA_ID,T.AREA_NAME,T.RAT";*/
			sql =
					"SELECT * FROM " +
							"(SELECT "+timeFm+"'"+netType+"' AS NET_TYPE,"
							+ "TT1.AREA_NAME,'' AS APP_TYPE_ID, TT1.TERMINAL_BRAND AS APP_TYPE_NAME,'' AS APP_SUBTYPE_ID, " +
							"TT1.TERMINAL_MODEL AS APP_SUBTYPE_NAME, ROUND(TT1.UDL_BYTES ,2) AS FLOW, " +
							"ROUND(TT1.UDL_BYTES*100.0/NULLIF(TT1.TOAL_UDL_BYTES,0),2) AS FLOW_ZB," +
							"'' AS LOGO_URL, TT1.TODAY_RN AS ROWNUM_, TT3.LAST_RN AS FLU " +
							"FROM " +
							"(" +
							"SELECT T1.AREA_NAME, T1.TERMINAL_BRAND, T1.TERMINAL_MODEL, " +
							"T1.UDL_BYTES, T2.TOAL_UDL_BYTES, " +
							"ROW_NUMBER() OVER (ORDER BY T1.UDL_BYTES DESC ) AS TODAY_RN FROM " +
							"(" +t_sql+") T1, " +
							"(" +
							"SELECT AREA_NAME, SUM(UL_BYTES+DL_BYTES)*1.0/1024/1024/1024 AS TOAL_UDL_BYTES " +
							"FROM "+tableName4+" T LEFT JOIN DW_DM_NETYPE_INFO NT ON T.RAT=NT.ID_ "+
							wherePartSql1 + " GROUP BY AREA_NAME" +
							") T2 " +
							"WHERE T1.AREA_NAME=T2.AREA_NAME " +
							") TT1 " +
							"LEFT OUTER JOIN " +
							"(" +
							"SELECT T3.AREA_NAME, T3.TERMINAL_BRAND, T3.TERMINAL_MODEL, " +
							"T3.UDL_BYTES, ROW_NUMBER() OVER (ORDER BY T3.UDL_BYTES DESC ) AS LAST_RN " +
							"FROM " +
							"(" +t_sql.replace(timeId, time_hb)+") T3 " +
							")TT3 " +
							"ON TT1.TERMINAL_BRAND=TT3.TERMINAL_BRAND AND TT1.TERMINAL_MODEL=TT3.TERMINAL_MODEL " +
							"ORDER BY TT1.TODAY_RN) WHERE ROWNUM<="+top;

		}
		log.info("业务排行榜"+app_type+"(ORACLE数据源)sql：" + sql);
		final List<String> arr = new ArrayList<>();
		arr.add("TIME_ID");
		arr.add("NET_TYPE");
		arr.add("AREA_NAME");
		arr.add("APP_TYPE_ID");
		arr.add("APP_TYPE_NAME");
		arr.add("APP_SUBTYPE_ID");
		arr.add("APP_SUBTYPE_NAME");
		if("flow".equals(field_type)){
			arr.add("FLOW");
			arr.add("FLOW_ZB");
		}else if("user".equals(field_type)){
			arr.add("USER_COUNT");
			arr.add("USER_COUNT_ZB");
		}else if("request".equals(field_type)){
			arr.add("REQ_COUNT");
			arr.add("REQ_COUNT_ZB");
		}
		arr.add("LOGO_URL");
		arr.add("ROWNUM_");
		arr.add("FLU");

		final List<JSONObject> list = jdbcTemplate.query(sql.toString(), new Object[]{} , new RowMapper<JSONObject>() {
			@Override
			public JSONObject mapRow(ResultSet resultSet, int i) throws SQLException {
				JSONObject json = new JSONObject();
				for(int j=0;j<arr.size();j++) {
					String field = arr.get(j);
					String value = resultSet.getString(field+"");
					if(value == null) {
						json.accumulate(arr.get(j), "");
					}else {
						json.accumulate(arr.get(j), value);
					}
				}
				/*json.accumulate("time_id", resultSet.getString("TIME_ID"));
				json.accumulate("net_type", resultSet.getString("NET_TYPE"));
				json.accumulate("area_name", resultSet.getString("AREA_NAME"));
				json.accumulate("app_type_id", resultSet.getString("APP_TYPE_ID"));
				json.accumulate("app_type_name", resultSet.getString("APP_TYPE_NAME"));
				json.accumulate("app_subtype_id", resultSet.getString("APP_SUBTYPE_ID"));
				json.accumulate("app_subtype_name", resultSet.getString("APP_SUBTYPE_NAME"));

				if("flow".equals(field_type)){
					json.accumulate("flow", resultSet.getString("FLOW"));
					json.accumulate("flow_zb", resultSet.getString("FLOW_ZB"));
				}else if("user".equals(field_type)){
					json.accumulate("user_count", resultSet.getString("USER_COUNT"));
					json.accumulate("user_count_zb", resultSet.getString("USER_COUNT_ZB"));
				}else if("request".equals(field_type)){
					json.accumulate("req_count", resultSet.getString("REQ_COUNT"));
					json.accumulate("req_count_zb", resultSet.getString("REQ_COUNT_ZB"));
				}
				if(resultSet.getString("LOGO_URL") == null){
					json.accumulate("logo_url","");
				}else{
					json.accumulate("logo_url", resultSet.getString("LOGO_URL"));
				}
				json.accumulate("rownum", resultSet.getString("ROWNUM_"));
				json.accumulate("flu", resultSet.getString("FLU"));*/

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
