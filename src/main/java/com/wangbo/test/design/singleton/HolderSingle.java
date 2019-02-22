package com.wangbo.test.design.singleton;

import java.io.Serializable;

/**
 * 只有当调用getInstance方法时才会显式调用HolderSingleHolder类，加载class对象时会创建静态常量对象INSTANCE。
 * 由于classLoader加载类的机制，保证了初始化INSTANCE的线程只有一个，不存在多线程安全问题。
 * 
 * @author 姓名 工号
 * @version [版本号, 2018年11月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HolderSingle implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4557377805181099914L;

    private HolderSingle() {
    }

    public static HolderSingle getInstance() {
        return HolderSingleHolder.INSTANCE;
    }

    private static class HolderSingleHolder {
        private static final HolderSingle INSTANCE = new HolderSingle();
    }

}
