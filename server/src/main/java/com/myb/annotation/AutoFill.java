package com.myb.annotation;

import com.myb.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 -- 用于标记mapper层中的insert和update方法
 * 自动填充创建时间、更新时间、创建人id和更新人id
 **/
/*
* 指定该自定义注解的保留策略，即该注解在什么时候生效
* RUNTIME：该注解会被保留到运行时
* SOURCE：该注解仅保留在源代码中，编译时回被丢弃
* CLASS：保留到编译后的字节文件中，但运行时会被JVM丢弃（默认值）
*/
@Retention(RetentionPolicy.RUNTIME)

/*
* 指定自定义注解用于修饰哪些Java元素
* TYPE：类、接口、枚举、注解类型。
* FIELD：成员变量。
* PARAMETER：方法参数。
* CONSTRUCTOR：构造方法。
*/
@Target(ElementType.METHOD)
public @interface AutoFill {
    // 设定该自定义注解使用时应该传入的参数为OperationType中的INSERT、UPDATE
    // 区分当前被注解的方法执行的时插入还是更新操作，时AOP切面根据不同类型操作类型执行不同的填充逻辑
    OperationType value();
}
