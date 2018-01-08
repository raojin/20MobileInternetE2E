package com.eastcom_sw.poc.domain.redis;

/**
 * Created by admin on 2017/5/27.
 */
public class SetKeyReq {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
