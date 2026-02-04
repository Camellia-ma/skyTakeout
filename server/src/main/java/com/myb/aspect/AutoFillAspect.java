package com.myb.aspect;

import com.myb.annotation.AutoFill;
import com.myb.constant.AutoFillConstant;
import com.myb.context.BaseContext;
import com.myb.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面 -- 实现公共字段自动填充逻辑
 **/

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /* 设置拦截的类或方法 -- 配置切入点,指定哪些方法会被AOP拦截 */
    @Pointcut("execution(* com.myb.mapper.*.*(..)) && @annotation(com.myb.annotation.AutoFill)")
    public void autoFillPointCut() {}

    /* 前置通知 -- 在insert或update方法执行前执行自动填充公共字段的操作 */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("进行公共字段自动填充....");
        // 1、读取当前拦截方法上通过注解标记的数据库操作类型
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); // 方法签名对象
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);  // 获取方法上的注解对象
        OperationType operationType = null;
        if (autoFill != null) {
            operationType = autoFill.value();
        }
        // 2、获取被拦截方法的参数 -- 实体对象
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];   // 获取传入的对象
        // 3、准备填充公共字段的数据
        LocalDateTime now = LocalDateTime.now(); // 当前系统时间
        Long userId = BaseContext.getCurrentId();   // 操作人id
        // 4、根据数据库操作类型为对应的属性进行反射赋值
        if(operationType == OperationType.INSERT){
            // 为四个公共值赋值
            try{
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);
                // 通过放射为对象属性赋值
                setCreateTime.invoke(entity,now);
                setUpdateTime.invoke(entity,now);
                setCreateUser.invoke(entity,userId);
                setUpdateUser.invoke(entity,userId);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(operationType == OperationType.UPDATE){
            try{
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,userId);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
