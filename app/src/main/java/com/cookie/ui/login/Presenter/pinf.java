package com.cookie.ui.login.Presenter;

import com.cookie.ui.login.model.bean.User;
import com.cookie.ui.login.view.vinf;

/**
 * Created by weifuqi on 16/5/16.
 */
public interface pinf {


    void onCreate();//创建p
    void performOnClick();//执行方法，得到data，并且传递给view，操作都依赖鱼接口
    void Login();//登入方法
}




