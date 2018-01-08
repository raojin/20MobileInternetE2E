package com.eastcom_sw.poc.rsservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //  http://localhost:7081/index/querySel1FlowTop?level=day&timeId=20171211&areaId=1766&netType=4G&queryType=999999%2C1%2C7%2C15%2C5%2C8
    @RequestMapping(value = "/queryFlowTop", method = RequestMethod.POST )
    public List<Map<String,String>> querySel2FlowTop(BaseRequest baseRequest) {
        List<Map<String,String>> listMap = pocService.queryFlowTop(baseRequest);
    	return listMap;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public  Map<String,String> test(){
        Map<String,String> map = new HashMap<>();
        map.put("success","1");
        map.put("test","2");
        return map;
    }


    
}
