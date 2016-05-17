package com.cookie.ui.login.model;

import com.cookie.ui.login.model.bean.User;

/**
 * Created by weifuqi on 16/5/16.
 */
public interface mInf {

    public void getLoginData(ICallBack _ICallBack);

    public interface ICallBack{
        public void onResult(User user);
    }
    void setLogin(String tel,String pwd);
    User getLogin();

}
