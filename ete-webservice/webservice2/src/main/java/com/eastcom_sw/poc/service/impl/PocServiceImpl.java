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
	public List<JSONObject> getHotSelRankList(BaseRequest baseRequest) {
		List<JSONObject> listYwKpi = new ArrayList<>();
		try {
			listYwKpi = pocDao.getHotSelRankList(baseRequest.getLevel(), baseRequest.getTimeId(), baseRequest.getAreaId(),
					baseRequest.getCityId(), baseRequest.getNetType(), baseRequest.getField_type(), baseRequest.getApp_type_id(),baseRequest.getApp_type(),baseRequest.getTop(),baseRequest.getLogo_type());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return listYwKpi;
	}


}
