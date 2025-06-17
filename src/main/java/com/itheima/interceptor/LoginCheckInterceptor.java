package com.itheima.interceptor;

import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录校验拦截器
 */
@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求url。
        String uri = request.getRequestURI();
        log.info("uri = {}", uri);

        // 2.判断请求url中是否包含login，如果包含，说明是登录操作，放行。
        if (uri.contains("login")){
            return true;
        }

        // 3.获取请求头中的令牌（token）。
        String token = request.getHeader("token");

        // 4.判断令牌是否存在，如果不存在，响应401。
        if (token == null) {
            log.warn("令牌不存在！");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            //不放行
            return false;
        }

        // 5.解析token，如果解析失败，响应401 。
        try {
            Claims claims = JwtUtils.parseJWT(token);
            log.info("claims = {}", claims);
        } catch (Exception e) {
            log.error("令牌解析失败：{}", e.getMessage());
            // e.printStackTrace();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            //不放行
            return false;
        }

        // 6.放行。
        return true;
    }
}
