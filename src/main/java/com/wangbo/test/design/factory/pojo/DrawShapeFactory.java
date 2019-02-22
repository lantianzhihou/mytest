package com.wangbo.test.design.factory.pojo;

import com.wangbo.test.design.abstractfactory.AbstractFactory;
import com.wangbo.test.design.abstractfactory.ColorFactory.ColorEnum;
import com.wangbo.test.design.pojo.Color;
import com.wangbo.test.design.pojo.DrawShape;

public class DrawShapeFactory extends AbstractFactory {

    @Override
    public Color getColor(ColorEnum colorType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DrawShape getShape(ShapeNnum shapeType) {
        DrawShape shape = null;
        switch (shapeType) {
        case CIRCLE:
            shape = new Circle();
            /**
             * 对该对象进行编辑
             */
            break;
        case RECTANGLE:
            shape = new Rectangle();
            /**
             * 对该对象进行编辑
             */
            break;
        case SQUARE:
            shape = new Square();
            /**
             * 对该对象进行编辑
             */
            break;

        default:
            break;
        }
        return shape;
    }

    public enum ShapeNnum {
        CIRCLE, RECTANGLE, SQUARE;
    }

    private static class Circle implements DrawShape {

        @Override
        public void draw() {
            System.out.println("Inside Circle::draw() method.");
        }

    }

    private static class Rectangle implements DrawShape {

        @Override
        public void draw() {
            System.out.println("Inside Rectangle::draw() method.");
        }

    }

    private static class Square implements DrawShape {

        @Override
        public void draw() {
            System.out.println("Inside Square::draw() method.");
        }

    }

}
