package com.itheima.aop;

import com.itheima.entity.OperateLog;
import com.itheima.mapper.OperateLogMapper;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 为增删改业务记录操作日志
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    @Autowired
    private OperateLogMapper operateLogMapper;
    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(com.itheima.annotation.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //1.构造日志记录数据
        OperateLog operateLog = new OperateLog();

        //操作人--登录人id--在登录成功的时候将id存入到了令牌中，所以可以解析令牌获取登录id
        String token = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(token);
        Object empId = claims.get("id");
        operateLog.setOperateEmpId((Integer) empId);

        //操作时间
        operateLog.setOperateTime(LocalDateTime.now());

        //执行方法的全类名
        operateLog.setClassName(joinPoint.getTarget().getClass().getName());

        //执行方法名
        operateLog.setMethodName(joinPoint.getSignature().getName());

        //方法运行参数
        operateLog.setMethodParams(Arrays.toString(joinPoint.getArgs()));

        //返回值
        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();        //执行目标方法
        operateLog.setReturnValue(result.toString());

        //执行耗时
        long end = System.currentTimeMillis();
        operateLog.setCostTime(end - begin);


        //2.存入日志表operate_log
        operateLogMapper.insert(operateLog);

        //3.返回结果
        return result;
    }
}
