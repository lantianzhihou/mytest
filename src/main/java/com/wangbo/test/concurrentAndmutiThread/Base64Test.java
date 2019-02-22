package com.wangbo.test.concurrentAndmutiThread;

import java.util.Base64;
import java.util.Base64.Encoder;

import org.junit.Test;

import sun.misc.Unsafe;

public class Base64Test {
	
    @SuppressWarnings("restriction")
    @Test
	public void sourceTest() {
		Encoder encoder = Base64.getEncoder();
		byte[] bytes = "hello".getBytes();
		System.out.println(encoder.encodeToString(bytes));
        Unsafe unsafe = Unsafe.getUnsafe();
        System.out.println(unsafe);
	}
}
