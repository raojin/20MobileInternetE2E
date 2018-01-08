package com.eastcom_sw.poc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.eastcom_sw.frm.common.utils.ParseJSONObject;
import com.eastcom_sw.frm.core.entity.Page;
import com.eastcom_sw.frm.core.entity.PageObject;
import net.sf.json.JSONArray;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.eastcom_sw.frm.core.dao.jpa.DaoImpl;
import com.eastcom_sw.poc.dao.PocDao;

import net.sf.json.JSONObject;



@Component
public class PocDaoImpl extends DaoImpl implements PocDao{

	/**
	 * 终端品牌
	 */
	@Override
	public List<JSONObject> terminalBrand(String timeType, String time, String areaId,String cityId, String netType) {
		String findStr = "";
		String leftStr="";
		String table = getTableName(timeType);
		if(!netType.equals("") && netType !=null && netType !=""){
			findStr +=  " and  NT.NETTYPE_NAME='"+netType+"'";
		}
		leftStr += "left join DW_DM_NETYPE_INFO NT ON C.RAT = NT.ID_";
		StringBuffer sql = new StringBuffer();
		sql.append("select * from(select terminal_brand AS NAME, round(1.0*(sum(UL_BYTES) + sum(DL_BYTES))/1024/1024/1024 ,2) FLOW ");
		sql.append(" from "+table +" C ");
		sql.append(leftStr);
		sql.append("  WHERE C.AREA_ID = "+areaId+" AND C.TIME_ID = "+time+findStr);
		sql.append(" and terminal_brand is not null group by terminal_brand ORDER BY FLOW DESC nulls last) WHERE ROWNUM < 11");
		log.info("终端品牌sql(oracle数据库：============="+sql.toString());
		String[] arr = new String[] {"NAME","FLOW"};
		return getList(sql.toString(),arr);
	}

	/**
	 * 终端型号
	 */
	@Override
	public List<JSONObject> terminalModel(String timeType, String time, String areaId,String cityId, String netType) {
		String findStr = "";
		String leftStr="";
		String table = getTableName(timeType);
		if(!netType.equals("") && netType !=null && netType !=""){
			findStr +=  " and  NT.NETTYPE_NAME='"+netType+"'";
		}
		leftStr += "left join DW_DM_NETYPE_INFO NT ON C.RAT = NT.ID_";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM(select TERMINAL_MODEL AS NAME, round(1.0*(sum(UL_BYTES) + sum(DL_BYTES))/1024/1024/1024 ,2) FLOW ");
		sql.append(" from "+table +" C ");
		sql.append(leftStr);
		sql.append("  WHERE C.AREA_ID = "+areaId+" AND C.TIME_ID = "+time+findStr);
		sql.append(" and TERMINAL_MODEL is not null group by TERMINAL_MODEL ORDER BY FLOW DESC nulls last) WHERE ROWNUM < 11");
		log.info("终端型号sql(oracle数据库：==========="+sql.toString());
		String[] arr = new String[] {"NAME","FLOW"};
		return getList(sql.toString(),arr);
	}


	//热门终端
	@Override
	public Page terminalHotPage(String timeType, String time, String areaId, String netType, String brand, int pageNo, int limit) {
		List list=new ArrayList();
		String table1 = "";
		String table2 = "";
		String field= "";
		String field1 = "";
		String findStr = "";
		String outTime="";
		if(timeType.equals("day") ){
			outTime = " TO_CHAR(TO_DATE(TIME_ID,'YYYYMMDDHH24MI'),'YYYY-MM-DD')";
			table1 = "DM_PTP_NET_TM_D";
			table2 = "DM_PTP_USER_DIST_TM_D";
		}else if(timeType.equals("week")){
			outTime = " SUBSTR(TIME_ID,0,4)||'-'||SUBSTR(TIME_ID,4,2)";
			table1 = "DM_PTP_NET_TM_W";
			table2 = "DM_PTP_USER_DIST_TM_W";
		}else if(timeType.equals("month")){
			outTime = " TO_CHAR(TO_DATE(TIME_ID,'YYYYMMDDHH24MI'),'YYYY-MM')";
			table1 = "DM_PTP_NET_TM_M";
			table2 = "DM_PTP_USER_DIST_TM_M";
		}else if ("hour".equals(timeType)) {
			outTime = " TO_CHAR(TO_DATE(TIME_ID,'YYYYMMDDHH24MI'),'YYYY-MM-DD HH24:MI')";
			table1 = "DM_PTP_NET_TM_H";
			table2 = "DM_PTP_USER_DIST_TM_H";
		}
		if(!netType.equals("") && netType !=null && netType !=""){
			findStr +=  " And c.rat =(SELECT DISTINCT F.ID_ FROM DW_DM_NETYPE_INFO F WHERE F.NETTYPE_NAME = '"+netType+"')";

		}
		StringBuffer findBrand=new StringBuffer("");
		if(!brand.equals("") && brand !=null && brand !=""){
			findBrand.append(" and  c.TERMINAL_BRAND like '%"+brand+"%' ");
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select "+outTime+" time_id,area_id,IMEI_TAC,AREA_NAME,TERMINAL_BRAND AS TMBRAND,TERMINAL_MODEL AS TMMODEL,TERMINAL_NETTYPE AS TMNETTYPE,TERMINAL_SUPORT AS TMSUPPORTSYS,");
		sql.append("sumavg,sumcnt,ATTACHSUCCRATE AS ATT_SUCCRATE ,TAUSUCCRATE AS RAU_SUCCRATE,DNSSUCCRATE AS DNS_SUCCRATE,"
				+ "decode(tcp_,0,0,round(tcp_1/tcp_*100,2)) AS TCP_SUCCRATE,HTTPDISPLAYSUCCRATE AS SESSION_SUCCRATE,HTTPDISPLAYDUR AS SESSION_DUR,SPEED_DL");
		sql.append(" from( select C.time_id,C.area_id,C.AREA_NAME,C.TERMINAL_BRAND,C.TERMINAL_MODEL,C.TERMINAL_NETTYPE,C.TERMINAL_SUPORT,C.IMEI_TAC,");
		sql.append("sum(T.USER_COUNT) sumcnt, round(sum(C.DL_BYTES+C.UL_BYTES) / 1024 / 1024 / 1024, 2) sumavg,");
		sql.append("DECODE(sum(ATTACH_REQ_CNT),0,0,ROUND(sum(ATTACH_SUCC_CNT)/sum(ATTACH_REQ_CNT) * 100,2)) as attachSuccRate,DECODE(sum(TAU_REQ_CNT),0,0,ROUND(SUM(TAU_SUCC_CNT)/SUM(TAU_REQ_CNT) * 100,2)) as tauSuccRate,");
		sql.append("DECODE(sum(DNS_REQ_CNT),0,0,ROUND(sum(DNS_REQ_SUCC)/sum(DNS_REQ_CNT) *100,2)) as dnsSuccRate,sum(TCP_SYN+TCP_SYN_ACK) as tcp_,sum(TCP_ACK_SUCC+TCP_SYN_ACK_SUCC) as tcp_1,");
		sql.append("DECODE(sum(HTTP_RESP_CNT),0,0,ROUND(sum(HTTP_RESP_SUCC_CNT)/sum(HTTP_RESP_CNT) * 100,2)) as httpDisplaySuccRate, DECODE(sum(HTTP_RESP_SUCC_CNT),0,0,ROUND(sum(HTTP_RESP_DURATION)/ sum(HTTP_RESP_SUCC_CNT),2)) as httpDisplayDur,");
		sql.append("DECODE(sum(DL_ONLINE_TIME),0,0,ROUND(SUM(DL_BYTES)/SUM(DL_ONLINE_TIME),2)) as speed_dl");
		sql.append("  from "+table1+" C left join "+table2+" T on T.TIME_ID = C.TIME_ID AND T.AREA_ID = C.AREA_ID AND T.IMEI_TAC = C.IMEI_TAC ");
		sql.append(" WHERE C.area_id = "+areaId+" and C.TIME_ID = "+time+findStr+findBrand.toString()+"  and c.TERMINAL_MODEL is not null ");
		sql.append(" group by C.time_id,C.area_id,C.AREA_NAME,C.TERMINAL_BRAND,C.TERMINAL_MODEL,C.TERMINAL_NETTYPE,C.TERMINAL_SUPORT,C.IMEI_TAC");
		sql.append(") order by sumavg desc nulls last");
		String[] arr = new String[] {"TIME_ID","AREA_ID","IMEI_TAC","AREA_NAME","TMBRAND","TMMODEL","TMNETTYPE","TMSUPPORTSYS","SUMAVG","SUMCNT","ATT_SUCCRATE",
				"RAU_SUCCRATE","DNS_SUCCRATE","TCP_SUCCRATE","SESSION_SUCCRATE","SESSION_DUR","SPEED_DL"};
		log.info("热门终端sql:"+sql.toString());
		if (pageNo == -1 && limit == -1) {
			List<JSONObject> rellist =getList(sql.toString(),arr);
			int n = rellist.size();
			return new PageObject(JSONArray.fromObject(rellist), n, n,n+1);
		} else {
			Page page = pagedSQLQuery(sql.toString(), pageNo, limit,list.toArray());
			List rellist = page.getElements();
			List<JSONObject> jsonList = ParseJSONObject.parse(this.getParseStr(), rellist);
			page.setElements(jsonList);
			return page;
		}
	}

	/**
	 * 根据条件不同 获取表名
	 * @param timeType
	 * @return
	 */
	private String getTableName(String timeType) {
		String table = "";
		if(timeType.equals("day") ){
			table = "DM_PTP_NET_TM_D";
		}else if(timeType.equals("week")){
			table = "DM_PTP_NET_TM_W";
		}else if(timeType.equals("month")){
			table = "DM_PTP_NET_TM_M";
		}
		return table;
	}


	/**
	 * 封装返回数据list
	 * @param sql
	 * @param arr
	 * @return
	 */
	private List<JSONObject> getList(String sql, final String[] arr) {
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
	 * @Description 解析查出的字段
	 * @return
	 */
	public List<String> getParseArr(){
		List<String> list = new ArrayList<>();
		list.add("TIME_ID");
		list.add("AREA_ID");
		list.add("IMEI_TAC");
		list.add("AREA_NAME");
		list.add("TMBRAND");
		list.add("TMMODEL");
		list.add("TMNETTYPE");
		list.add("TMSUPPORTSYS");
		list.add("SUMAVG");
		list.add("SUMCNT");
		list.add("ATT_SUCCRATE");
		list.add("RAU_SUCCRATE");
		list.add("DNS_SUCCRATE");
		list.add("TCP_SUCCRATE");
		list.add("SESSION_SUCCRATE");
		list.add("SESSION_DUR");
		list.add("SPEED_DL");

		return list;
	}

	/**
	 * @Description 解析查出的字段
	 * @return
	 */
	public String getParseStr(){
		StringBuffer parseStr = new StringBuffer();
		parseStr.append("TIME_ID,AREA_ID,IMEI_TAC,AREA_NAME,TMBRAND,TMMODEL,");
		parseStr.append("TMNETTYPE,TMSUPPORTSYS,SUMAVG,SUMCNT,");
		parseStr.append("ATT_SUCCRATE,");
		parseStr.append("RAU_SUCCRATE,DNS_SUCCRATE,");
		parseStr.append("TCP_SUCCRATE,");
		parseStr.append("SESSION_SUCCRATE,");
		parseStr.append("SESSION_DUR,SPEED_DL");

		return parseStr.toString();
	}

}
