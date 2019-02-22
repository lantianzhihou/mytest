package com.wangbo.test.reflectTest;

import java.util.Arrays;

/**
 * <Java动态修改Enum实例!http://www.hankcs.com/program/java/enum-java-examples-of-dynamic-modification.html>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2018年1月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Human {
	
	public static void main(String[] args) {
	    EnumBuster<HumanState> buster =
	            new EnumBuster<HumanState>(HumanState.class,
	                                       Human.class);
	    HumanState ANGRY = buster.make("ANGRY");
	    buster.addByValue(ANGRY);
	    System.out.println(Arrays.toString(HumanState.values()));
	     
	    Human human = new Human();
	    human.sing(ANGRY);
	}
	
	public void sing(HumanState state) {
		switch (state) {
		case HAPPY:
			singHappySong();
			break;
		case SAD:
			singDirge();
			break;
		default:
			new IllegalStateException("Invalid State: " + state);
		}
	}

	private void singHappySong() {
		System.out.println("When you're happy and you know it ...");
	}

	private void singDirge() {
		System.out.println("Don't cry for me Argentina, ...");
	}
}