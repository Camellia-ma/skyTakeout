package com.myb.constant;

/**
 * 公共字段自动填充相关常量 -- 用于比对切面获取的方法名称是否为需要填充公告字段的方法
 **/

public class AutoFillConstant {
    /* 切面获取的实体类中的方法名称 */
    public static final String SET_CREATE_TIME = "setCreateTime";  // 设置创建时间
    public static final String SET_UPDATE_TIME = "setUpdateTime";  // 设置更新时间
    public static final String SET_CREATE_USER = "setCreateUser";  // 设置创建用户
    public static final String SET_UPDATE_USER = "setUpdateUser";  // 设置更新用户
}
