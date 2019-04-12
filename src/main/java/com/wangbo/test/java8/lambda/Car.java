package com.wangbo.test.java8.lambda;

import java.util.function.Supplier;

public class Car {

    private Double price;
    
    private String name;

    public Car() {
        System.out.println("car的无参构造法！");
    }

    public Car(String name) {
        super();
        this.name = name;
    }

    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println(this + "Following the " + another.toString());
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Car [name=" + name + "]";
    }
}
