package com.eastcom_sw.poc.request;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class BaseRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5885178271770947888L;
	
	private String module;
	private String start;
	private String limit;
	private String page;
	
	private String uuid;
	private String level;
	private String levelName;
	private String time;
	private String timeId;
	private String startTime;
	private String endTime;
	private String startTimeFm;
	private String endTimeFm;
	private String areaId;
	private String areaName;
	private String areaNameFull;
	private String cityId;
	private String cityName;
	private String cellId;
	private String cellName;
	private String netType;
	private String temp;
	private String flag;
	
	private String top;
	private String filters;
	private String filterType;
	private String sord;
	private String sidx;

	private String queryType;
	private String ywType;

	private String field_type;
	private String app_type_id;
	private String app_type;
	private String logo_type;

	private String brand;

	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTimeId() {
		return timeId;
	}
	public void setTimeId(String timeId) {
		this.timeId = timeId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartTimeFm() {
		return startTimeFm;
	}
	public void setStartTimeFm(String startTimeFm) {
		this.startTimeFm = startTimeFm;
	}
	public String getEndTimeFm() {
		return endTimeFm;
	}
	public void setEndTimeFm(String endTimeFm) {
		this.endTimeFm = endTimeFm;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaNameFull() {
		return areaNameFull;
	}
	public void setAreaNameFull(String areaNameFull) {
		this.areaNameFull = areaNameFull;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCellId() {
		return cellId;
	}
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	public String getNetType() {
		return netType;
	}
	public void setNetType(String netType) {
		this.netType = netType;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getYwType() {
		return ywType;
	}

	public void setYwType(String ywType) {
		this.ywType = ywType;
	}

	public String getField_type() {
		return field_type;
	}

	public void setField_type(String field_type) {
		this.field_type = field_type;
	}

	public String getApp_type_id() {
		return app_type_id;
	}

	public void setApp_type_id(String app_type_id) {
		this.app_type_id = app_type_id;
	}

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}

	public String getLogo_type() {
		return logo_type;
	}

	public void setLogo_type(String logo_type) {
		this.logo_type = logo_type;
	}


	public void setStartToInt(int str) {
	}

	public void setLimitToInt(int str) {
	}

	public int getStartToInt() {
		int start = -1;
		if (StringUtils.isNotBlank(this.start)) {
			start = Integer.valueOf(this.start).intValue();
		}

		if (StringUtils.isNotBlank(this.page)) {
			start = (Integer.valueOf(this.page).intValue() - 1) * Integer.valueOf(this.limit).intValue();
		}

		return start;
	}

	public int getLimitToInt() {
		int limit = -1;
		if (StringUtils.isNotBlank(this.limit)) {
			limit = Integer.valueOf(this.limit).intValue();
		}

		return limit;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
}
