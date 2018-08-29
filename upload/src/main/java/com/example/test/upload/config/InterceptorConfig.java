package com.example.test.upload.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author ceshi
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/8/2917:26
 */
@Component
public class InterceptorConfig implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("-------------preHandle-----------");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            logger.info("------request被拦截------" + request.getRequestURI());
            // 被拦截的URL 强制重定向到登录页面
            response.sendRedirect("/login-page");
            return false;
        } else {
            logger.info("------request被放行------");
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        logger.info("----------------postHandle----------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        logger.info("------------afterCompletion------------");
    }
}
