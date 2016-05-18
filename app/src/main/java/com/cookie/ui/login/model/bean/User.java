package com.cookie.ui.login.model.bean;

import android.util.Log;

import com.cookie.ui.login.model.mInf;
import com.cookie.ui.util.network.bean.code;
import com.cookie.ui.util.network.bean.data;
import com.cookie.ui.util.network.bean.result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

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
        OkHttpUtils
                .post()
                .url("http://txb.360yeke.com/user/sendCode")
                .addParams("tel", "13075917094")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            Gson gson=new Gson();// 使用gson解析；
                            java.lang.reflect.Type type = new TypeToken<code>() {}.getType();
                            code mcode=gson.fromJson(response, type);
                            data mdata=mcode.getData();
                            Log.v("fuqi",mdata.toString());
                            User user=new User();
                            user.setTel("13075917094");
                            user.setName("fuqi");
                            user.setPwd("123456");
                            user.setId(10000l);
                            _ICallBack.onResult(user);
                        }catch (Exception e){
                            Log.v("fuqi",e.getMessage());
                        }

                    }
                });

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
