package com.xinou.quickadmin.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.xinou.quickadmin.bean.Pager;
import com.xinou.quickadmin.controller.BaseController;
import com.xinou.quickadmin.controller.IngoreCheck;
import com.xinou.quickadmin.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 新闻服务
 * Created by shizhida on 16/10/10.
 */
@RequestMapping("/news/")
@Controller
public class NewsApi extends BaseController {


    @Autowired
    NewsService newsService;

    @RequestMapping("delete")
    public void newsDel(@RequestParam int id,HttpServletResponse response){
        renderUpdate(response,newsService.delete(id));
    }

    @IngoreCheck
    @RequestMapping("list")
    public void newsList(@RequestParam int page,HttpServletResponse response){
        Pager list = newsService.list(page);
        renderJson(response,list);
    }

    @IngoreCheck
    @RequestMapping("info")
    public void info(@RequestParam int id,HttpServletResponse response){
        Map<String,Object> n = newsService.revert(id);
        renderJson(response,n);
    }


    @RequestMapping("add")
    public void add (@RequestBody JSONObject news,HttpServletResponse response){
        String msg = newsService.add(news);
        if(msg!=null){
            renderFail(response,msg);
            return;
        }else{
            renderJson(response,"成功添加新闻");
        }
    }
    @RequestMapping("update")
    public void update (@RequestBody JSONObject news,HttpServletResponse response){
        String msg = newsService.update(news);
        if(msg!=null){
            renderFail(response,msg);
            return;
        }else{
            renderJson(response,"成功添加新闻");
        }
    }


}
