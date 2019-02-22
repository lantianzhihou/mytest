package com.wangbo.test.design.decorator;

import com.wangbo.test.design.pojo.DrawShape;

public class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(DrawShape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw() {
        setRedBorder(decoratedShape);
        super.draw();
    }

    private void setRedBorder(DrawShape decoratedShape) {
        System.out.println("Border Color: Red");
    }

}
