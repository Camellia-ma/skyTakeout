package com.myb.context;

/**
 * 这个类负责创建一个新线程存储当前操作的人员的 id
 **/

public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /* 设置当前正在操作人员的 id */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /* 获取当前正在操作的人员的 id */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    /* 删除线程 */
    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
