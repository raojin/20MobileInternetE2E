package com.eastcom_sw.poc.domain.callinglog;

/**
 * Created by admin on 2017/5/27.
 */
public class InserLogReq {
    private String service_name;
    private String exe_status;
    private long exe_cost_time;
    private String request_params;
    private String reponse_result;

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getExe_status() {
        return exe_status;
    }

    public void setExe_status(String exe_status) {
        this.exe_status = exe_status;
    }

    public long getExe_cost_time() {
        return exe_cost_time;
    }

    public void setExe_cost_time(long exe_cost_time) {
        this.exe_cost_time = exe_cost_time;
    }

    public String getRequest_params() {
        return request_params;
    }

    public void setRequest_params(String request_params) {
        this.request_params = request_params;
    }

    public String getReponse_result() {
        return reponse_result;
    }

    public void setReponse_result(String reponse_result) {
        this.reponse_result = reponse_result;
    }
}
