package com.cookie.ui.login.model.bean;

import com.cookie.ui.login.model.mInf;

/**
 * Created by weifuqi on 16/5/16.
 */
public class User  implements mInf {

    private String tel;
    private String pwd;
    private String name;
    private Long id;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    @Override
    public void getLoginData(final ICallBack _ICallBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user=new User();
                user.setTel("13075917094");
                user.setName("fuqi");
                user.setPwd("123456");
                user.setId(10000l);
                _ICallBack.onResult(user);
            }
        }).start();
    }

    @Override
    public void setLogin(String tel, String pwd) {
        this.tel=tel;
        this.pwd=pwd;
    }

    @Override
    public User getLogin() {
        return this;
    }
}
