package com.wangbo.test.design.decorator;

import com.wangbo.test.design.pojo.Circle;
import com.wangbo.test.design.pojo.DrawShape;
import com.wangbo.test.design.pojo.Rectangle;

public class DecoratorPatternDemo {

    public static void main(String[] args) {
        DrawShape shape1 = new Circle();
        DrawShape shape2 = new Rectangle();

        DrawShape shapeDecorator1 = new RedShapeDecorator(shape1);
        DrawShape shapeDecorator2 = new RedShapeDecorator(shape2);

        shape1.draw();
        System.out.println("============分割线============");
        shapeDecorator1.draw();

        System.out.println("============分割线============");

        shape2.draw();
        System.out.println("============分割线============");
        shapeDecorator2.draw();
    }

}
