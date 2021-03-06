package com.xinou.quickadmin.auth.impl;

import com.xinou.quickadmin.auth.AuthResult;
import com.xinou.quickadmin.auth.UserAuth;
import com.xinou.quickadmin.bean.CurrentUser;
import org.springframework.stereotype.Component;

/**
 * 默认的用户校验模块
 * Created by shizhida on 16/12/19.
 */
@Component("defaultUserAuth")
public class DefaultUserAuth implements UserAuth {


    @Override
    public AuthResult check(String path, CurrentUser user) {

        if(path.equals("/error"))
            return AuthResult.success;

        if(user==null)
        {
            return AuthResult.error("用户未登录，请刷新页面以进入登录页");
        }
        else{
            if(user.getType()==CurrentUser.TYPE_ADMIN){
                return AuthResult.success;
            }else{
                return AuthResult.error("用户权限不足，请以管理员身份登录");
            }
        }

    }
}
