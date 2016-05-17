package com.cookie.ui.login.Presenter.imp;

import android.content.pm.PackageInfo;

import com.cookie.ui.login.Presenter.pinf;
import com.cookie.ui.login.model.bean.User;
import com.cookie.ui.login.model.mInf;
import com.cookie.ui.login.view.vinf;

/**
 * Created by weifuqi on 16/5/16.
 */
public class pimp implements pinf {
    mInf _mInf;
    vinf _vinf;


    public  pimp(vinf _vinf) {
        this._vinf=_vinf;
        _mInf=new User();
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void performOnClick() {

        _mInf.getLoginData(new mInf.ICallBack() {
            @Override
            public void onResult(User user) {
                _vinf.setData(user);
            }
        });

    }

    @Override
    public void Login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                _mInf.setLogin(_vinf.getTel(),_vinf.getPwd());
                _vinf.toMainActivity(_mInf.getLogin());

            }
        }).start();;
    }
}
