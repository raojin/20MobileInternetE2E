package com.eastcom_sw.poc.rsservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eastcom_sw.poc.request.BaseRequest;
import com.eastcom_sw.poc.service.PocService;

/**
 * Created by jiangyi on 2017/5/26.
 */
@RestController
@RequestMapping("/index")
public class RsController {
    @Autowired
    private PocService pocService;
    
    private static Log log = LogFactory.getFactory().getInstance(RsController.class);
    @RequestMapping(value = "/queryBusinessTop", method = RequestMethod.POST )
    public List<JSONObject> queryBusinessTop(BaseRequest baseRequest) {
        List<JSONObject> list = pocService.getHotSelRankList(baseRequest);
    	return list;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public  Map<String,String> test(){
        Map<String,String> map = new HashMap<>();
        map.put("success","1");
        map.put("test","2");
        return map;
    }


    
}
