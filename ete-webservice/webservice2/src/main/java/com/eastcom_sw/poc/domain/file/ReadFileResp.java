package com.eastcom_sw.poc.domain.file;

import com.eastcom_sw.poc.domain.BaseResponse;

/**
 * Created by admin on 2017/5/27.
 */
public class ReadFileResp extends BaseResponse{
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
