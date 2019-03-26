package com.wangbo.test.concurrentAndmutiThread.pojo;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;

    private int age;

    private List<String> list = new ArrayList<String>();

    public Person() {
    }

    public Person(String name, int age) {
        this.setName(name);
        this.setAge(age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
