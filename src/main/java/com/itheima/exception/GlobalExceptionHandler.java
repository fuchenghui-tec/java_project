package com.itheima.exception;

import com.itheima.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice   //作用：用来捕获控制器controller层抛出的所有异常
public class GlobalExceptionHandler {

    @ExceptionHandler     //指定处理何种异常
    public Result doException(Exception ex){
        log.error("程序出错了...", ex);
        return Result.error("出错了，请联系管理员！");
    }

    @ExceptionHandler
    public Result handleBusinessException(BusinessException e){
        log.error("程序运行出错啦 .... ", e);
        return Result.error(e.getMessage());
    }
}
