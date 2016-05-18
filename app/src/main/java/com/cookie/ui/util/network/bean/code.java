package com.cookie.ui.util.network.bean;

import java.util.List;

/**
 * Created by weifuqi on 16/5/18.
 */
public class code {
    private String code;

    public  data  getData() {
        return data;
    }

    public void setData(data mdata) {
        this.data = mdata;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private data data;
    private String timestamp;


    //{"code":200,"data":{"result":{"result":"0","resultDesc":"","sessionId":"27974206569806285658308509329042"}},"timestamp":1463560614978}
}
