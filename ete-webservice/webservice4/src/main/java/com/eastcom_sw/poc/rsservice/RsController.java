package com.eastcom_sw.poc.rsservice;


import com.eastcom_sw.frm.core.entity.Page;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eastcom_sw.poc.request.BaseRequest;
import com.eastcom_sw.poc.service.PocService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/index")
public class RsController {
    @Autowired
    private PocService pocService;
    
    private static Log log = LogFactory.getFactory().getInstance(RsController.class);
    @RequestMapping(value = "/queryBrandList", method = RequestMethod.POST )
    public List<JSONObject> queryBrandList(BaseRequest baseRequest) {
        List<JSONObject> list = pocService.getBrandList(baseRequest);
    	return list;
    }

    @RequestMapping(value = "/queryModelList", method = RequestMethod.POST )
    public List<JSONObject> queryModelList(BaseRequest baseRequest) {
        List<JSONObject> list = pocService.getModelList(baseRequest);
        return list;
    }

    @RequestMapping(value = "/queryHostList",method = RequestMethod.POST)
    public Page queryHostList(BaseRequest baseRequest){
        Page page = pocService.getHostList(baseRequest);
        return page;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public  Map<String,String> test(){
        Map<String,String> map = new HashMap<>();
        map.put("success","1");
        map.put("test","2");
        return map;
    }


    
}
