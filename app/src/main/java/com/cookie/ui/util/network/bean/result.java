package com.cookie.ui.util.network.bean;

/**
 * Created by weifuqi on 16/5/18.
 */
public class result {
    private String result;
    private String resultDesc;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    private String sessionId;

    public String toString()
    {
        return "result [result=" + result + ", resultDesc=" + resultDesc
                + ", sessionId=" + sessionId  + "]";
    }
}
