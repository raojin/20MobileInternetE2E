package com.eastcom_sw.poc.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.eastcom_sw.frm.core.entity.Page;
import com.eastcom_sw.poc.util.DateTools;
import com.eastcom_sw.poc.request.BaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eastcom_sw.poc.dao.PocDao;
import com.eastcom_sw.poc.service.PocService;

import net.sf.json.JSONObject;
@Component
public class PocServiceImpl implements PocService {

	@Autowired
	private PocDao pocDao;


	@Override
	public List<JSONObject> getModelList(BaseRequest baseRequest) {
		String level=baseRequest.getLevel();
		String time=baseRequest.getTime();
		String areaId=baseRequest.getAreaId();
		String netType=baseRequest.getNetType();
		time = getTime(level, time);
		List list= pocDao.terminalModel(level, time, areaId, "", netType);
		return list;
	}

	@Override
	public List<JSONObject> getBrandList(BaseRequest baseRequest) {

		String level=baseRequest.getLevel();
		String time=baseRequest.getTime();
		String areaId=baseRequest.getAreaId();
		String netType=baseRequest.getNetType();
		time = getTime(level, time);
		List list= pocDao.terminalBrand(level, time, areaId, "", netType);
		return list;
	}

	@Override
	public Page getHostList(BaseRequest baseRequest) {

		String level=baseRequest.getLevel();
		String time=baseRequest.getTime();
		String areaId=baseRequest.getAreaId();
		String netType=baseRequest.getNetType();
		String brand=baseRequest.getBrand();
		int pageNo = Integer.parseInt(baseRequest.getPage());
		int limit = baseRequest.getLimitToInt();
		time = getTime(level, time);
		Page page= pocDao.terminalHotPage(level, time,areaId, netType, brand,pageNo,limit);

		return page;
	}

	/**
	 * 根据level确定时间
	 * @param level
	 * @param time
	 * @return
	 */
	private String getTime(String level, String time) {
		if("day".equals(level)){
			time=time+"0000";
		}else if("week".equals(level)){
			JSONObject timeJson = this.handleWeek(time);
			time = timeJson.getString("startDate")+"0000";
			//time=fetchCommonDWDao.getStartDayForWeek(time.replaceAll("-",""))+"0000";//以周-作为当前所在周的第一天
		}else if("month".equals(level)){
			time=time+"010000";
		}
		return time;
	}

	/**
	 * 处理周粒度日期
	 * @param weekStr
	 * @return
	 */
	public JSONObject handleWeek(String weekStr){
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
		JSONObject json = new JSONObject();
		json.put("time", weekStr);
		String[] timeArr = { "0", "0" };
		int year = 0;
		int week = 0;
		if (weekStr.indexOf("-") > -1) {
			timeArr = weekStr.split("-");
		} else {
			timeArr[0] = weekStr.substring(0, 4);
			timeArr[1] = weekStr.substring(4, 6);
		}
		year = Integer.valueOf(timeArr[0]).intValue();
		week = Integer.valueOf(timeArr[1]).intValue();

		Date startDate = DateTools.getFirstDayOfWeek(year, week);
		json.put("startDate", dFormat.format(startDate));
		json.put("endDate", dFormat.format(DateTools.getLastDayOfWeek(year, week, 0)));

		json.put("tbTime", new StringBuilder().append(year - 1).append("").append(week < 10 ? new StringBuilder().append("0").append(week).toString() : Integer.valueOf(week)).append("000000").toString());
		json.put("tbDate", dFormat.format(DateTools.getFirstDayOfWeek(year - 1, week)));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(5, -7);

		json.put("hbTime", new StringBuilder().append(DateTools.getWeekOfYearFmt(calendar.getTime())).append("000000").toString());
		json.put("hbDate", dFormat.format(DateTools.getFirstDayOfWeek(calendar.getTime())));

		calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(5, -49);

		json.put("lastTime_7", new StringBuilder().append(DateTools.getWeekOfYearFmt(calendar.getTime())).append("000000").toString());
		json.put("lastDate_7", dFormat.format(DateTools.getFirstDayOfWeek(calendar.getTime())));

		calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(5, -70);

		json.put("lastTime_10", new StringBuilder().append(DateTools.getWeekOfYearFmt(calendar.getTime())).append("000000").toString());
		json.put("lastDate_10", dFormat.format(DateTools.getFirstDayOfWeek(calendar.getTime())));

		return json;
	}
}
