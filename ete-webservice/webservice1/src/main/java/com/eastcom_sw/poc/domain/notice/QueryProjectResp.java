package com.eastcom_sw.poc.domain.notice;

import java.util.List;

import com.eastcom_sw.poc.domain.BaseResponse;

/**
 * Created by admin on 2017/5/12.
 */
public class QueryProjectResp extends BaseResponse {
    private List<GisProjectInfo> list;

    public List<GisProjectInfo> getList() {
        return list;
    }

    public void setList(List<GisProjectInfo> list) {
        this.list = list;
    }
}
