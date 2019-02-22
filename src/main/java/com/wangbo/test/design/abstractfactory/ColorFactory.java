package com.wangbo.test.design.abstractfactory;

import com.wangbo.test.design.factory.pojo.DrawShapeFactory.ShapeNnum;
import com.wangbo.test.design.pojo.Color;
import com.wangbo.test.design.pojo.DrawShape;

public class ColorFactory extends AbstractFactory {

    @Override
    public Color getColor(ColorEnum colorType) {
        Color color = null;
        switch (colorType) {
        case RED:
            color = new Red();

            break;
        case GREEN:
            color = new Green();

            break;
        case BLUE:
            color = new Blue();

            break;

        default:
            break;
        }
        return color;
    }

    @Override
    public DrawShape getShape(ShapeNnum shapeType) {
        return null;
    }

    public enum ColorEnum {
        RED, GREEN, BLUE
    }

    private static class Red implements Color {

        @Override
        public void fill() {
            System.out.println("Inside Red::fill() method.");
        }

    }

    private static class Green implements Color {

        @Override
        public void fill() {
            System.out.println("Inside Green::fill() method.");
        }

    }

    private static class Blue implements Color {

        @Override
        public void fill() {
            System.out.println("Inside Blue::fill() method.");
        }

    }
}
