package com.wangbo.test.enums;

/**
 * <一句话功能简述> 
 * <1.enum这个关键字，可以理解为跟class差不多，即为单独的类; 
 * 	2.枚举的实例可以理解为class new 出来的实例对象,对应的实例是有限的; 
 * 	3.不允许在其他类型中创建该类对象,构造方法被默认强制是私有的;
 * 	
 * 	4.所有的枚举对象都有2个默认的属性，一个是name，一个是ordinal;
 * 	5.不能对系统自带的name属性以及ordinal属性,在自定义构造函数里面赋值
 * 	6.添加了自定义的构造方法,自定义属性typeName,以及自定义方法valueOfType;
 * 	7.通过自定义的构造方法给自定义的属性初始化值的>
 * 
 * @author 姓名 工号
 * @version [版本号, 2018年1月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public enum SimpleEnum {
	
	/**
	 * 使用自定义的构造方法
	 * public static final SPIRNG = new SimpleEnum("春天", 3);
	 */
	SPIRNG("春天", 3), SUMMER("夏天", 2);

	private String typeName;
	private int ordinal;

	private SimpleEnum(String typeName, int ordinal) {
	/**
	 *	SimpleEnum没有继承父类
	 * 	super(String name, int ordinal);
	 */
		this.typeName = typeName;
		this.ordinal = ordinal;
	}
	
	public int getOrdinal() {
		return ordinal;
	}

	public String getTypeName() {
		return typeName;
	}

	public static SimpleEnum valueOfType(String typeName) {
		for (SimpleEnum simpleEnum : SimpleEnum.values()) {
			if (simpleEnum.typeName.equals(typeName)) {
				return simpleEnum;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "name: " + this.name() + "---typeName: " + this.getTypeName() + "---ordinal: " + this.getOrdinal()
				+ "---superOrdinal: " + this.ordinal();
	}

}
