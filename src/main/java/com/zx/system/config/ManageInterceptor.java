package com.zx.system.config;

import com.zx.system.entity.Constant;
import com.zx.system.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台管理端拦截器
 * Created by Administrator on 2018/5/14.
 */
public class ManageInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(ManageInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("------preHandle------");
        //获取session
        String UserId = (String) SessionUtil.getSessionAttribute(Constant.LOGIN_USER_ID);
        //判断用户ID是否存在，不存在就跳转到登录界面
        if(UserId == null){
            logger.info("------:跳转到login页面！");
            response.sendRedirect(request.getContextPath()+"/manage/sys/login");
            return false;
        }else{
            SessionUtil.setSessionAttribute(Constant.LOGIN_USER_ID,UserId);
            return true;
        }
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
