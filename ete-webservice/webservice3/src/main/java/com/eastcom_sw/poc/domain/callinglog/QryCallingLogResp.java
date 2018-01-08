package com.eastcom_sw.poc.domain.callinglog;


import java.util.List;

import com.eastcom_sw.poc.domain.BaseResponse;

/**
 * Created by admin on 2017/5/12.
 */
public class QryCallingLogResp extends BaseResponse {
    private List<CallingLog> logs;

    public List<CallingLog> getLogs() {
        return logs;
    }

    public void setLogs(List<CallingLog> logs) {
        this.logs = logs;
    }
}
