package com.itheima.filter;


import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
// @WebFilter("/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 0.强转对象
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 1.获取请求url。
        // StringBuffer url = req.getRequestURL(); // eg:  http://localhost:8080/login
        // log.info("url = {}", url);
        String uri = req.getRequestURI();       // eg:  /login
        log.info("uri = {}", uri);

        // 2.判断请求url中是否包含login，如果包含，说明是登录操作，放行。
        if (uri.contains("login")) {
            //登录操作，放行。
            chain.doFilter(request, response);
            //【注意】一定要结束当前请求，不应该继续往下执行代码逻辑
            return;
        }

        // 3.获取请求头中的令牌（token）。
        String token = req.getHeader("token");

        // 4.判断令牌是否存在，如果不存在，响应401。
        if (token == null) {
            resp.setStatus(401);
            log.error("令牌为空！！！");
            // throw new BusinessException("未登录，请先登录！");
            //【注意】一定要结束当前请求，不应该继续往下执行代码逻辑
            return;
        }

        // 5.解析token，如果解析失败，响应401 。
        try {
            Claims claims = JwtUtils.parseJWT(token);
            log.info("claims = {}",claims);
        } catch (Exception e) {
            resp.setStatus(401);
            log.error("令牌解析失败:{}", e.getMessage());
            // throw new BusinessException("未登录，请先登录！");
            //【注意】一定要结束当前请求，不应该继续往下执行代码逻辑
            return;
        }

        // 6.放行。
        chain.doFilter(request, response);
    }
}
