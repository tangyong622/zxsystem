package com.zx.system.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2018/5/14.
 */
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    /**
     * <p>Title:</p>
     * <p>Description:重写增加自定义拦截器的注册，某一个拦截器需要先注册进来，才能工作</p>
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //后台管理端
        registry.addInterceptor(new ManageInterceptor()).addPathPatterns("/manage/**");
        //前台用户端
        registry.addInterceptor(new ShopInterceptor()).addPathPatterns("/shop/**");
        super.addInterceptors(registry);
    }
}
