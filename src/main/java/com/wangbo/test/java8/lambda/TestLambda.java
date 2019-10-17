package com.wangbo.test.java8.lambda;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class TestLambda {

    @Test
    public void displayAnnotation() {
        Annotation[] declaredAnnotations = TestLambda.class.getDeclaredAnnotations();
        for (Annotation annotation : declaredAnnotations) {
            System.out.println(annotation.getClass()); // class com.sun.proxy.$Proxy1
            System.out.println(annotation.getClass().getDeclaredAnnotations()); // []
            System.out.println(Component.class == annotation.getClass()); // false
            System.out.println(Component.class == annotation.annotationType()); //true
        }
    }
    
    @Test
    public void displayMethod() throws NoSuchMethodException, SecurityException {
        Method method = TestLambda.class.getMethod("displayMethod");
        System.out.println(method.getDeclaredAnnotations());
    }
    
    @Test
    public void displayMethodRef() {
        List<Car> cars = Lists.newArrayList(new Car("新车1号"), new Car("新车2号"), new Car("新车3号"), new Car("新车4号"));
        // 构造器引用：它的语法是Class::new，或者更一般的Class< T >::new实例如下：
        Car police1 = Car.create(() -> new Car("土匪车"));
        Car police2 = Car.create(Car::new);
        police2.setName("警车");
        // 静态方法引用：它的语法是Class::static_method，实例如下：
        // cars.forEach(car -> Car.collide(car));
        cars.forEach(Car::collide);
        // 特定对象的方法引用(方法有参数)：它的语法是instance::method实例如下：
        cars.forEach((Car another) -> police1.follow(another));
        cars.forEach(police2::follow);
        // 特定类的任意对象的方法引用(方法无参数)：它的语法是Class::method实例如下
        cars.forEach(Car::repair);
    }

    @Test
    public void displayLamada() {
        List<String> names = Lists.newArrayList("Google ", "Runoob ", "Taobao ", "Baidu ", "Sina ");

        System.out.println("使用 Java 8 语法: ");
        sortUsingJava8(names);
        names.forEach(System.out::print);
        System.out.print('\r');
    }

    /**
     * 1.lambda表达式 lambda表达式的语法格式： 
     *      (parameters) -> expression或 (parameters) ->{statements; } 
     * (1).可选类型声明： 不需要声明参数类型，编译器可以统一识别参数值。 
     * (2).可选的参数圆括号： 一个参数无需定义圆括号，但无参数或者多个参数需要定义圆括号。 
     * (3).可选的大括号： 如果statements主体仅仅包含了一个语句，就不需要使用大括号。
     * (4).可选的返回关键字： 如果主体只有一个语句，表达式有返回值，则编译器会自动返回值，不需要return；大括号需要指定明表达式返回了一个数值。
     * 
     * 注意： Lambda 表达式免去了使用匿名类的麻烦，并且给予Java简单但是强大的函数化的编程能力。
     * 接口要求必须是函数式接口，如果其中有两个方法则lambda表达式会编译错误。 
     * lambda 表达式只能引用 final 修饰的变量，这就是说不能在 lambda 内部修改定义在域外的局部变量，否则会编译错误
     */
    // 使用 java 8 排序
    private void sortUsingJava8(List<String> names) {
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
    }

}
