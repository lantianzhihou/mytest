package com.wangbo.test.codeLump;

public class FatherCodeLump {

    private double height = 1.73;

    // public double getHeight() {
    // return height;
    // }
    //
    // public void setHeight(double height) {
    // this.height = height;
    // }

    {
        // addName("构造代码块");
        height = 1.82;
        // System.out.println("构造代码块！name = " + name);
    }

    public FatherCodeLump() {
        System.out.println("父类构造方法@");
    }

    public static void main(String[] args) {
        String str1 = "HelloWorld";
        String str2 = "Hello" + "World";
        System.out.println(str1 == str2);// true
        String valueOf = String.valueOf("World");
        String str3 = "Hello" + valueOf;
        System.out.println(str1 == str3);// false
    }
}
