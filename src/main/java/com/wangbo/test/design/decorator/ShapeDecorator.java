package com.wangbo.test.design.decorator;

import com.wangbo.test.design.pojo.DrawShape;

public abstract class ShapeDecorator implements DrawShape {

    protected DrawShape decoratedShape;

    public ShapeDecorator(DrawShape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public void draw() {
        decoratedShape.draw();
    }

}
