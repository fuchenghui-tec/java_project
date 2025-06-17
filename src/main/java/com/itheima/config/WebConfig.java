package com.itheima.config;

import com.itheima.interceptor.DemoInterceptor;
import com.itheima.interceptor.LoginCheckInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 */
@Slf4j
@Configuration  //声明当前类是一个配置类 等价于@Component
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private DemoInterceptor demoInterceptor;

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("注册拦截器....");
        // registry.addInterceptor(demoInterceptor).addPathPatterns("/**");
        // addPathPatterns：设置要拦截什么路径；excludePathPatterns：排除要拦截的路径
        // registry.addInterceptor(demoInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
        // /**代表任意级任意路径；/*代表一级任意路径
        // registry.addInterceptor(demoInterceptor).addPathPatterns("/*").excludePathPatterns("/login");

        // 如果没有写拦截路径，默认拦截所有
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}
