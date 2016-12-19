package com.xinou.quickadmin.controller.view;

import com.xinou.quickadmin.controller.IngoreCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理系统页面，整个管理系统仅有登录页面和管理页面两个页面。
 * Created by shizhida on 16/12/13.
 */
@Controller
@RequestMapping("/")
public class AdminViews {

    @IngoreCheck
    @RequestMapping("login")
    public String signin(){
        return "signin.html";
    }

    @IngoreCheck
    @RequestMapping("index")
    public String login() {return "admin.html";}
}
