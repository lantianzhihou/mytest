package com.wangbo.test.design.pojo;

public class Rectangle implements DrawShape {

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }

}
