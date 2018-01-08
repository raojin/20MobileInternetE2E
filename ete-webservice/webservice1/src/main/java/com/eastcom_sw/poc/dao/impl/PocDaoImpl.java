package com.eastcom_sw.poc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.eastcom_sw.frm.core.dao.jpa.DaoImpl;
import com.eastcom_sw.poc.dao.PocDao;

import net.sf.json.JSONObject;


@Component
public class PocDaoImpl extends DaoImpl implements PocDao{
	@Override
	public List<JSONObject> querySel1FlowTop(JSONObject json, String timeCn, String time, String level, String netType, String ywTypeCombo, String areaId, String appTypeId) throws Exception {
		try {
			/* 查询 按流量排序 信息 */
			String tableName = "DM_PTP_SE_AREA_L1_D";
			String tableName1 = "DM_PTP_USER_DIST_APP_L1_D";
			String tableName2 = "DM_PTP_USER_DIST_AREA_D";
			String sql = "";
			String areaString = "";
			if ("day".equals(level)) {
				tableName = "DM_PTP_SE_AREA_L1_D";
				tableName1 = "DM_PTP_USER_DIST_APP_L1_D";
				tableName2 = "DM_PTP_USER_DIST_AREA_D";
//			}else if ("month".equals(level)) {
//				tableName = "DM_PTP_SE_AREA_L1_M";
//				tableName1 = "DM_PTP_USER_DIST_APP_L1_M";
//				tableName2 = "DM_PTP_USER_DIST_AREA_M";
//			}else if ("week".equals(level)) {
//				tableName = "DM_PTP_SE_AREA_L1_W";
//				tableName1 = "DM_PTP_USER_DIST_APP_L1_W";
//				tableName2 = "DM_PTP_USER_DIST_AREA_W";
			}else if ("hour".equals(level)) {
				tableName = "DM_PTP_SE_AREA_L1_H";
				tableName1 = "DM_PTP_USER_DIST_APP_L1_H";
				tableName2 = "DM_PTP_USER_DIST_AREA_H";
			}
			if (!StringUtils.isNotBlank(areaId)) {
				areaString = " AND T.AREA_ID=85";
			} else {
				areaString = " AND T.AREA_ID="+areaId;
			}
			sql = "WITH FLOW_T AS ("
					+ "SELECT SUM(T.UL_BYTES+DL_BYTES)*1.0 FLOW"
					+ " FROM "+tableName+" T,(SELECT DISTINCT F.ID_ FROM DW_DM_NETYPE_INFO F WHERE F.NETTYPE_NAME = '"+netType+"') N"
					+ " WHERE N.ID_=T.RAT AND T.TIME_ID = "+time+" "+areaString+" AND T.APP_TYPE_ID IS NOT NULL ),"
					+ "USER_T AS ("
					+ "SELECT SUM(T.USER_COUNT)*1.0 GB_USER"
					+ " FROM "+tableName2+" T,(SELECT DISTINCT F.ID_ FROM DW_DM_NETYPE_INFO F WHERE F.NETTYPE_NAME = '"+netType+"') N"
					+ " WHERE N.ID_=T.RAT AND T.TIME_ID = "+time+" "+areaString+"),"
					+ "ALL_T AS ("
					+ "SELECT T.TIME_ID,T.APP_TYPE_ID,T.APP_TYPE_NAME,SUM(T.UL_BYTES+DL_BYTES)*1.0 FLOW,"
					+ "SUM(U.USER_COUNT)*1.0 GB_USER"
					+ " FROM "+tableName+" T,"+tableName1+" U,"
					+ "(SELECT DISTINCT F.ID_ FROM DW_DM_NETYPE_INFO F WHERE F.NETTYPE_NAME = '"+netType+"') N"
					+ " WHERE N.ID_=T.RAT AND U.RAT=N.ID_ AND T.TIME_ID in ("+time+","+json.getString("hbTime")+")"
					+ " AND U.TIME_ID = T.TIME_ID "+areaString+" AND U.AREA_ID = T.AREA_ID AND T.APP_TYPE_ID IS NOT NULL"
					+ " AND U.APP_TYPE_ID = T.APP_TYPE_ID AND T.APP_TYPE_ID IN ("+appTypeId+")"
					+ " GROUP BY T.TIME_ID, T.APP_TYPE_ID,T.APP_TYPE_NAME)"
					+ " SELECT D.TIME_ID,D.APP_TYPE_NAME,ROUND(D.FLOW/(1024*1024*1024),2) FLOW,"
					+ " TO_CHAR(ROUND((D.FLOW-F.FLOW)/F.FLOW*100,2),'fm99990.0099') FLOWHB,"
					+ "ROUND(D.GB_USER/10000,2) GB_USER,"
					+ "TO_CHAR(ROUND(D.FLOW / E.FLOW * 100, 2),'fm99990.0099') FLOWZB1,"
					+ "TO_CHAR(ROUND(D.GB_USER/G.GB_USER*100,2),'fm99990.0099') USERZB1"
					+ " FROM FLOW_T E,USER_T G,(SELECT * FROM ALL_T T WHERE T.TIME_ID = "+time+") D "
					+ " LEFT OUTER JOIN (SELECT * FROM ALL_T T WHERE T.TIME_ID = "+json.getString("hbTime")+") F"
					+ " ON D.APP_TYPE_NAME = F.APP_TYPE_NAME ORDER BY D.FLOW DESC";

			log.info("Oracle 查询一级业务流量分布  sql: " + sql.toString());

			List<JSONObject> list = jdbcTemplate.query(sql.toString(), new Object[]{} , new RowMapper<JSONObject>() {
				@Override
				public JSONObject mapRow(ResultSet resultSet, int i) throws SQLException {
					JSONObject json = new JSONObject();
					json.accumulate("time", resultSet.getString("TIME_ID"));
					json.accumulate("selName", resultSet.getString("APP_TYPE_NAME"));
					json.accumulate("flow", resultSet.getString("FLOW"));

					json.accumulate("flowHb", resultSet.getString("FLOWHB"));
					json.accumulate("user", resultSet.getString("GB_USER"));
					json.accumulate("allFlowZb", resultSet.getString("FLOWZB1"));

					json.accumulate("userZb", resultSet.getString("USERZB1"));
					return json;
				}
			});
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}

	@Override
	public List<JSONObject> querySel2FlowTop(JSONObject json, String timeCn, String time, String level, String netType, String ywTypeCombo, String areaId) throws Exception {
		try {
			/* 查询 按流量排序 信息 */
			String tableName = "DM_PTP_SE_AREA_L2_D";
			String tableName_l3 = "DM_PTP_SE_AREA_L3_D";
			String tableName1 = "DM_PTP_USER_DIST_APP_L2_D";
			String tableName2 = "DM_PTP_SE_AREA_L1_D";
			String tableName3 = "DM_PTP_USER_DIST_APP_L1_D";
			String sql = "";
			String areaString = "";
			if (!StringUtils.isNotBlank(areaId)) {
				areaString = " AND T.AREA_ID=85"/*+ProjectConstants.PROVINCEID*/;
			} else {
				areaString = " AND T.AREA_ID="+areaId;
			}
			if ("day".equals(level)) {
				tableName = "DM_PTP_SE_AREA_L2_D";
				tableName_l3 = "DM_PTP_SE_AREA_L3_D";
				tableName1 = "DM_PTP_USER_DIST_APP_L2_D";
				tableName2 = "DM_PTP_SE_AREA_L1_D";
				tableName3 = "DM_PTP_USER_DIST_APP_L1_D";
//			}else if ("month".equals(level)) {
//				tableName = "DM_PTP_SE_AREA_L2_M";
//				tableName_l3 = "DM_PTP_SE_AREA_L3_M";
//				tableName1 = "DM_PTP_USER_DIST_APP_L2_M";
//				tableName2 = "DM_PTP_SE_AREA_L1_M";
//				tableName3 = "DM_PTP_USER_DIST_APP_L1_M";
//			}else if ("week".equals(level)) {
//				tableName = "DM_PTP_SE_AREA_L2_W";
//				tableName_l3 = "DM_PTP_SE_AREA_L3_W";
//				tableName1 = "DM_PTP_USER_DIST_APP_L2_W";
//				tableName2 = "DM_PTP_SE_AREA_L1_W";
//				tableName3 = "DM_PTP_USER_DIST_APP_L1_W";
			}else if ("hour".equals(level)) {
				tableName = "DM_PTP_SE_AREA_L2_H";
				tableName_l3 = "DM_PTP_SE_AREA_L3_H";
				tableName1 = "DM_PTP_USER_DIST_APP_L2_H";
				tableName2 = "DM_PTP_SE_AREA_L1_H";
				tableName3 = "DM_PTP_USER_DIST_APP_L1_H";
			}

			String APP_SUBTYPE_MERGE_NAME = "t.APP_SUBTYPE_MERGE_NAME,";

			String ywWhere = " AND T.APP_TYPE_ID ="+(StringUtils.isBlank(ywTypeCombo)?"0":ywTypeCombo)+" ";
			if (StringUtils.isNotBlank(ywTypeCombo)) {
				if (ywTypeCombo.indexOf(",")>-1) {
					String[] ywType = ywTypeCombo.split(",");
					ywWhere = "";
					for (int i = 0; i < ywType.length; i++) {
						ywWhere += ""+ywType[i]+"";
						if (i+1<ywType.length) {
							ywWhere += ",";
						}
					}
					ywWhere = " AND T.APP_TYPE_ID NOT IN ("+ywWhere+") ";
				}
			}
			sql = "WITH FLOW_T AS ("
					+ "SELECT SUM(T.UL_BYTES + DL_BYTES)*1.0 FLOW FROM "+tableName2+" T,"
					+ "(SELECT DISTINCT F.ID_ FROM DW_DM_NETYPE_INFO F WHERE F.NETTYPE_NAME = '"+netType+"') N"
					+ " WHERE N.ID_ = T.RAT AND T.TIME_ID = "+time+" "+areaString+" "+ywWhere+" ),"
					+ "USER_T AS ("
					+ "SELECT SUM(T.USER_COUNT)*1.0 GB_USER FROM "+tableName3+" T,"
					+ "(SELECT DISTINCT F.ID_ FROM DW_DM_NETYPE_INFO F WHERE F.NETTYPE_NAME = '"+netType+"') N"
					+ " WHERE N.ID_ = T.RAT AND T.TIME_ID = "+time+" "+areaString+" "+ywWhere+"),"
					+ "ALL_T AS ("
					+ "SELECT T.TIME_ID,T.APP_TYPE_NAME,T.APP_SUBTYPE_MERGE_NAME,SUM(T.FLOW) FLOW,SUM(T.USER_COUNT) GB_USER,"
					+ "ROW_NUMBER() OVER(ORDER BY T.TIME_ID DESC,SUM(T.FLOW) DESC) AS ROWNUM_"
					+ " FROM (SELECT t.TIME_ID,t.AREA_ID,t.APP_TYPE_ID,t.APP_TYPE_NAME,t.APP_SUBTYPE_MERGE_ID,"+APP_SUBTYPE_MERGE_NAME
					+ "(t.UL_BYTES+t.DL_BYTES)*1.0 flow,u.USER_COUNT*1.0 USER_COUNT"
					+ " FROM (SELECT t.TIME_ID,t.AREA_ID,t.APP_TYPE_ID,"
					+ " t.APP_TYPE_NAME, t.APP_SUBTYPE_MERGE_ID, t.APP_SUBTYPE_MERGE_NAME,t.UL_BYTES , t.DL_BYTES,t.rat FROM "+tableName+" T,"
					+ "(SELECT DISTINCT A.APP_TYPE_OWN_ID,A.APP_SUBTYPE_MERGE_ID FROM DW_DM_SE_REL_MERGE_APPSUBTYPE A) A"
					+ " WHERE A.APP_TYPE_OWN_ID = T.APP_TYPE_ID AND T.APP_SUBTYPE_MERGE_ID = A.APP_SUBTYPE_MERGE_ID"
					+ " UNION ALL SELECT t.TIME_ID,t.AREA_ID,t.APP_TYPE_ID, t.APP_TYPE_NAME, t.APP_SUBTYPE_ID,"
					+ " t.APP_SUBTYPE_NAME, t.UL_BYTES , t.DL_BYTES,t.rat FROM "+tableName_l3+" T"
					+ " WHERE NOT EXISTS (SELECT 1 FROM (SELECT DISTINCT A.APP_TYPE_OWN_ID,A.APP_SUBTYPE_OWN_ID FROM DW_DM_SE_REL_MERGE_APPSUBTYPE A) A"
					+ " WHERE A.APP_TYPE_OWN_ID = T.APP_TYPE_ID AND T.APP_SUBTYPE_ID = A.APP_SUBTYPE_OWN_ID)) t,"+tableName1+" u,"
					+ "(SELECT DISTINCT F.ID_ FROM DW_DM_NETYPE_INFO F WHERE F.NETTYPE_NAME = '"+netType+"') N,"
					+ "(SELECT DISTINCT X.APP_SUBTYPE_MERGE_ID,X.APP_SUBTYPE_MERGE_NAME,X.APP_TYPE_OWN_ID,X.APP_TYPE_OWN_NAME,X.IS_HOTSE,X.SHOW_FLAG FROM DW_DM_SE_OWN_APPSUBTYPE X) a"
					+ " WHERE N.ID_ = T.RAT AND U.RAT = N.ID_ AND T.TIME_ID in ("+time+","+json.getString("hbTime")+")"
					+ " AND U.TIME_ID = T.TIME_ID "+areaString+" AND U.AREA_ID = T.AREA_ID "
					+ " AND U.APP_TYPE_ID = T.APP_TYPE_ID AND U.APP_SUBTYPE_MERGE_ID = T.APP_SUBTYPE_MERGE_ID"
					+ " and a.APP_SUBTYPE_MERGE_ID=t.APP_SUBTYPE_MERGE_ID and a.APP_TYPE_OWN_ID=t.APP_TYPE_ID AND A.SHOW_FLAG=1"// and a.IS_HOTSE = 1
					+ " "+ywWhere+") t GROUP BY T.TIME_ID, T.APP_TYPE_NAME, T.APP_SUBTYPE_MERGE_NAME)"
					+ " SELECT D.TIME_ID,D.APP_TYPE_NAME,D.APP_SUBTYPE_MERGE_NAME,ROUND(D.FLOW/(1024*1024*1024),2) FLOW,"
					+ "TO_CHAR(ROUND(D.GB_USER/10000,2),'fm99990.0099') GB_USER,TO_CHAR(ROUND(D.GB_USER/g.GB_USER*100,2),'fm99990.0099') USERZB,TO_CHAR(ROUND(D.FLOW/E.FLOW*100,2),'fm99990.0099') FLOWZB1,"
					+ "TO_CHAR(ROUND((D.FLOW - F.FLOW) / F.FLOW * 100, 2),'fm99990.0099') FLOWHB"
					+ " FROM FLOW_T E,USER_T G,(SELECT * FROM ALL_T T WHERE T.TIME_ID = "+time+") D "
					+ " LEFT OUTER JOIN (SELECT * FROM ALL_T T WHERE T.TIME_ID = "+json.getString("hbTime")+") F"
					+ " on (D.APP_TYPE_NAME = F.APP_TYPE_NAME AND D.APP_SUBTYPE_MERGE_NAME = F.APP_SUBTYPE_MERGE_NAME)"
					+ " where D.ROWNUM_ < 6 ORDER BY D.FLOW desc";
			log.info("Oracle 查询二级业务流量Top分布的sql："+sql);
			List<JSONObject> list = jdbcTemplate.query(sql.toString(), new Object[]{} , new RowMapper<JSONObject>() {
				@Override
				public JSONObject mapRow(ResultSet resultSet, int i) throws SQLException {
					JSONObject json = new JSONObject();
					json.accumulate("time", resultSet.getString("TIME_ID"));
					json.accumulate("selName", resultSet.getString("APP_TYPE_NAME"));
					json.accumulate("sel3Name", resultSet.getString("APP_SUBTYPE_MERGE_NAME"));
					json.accumulate("flow", resultSet.getString("FLOW"));

					json.accumulate("user", resultSet.getString("GB_USER"));
					json.accumulate("userZb", resultSet.getString("USERZB"));
					json.accumulate("allFlowZb", resultSet.getString("FLOWZB1"));
					json.accumulate("flowHb", resultSet.getString("FLOWHB"));

					return json;
				}
			});
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
}
