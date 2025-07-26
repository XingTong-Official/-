package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
@Aspect
public class AutoFillAspect {
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPoint(){}

    @Before("autoFillPoint()")
    public void autoFill(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AutoFill annotation = method.getAnnotation(AutoFill.class);
        OperationType value = annotation.value();

        Object[] args = joinPoint.getArgs();
        Object object = args[0];

        Long currentId = BaseContext.getCurrentId();
        LocalDateTime now = LocalDateTime.now();

        try {
            if(value==OperationType.INSERT){
                Method createTime = object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method updateTime = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method createUser = object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method updateUser = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                createTime.invoke(object,now);
                updateTime.invoke(object,now);
                createUser.invoke(object,currentId);
                updateUser.invoke(object,currentId);
            }
            else if(value==OperationType.UPDATE){
                Method updateTime = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method updateUser = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                updateTime.invoke(object,now);
                updateUser.invoke(object,currentId);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error(e.getMessage());
        }
    }
}
