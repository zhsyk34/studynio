package com.cat.zhsy.base;

import java.io.IOException;
import java.nio.channels.Selector;

public class Test {

	public static void main(String[] args) throws IOException {
		Selector s1 = Selector.open();
		Selector s2 = Selector.open();

		System.out.println(s1 == s2);
		System.out.println(s1.hashCode());
		System.out.println(s2.hashCode());
	}

}
