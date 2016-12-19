package com.xinou.quickadmin;

import com.alibaba.druid.pool.DruidDataSource;
import com.xinou.quickadmin.auth.AuthInterceptor;
import com.xinou.quickadmin.auth.impl.DefaultUserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by shizhida on 16/10/9.
 */
@ComponentScan
@EnableAutoConfiguration
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean("jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(@Autowired DruidDataSource dataSource){
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthInterceptor interceptor = new AuthInterceptor();
        interceptor.setAuth(new DefaultUserAuth());
        registry.addInterceptor(interceptor);
    }

    @Bean("dataSource")
    public DruidDataSource getDataSource(){
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl("jdbc:mysql://database:3306/quickadmin?useUnicode=true&characterEncoding=UTF-8");
        ds.setUsername("username");
        ds.setPassword("password");

        ds.setInitialSize(0);
        ds.setMaxActive(20);
        ds.setMinIdle(0);
        ds.setMaxWait(10000);

        ds.setTestOnBorrow(false);
        ds.setTestOnReturn(false);
        ds.setTestWhileIdle(true);

        ds.setTimeBetweenEvictionRunsMillis(60000);
        ds.setMinEvictableIdleTimeMillis(25200000);

        ds.setRemoveAbandoned(true);
        ds.setRemoveAbandonedTimeout(1800);
        ds.setLogAbandoned(true);
        return ds;
    }
}
