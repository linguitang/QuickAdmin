package com.xinou.quickadmin.auth;

import com.xinou.quickadmin.bean.CurrentUser;

/**
 * Created by shizhida on 16/12/19.
 */
public interface UserAuth {

    public AuthResult check(String path,CurrentUser user);
}
