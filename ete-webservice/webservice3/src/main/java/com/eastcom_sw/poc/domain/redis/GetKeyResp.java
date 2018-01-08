package com.eastcom_sw.poc.domain.redis;


import java.util.List;

import com.eastcom_sw.poc.domain.BaseResponse;

/**
 * Created by admin on 2017/5/12.
 */
public class GetKeyResp extends BaseResponse {
    String value = "";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
