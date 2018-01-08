package com.eastcom_sw.poc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eastcom_sw.poc.dao.PocDao;
import com.eastcom_sw.poc.request.BaseRequest;
import com.eastcom_sw.poc.service.PocService;

import net.sf.json.JSONObject;
@Component
public class PocServiceImpl implements PocService{

	@Autowired
	private PocDao pocDao;


	@Override
	public List<Map<String,String>> queryFlowTop(BaseRequest baseRequest) {

		String[] arr = new String[]{};
		List<JSONObject> listYwKpi = new ArrayList<>();
		try {
			String time = baseRequest.getTimeId();
			String level = baseRequest.getLevel();
			String netType = baseRequest.getNetType();
			String city = baseRequest.getAreaId();
			String ywType = baseRequest.getYwType();
			String queryType = baseRequest.getQueryType();
			String stime = "";
			String hbTime = "";
			String tbTime = "";
			String timeCn = time;
			QueryLevel queryLevel = new QueryLevel(time, level, hbTime, tbTime).invoke();
			hbTime = queryLevel.getHbTime();
			tbTime = queryLevel.getTbTime();
			time = queryLevel.getTime();
			JSONObject json = new JSONObject();
			json.put("hbTime", hbTime);
			json.put("tbTime", tbTime);
			String flag = baseRequest.getFlag();

			if(StringUtils.equalsIgnoreCase("one",flag)){
				String ywTypeCombo = "";
				listYwKpi = pocDao.querySel1FlowTop(json, timeCn, time, level, netType,ywTypeCombo,city, queryType);
				arr = new String[] {"time","selName","flow","flowHb","user","allFlowZb","userZb"};
			} else if(StringUtils.equalsIgnoreCase("two",flag)){
				listYwKpi = pocDao.querySel2FlowTop(json, timeCn, time, level, netType,ywType,city);
				arr = new String[] {"time","selName","sel3Name","flow","user","userZb","allFlowZb","flowHb"};
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return returnMap(listYwKpi,arr);
	}


	/**
	 *
	 * @param list
	 * @param arr
	 * @return
	 */
	public List<Map<String,String>> returnMap(List<JSONObject> list, String[] arr){
		List<Map<String,String>> returnList = new ArrayList<>();
		if(list.size()>0){
			for(int i = 0; i < list.size();i++){
				Map<String,String> map = new HashMap<>();
				JSONObject jsonObject = list.get(i);
				for(String field:arr) {
					String value = jsonObject.getString(field+"");
					if("null".equals(value)) {
						map.put(field, "");
					}else {
						map.put(field, value);
					}
				}
				returnList.add(map);
			}
			return returnList;
		} else{
			return null;
		}
	}

	private class QueryLevel {
		private String time;
		private String level;
		private String hbTime;
		private String tbTime;

		public QueryLevel(String time, String level, String hbTime, String tbTime) {
			this.time = time;
			this.level = level;
			this.hbTime = hbTime;
			this.tbTime = tbTime;
		}

		public String getTime() {
			return time;
		}

		public String getHbTime() {
			return hbTime;
		}

		public String getTbTime() {
			return tbTime;
		}

		public QueryLevel invoke() throws ParseException {
			String stime;
			if (StringUtils.isNotBlank(time)) {
				SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
				if ("day".equals(level)) {
					Date date = dFormat.parse(time.replaceAll("-",""));
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.DATE, -1);
					stime = dFormat.format(calendar.getTime())+"0000";
					time = time.replaceAll("-","")+"0000";
					hbTime = stime;
					calendar.setTime(date);
					calendar.add(Calendar.MONTH, -1);
					tbTime = dFormat.format(calendar.getTime())+"0000";
//				} else if ("week".equals(level)) {
//					JSONObject json = handleWeek(time);
//					tbTime = json.getString("tbTime");
//					hbTime = json.getString("hbTime");
////					stime = json.getString("stime");
//					time = json.getString("startDate")+"0000";
//				}else if ("month".equals(level)) {
//					Date date = dFormat.parse(time.replaceAll("-","")+"01");
//					Calendar calendar = Calendar.getInstance();
//					calendar.setTime(date);
//					calendar.add(Calendar.MONTH,  -1);
//					stime = dFormat.format(calendar.getTime());
//					stime = stime.substring(0, 6)+"010000";
//					time = time.replaceAll("-","")+"010000";
//					hbTime = stime;
//					calendar.setTime(date);
//					calendar.add(Calendar.MONTH, -12);
//					tbTime = dFormat.format(calendar.getTime());
//					tbTime = tbTime.substring(0, 6)+"010000";
				}else if ("hour".equals(level) || "15min".equals(level)) {
					dFormat = new SimpleDateFormat("yyyyMMddHHmm");
					Date date = dFormat.parse(time.replaceAll("-",""));
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.HOUR_OF_DAY, -1);
					stime = dFormat.format(calendar.getTime());
					time = time.replaceAll("-","");
					hbTime = stime;
					calendar.setTime(date);
					calendar.add(Calendar.DATE, -1);
					tbTime = dFormat.format(calendar.getTime());
				}
			}
			return this;
		}
	}
}
