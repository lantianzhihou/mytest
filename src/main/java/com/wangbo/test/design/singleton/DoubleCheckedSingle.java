package com.wangbo.test.design.singleton;

import java.io.Serializable;

public class DoubleCheckedSingle implements Serializable {

    private final String name;

    private DoubleCheckedSingle() {
        this.name = "wangbo";
    }

    private volatile static DoubleCheckedSingle instance;

    public static DoubleCheckedSingle getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedSingle.class) {
                if (instance == null) {
                    instance = new DoubleCheckedSingle();
                }
            }
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1624134725397438659L;

}
