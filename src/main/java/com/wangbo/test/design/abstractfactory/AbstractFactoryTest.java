package com.wangbo.test.design.abstractfactory;

import com.wangbo.test.design.abstractfactory.ColorFactory.ColorEnum;
import com.wangbo.test.design.abstractfactory.FactoryProducer.Product;
import com.wangbo.test.design.factory.pojo.DrawShapeFactory.ShapeNnum;
import com.wangbo.test.design.pojo.Color;
import com.wangbo.test.design.pojo.DrawShape;

public class AbstractFactoryTest {

    public static void main(String[] args) {
        AbstractFactory colorFactory = FactoryProducer.getFactory(Product.COLOR);
        if (colorFactory != null) {
            Color color = colorFactory.getColor(ColorEnum.RED);
            if (color != null) {
                color.fill();
            }
        }
        AbstractFactory shapeFactory = FactoryProducer.getFactory(Product.SHAPE);
        if (shapeFactory != null) {
            DrawShape shape = shapeFactory.getShape(ShapeNnum.CIRCLE);
            if (shape != null) {
                shape.draw();
            }
        }
    }
}
