package com.wangbo.test.design.abstractfactory;

import com.wangbo.test.design.factory.pojo.DrawShapeFactory;

public class FactoryProducer {

    private FactoryProducer() {
    }

    public static AbstractFactory getFactory(Product factoryTypoe) {
        AbstractFactory factory = null;
        switch (factoryTypoe) {
        case COLOR:
            factory = new ColorFactory();
            break;
        case SHAPE:
            factory = new DrawShapeFactory();
            break;
        default:
            break;
        }
        return factory;
    }

    public enum Product {
        COLOR, SHAPE
    }
}
