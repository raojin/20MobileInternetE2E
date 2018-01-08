package com.eastcom_sw.poc.domain.callinglog;

/**
 * Created by admin on 2017/5/27.
 */
public class QryCallingLogReq {
    private String service_name;
    private String start_time;
    private String end_time;

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
