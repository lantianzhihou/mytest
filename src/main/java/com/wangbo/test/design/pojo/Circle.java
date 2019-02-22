package com.wangbo.test.design.pojo;

public class Circle implements DrawShape {

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }

}
