package com.eastcom_sw.poc.service.impl;

import java.util.*;


import com.eastcom_sw.poc.request.BaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eastcom_sw.poc.dao.PocDao;
import com.eastcom_sw.poc.service.PocService;

import net.sf.json.JSONObject;
@Component
public class PocServiceImpl implements PocService{

	@Autowired
	private PocDao pocDao;


	@Override
	public List<JSONObject> getTrendAnalysis(BaseRequest baseRequest) {
		List<JSONObject> listYwKpi = new ArrayList<>();
		try {
			listYwKpi = pocDao.getTrendAnalysis(baseRequest.getLevel(),baseRequest.getTimeId(),baseRequest.getAreaId(),
					""/*baseRequest.getCityId()*/,baseRequest.getNetType());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return listYwKpi;
	}

	@Override
	public List<JSONObject> getBusiness(BaseRequest baseRequest) {
		String level = baseRequest.getLevel();
		String timeId = baseRequest.getTimeId();
		String areaId = baseRequest.getAreaId();
		//		String cityId = baseRequest.getCityId();
		String cityId ="";
		String netType = baseRequest.getNetType();
//		String top = baseRequest.getTop();
		String top ="10";
		String field_type = baseRequest.getField_type();
		String app_type = baseRequest.getApp_type();
//		String threshold = baseRequest.getThreshold();
		String threshold ="";
		String app_type_id = baseRequest.getApp_type_id();


		List<JSONObject> list = new ArrayList<>();
		try{
			list =  pocDao.getSel3Business(level, timeId, areaId,
					cityId, netType, app_type, field_type,
					threshold, top, app_type_id, null);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@Override
	public List<JSONObject> getBusinessType(BaseRequest baseRequest) {

		String level = baseRequest.getLevel();
		String timeId = baseRequest.getTimeId();
		String areaId = baseRequest.getAreaId();
//		String cityId = baseRequest.getCityId();
		String cityId ="";
		String netType = baseRequest.getNetType();
//		String top = baseRequest.getTop();
		String top = "10";
		String field_type = baseRequest.getField_type();

		String appTypeIdStr = "";//全量数据
		List<JSONObject> list = new ArrayList<>();
		try{
			list =  pocDao.getBusinessType(level, timeId, areaId,
					cityId, netType, field_type, top,null,appTypeIdStr);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@Override
	public List<JSONObject> getBussinessTrendList(BaseRequest baseRequest) {

		String level = baseRequest.getLevel();
		String timeId = baseRequest.getTimeId();
		String areaId = baseRequest.getAreaId();
		//		String cityId = baseRequest.getCityId();
		String cityId ="";
		String netType = baseRequest.getNetType();
		String app_type_id = baseRequest.getApp_type_id();
		String app_subtype_id = baseRequest.getApp_subtype_id();
		String field_type = baseRequest.getField_type();
//		String threshold = baseRequest.getThreshold();
		String threshold="";
		String app_type = baseRequest.getApp_type();
		List<JSONObject> list = new ArrayList<>();
		try {
			list = pocDao.getBussinessTrendList(level, timeId, areaId,
					cityId, netType, app_type_id, app_subtype_id,
					field_type, threshold, null, app_type);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return list;
	}
}
