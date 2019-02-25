package com.wangbo.test.concurrentAndmutiThread;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ThreadLocalTest {
	
	@Test
	public void objectTest() {
		Person person1 = new Person(1);
		Person person2 = new Person(1);
		
		System.out.println(person1.getList());
		System.out.println(person1.equals(person2));
	}
	
	@Test
	public void testThreadLocal() {
		ThreadLocal<Person> threadLocal1 = new ThreadLocal<>();
		ThreadLocal<Person> threadLocal2 = new ThreadLocal<>();
		System.out.println(threadLocal1 == threadLocal2);
	}
	
	class Person {

		private int age;
		
		private List<String> list = new ArrayList<String>();
		
		public Person(int age) {
			this.setAge(age);
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
}
