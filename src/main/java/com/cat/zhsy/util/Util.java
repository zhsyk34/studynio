package com.cat.zhsy.util;

import java.io.Closeable;
import java.io.IOException;

public class Util {

	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			closeable = null;
		}
	}
}
