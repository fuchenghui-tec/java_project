package com.itheima.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
// @WebFilter(urlPatterns = "/*")
// @WebFilter("/*")    //设置拦截路径 /*  代表拦截所有请求
// @WebFilter("/login") //拦截具体路径
// @WebFilter("/emps/*")   //目录拦截
public class DemoFilter implements Filter {
    //初始化方法，在web服务器启动的时候会触发一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("DemoFilter....init....");
    }

    //销毁方法，在web服务器正常关闭的时候会触发一次
    @Override
    public void destroy() {
        log.info("DemoFilter....destroy....");
    }

    //每次拦截到了请求，就会触发该方法，会调用多次
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("DemoFilter....doFilter....放行前逻辑");

        //放行
        filterChain.doFilter(servletRequest, servletResponse);

        log.info("DemoFilter....doFilter....放行后逻辑");
    }
}
