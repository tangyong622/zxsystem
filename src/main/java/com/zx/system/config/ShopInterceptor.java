package com.zx.system.config;

import com.zx.system.util.JsonResult;
import com.zx.system.util.StringUtils;
import com.zx.system.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前台用户 端拦截器
 * Created by Administrator on 2018/5/14.
 */
public class ShopInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(ShopInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("------preHandle------");
        //获取session
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            logger.info("------:没有登录信息");
            return false;
        }
        JsonResult result = TokenUtils.getCache(token);
        if(result.getCode() != 0){
            logger.info("------:"+result.getMsg());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
