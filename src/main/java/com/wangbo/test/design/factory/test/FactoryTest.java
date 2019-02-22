package com.wangbo.test.design.factory.test;

import com.wangbo.test.design.factory.pojo.DrawShapeFactory;
import com.wangbo.test.design.factory.pojo.DrawShapeFactory.ShapeNnum;

public class FactoryTest {

    public static void main(String[] args) {
        DrawShapeFactory shapeFactory = new DrawShapeFactory();
        shapeFactory.getShape(ShapeNnum.CIRCLE).draw();
        shapeFactory.getShape(ShapeNnum.RECTANGLE).draw();
        shapeFactory.getShape(ShapeNnum.SQUARE).draw();

    }
}
