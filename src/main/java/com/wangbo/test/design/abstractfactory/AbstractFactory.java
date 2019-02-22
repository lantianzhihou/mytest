package com.wangbo.test.design.abstractfactory;

import com.wangbo.test.design.abstractfactory.ColorFactory.ColorEnum;
import com.wangbo.test.design.factory.pojo.DrawShapeFactory.ShapeNnum;
import com.wangbo.test.design.pojo.Color;
import com.wangbo.test.design.pojo.DrawShape;

public abstract class AbstractFactory {

    public abstract Color getColor(ColorEnum colorType);

    public abstract DrawShape getShape(ShapeNnum shapeType);
}
