package com.xinou.quickadmin.service;

import com.alibaba.fastjson.JSONObject;
import com.xinou.quickadmin.bean.Pager;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by shizhida on 16/10/9.
 */
@Service("newsService")
public class NewsService extends BaseService{

    public Pager list(int page) {
        Pager pager = pager(page, "select * from news order by id desc");
        for (Map<String, Object> news : pager.getRows()) {
            Timestamp date = (Timestamp) news.get("date");
            news.put("date",new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(date.getTime())));
        }
        return pager;
    }

    public Map<String,Object> revert(int id) {
        Map<String, Object> news = revert("news", id);
        Timestamp date = (Timestamp) news.get("date");
        news.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(date.getTime())));
        news.put("date_yymm", new SimpleDateFormat("yyyy/MM").format(new java.util.Date(date.getTime())));
        news.put("date_dd", new SimpleDateFormat("dd").format(new java.util.Date(date.getTime())));
        return news;
    }

    public String add(JSONObject news) {

        String title = news.getString("title");
        String cover = news.getString("cover");
        String desc = news.getString("description");
        String content = news.getString("content");
        Timestamp time = new Timestamp(System.currentTimeMillis());

        if(checkData(news)!=null) return checkData(news);
        jdbcTemplate.update("insert into news (title,cover,description,content,date) value (?,?,?,?,?)",
                title, cover, desc, content, time);

        return null;

    }

    public String update(JSONObject news) {

        int id = news.getInteger("id");
        String title = news.getString("title");
        String cover = news.getString("cover");
        String desc = news.getString("description");
        String content = news.getString("content");
        if(checkData(news)!=null) return checkData(news);
        jdbcTemplate.update("update news set title=?,cover=?,description=?,content=? where id=?",
                title,cover,desc,content,id);

        return null;
    }

    private String checkData(JSONObject news){
        String title = news.getString("title");
        if(nullcheck(title)) return "标题不能为空";
        String cover = news.getString("cover");
        if(nullcheck(cover)) return "封面不能为空";
        return null;
    }

    /**
     * 获取热点新闻
     * @return
     */
    public List<Map<String, Object>> getHotNews() {
        List<Map<String,Object>> news = jdbcTemplate.queryForList("select * from news order by id desc limit 0,6");
        return news;
    }

    public Pager getFrontListPager(int page){
        Pager pager = pager(page, 6, "select * from news order by id desc");
        for (Map<String, Object> news : pager.getRows()) {
            Timestamp date = (Timestamp) news.get("date");
            news.put("date",new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(date.getTime())));
            news.put("date_yymm",new SimpleDateFormat("yyyy/MM").format(new java.util.Date(date.getTime())));
            news.put("date_dd",new SimpleDateFormat("dd").format(new java.util.Date(date.getTime())));
        }
        return pager;
    }

    public Map<String, Object> getNearBy(int id,int where) {
        String sql = "select * from news where id ";
        if(where>0){
            sql+=">? order by id asc limit 0,1";
        }else{
            sql+="<? order by id desc limit 0,1 ";
        }

        List<Map<String,Object>> l = jdbcTemplate.queryForList(sql,id);
        if(l.size()==0)
            return null;
        else{
            return l.get(0);
        }
    }

    public String delete(int id) {
        delete("news",id);
        return null;
    }
}
