package com.cookie.ui.util.network.bean;

import java.util.List;

/**
 * Created by weifuqi on 16/5/18.
 */
public class data {
    public result  getResults() {
        return result;
    }

    public void setResults(result result) {
        this.result = result;
    }

    private result result;
    public String toString()
    {
        return "result [result=" + result   + "]";
    }
}
