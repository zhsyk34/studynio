package com.cat.zhsy.util;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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

	public static void read(FileChannel channel) {
		int capacity = 10;
		ByteBuffer buffer = ByteBuffer.allocate(capacity);

		try {
			while (channel.read(buffer) != -1) {
				buffer.flip();
				while (buffer.hasRemaining()) {
					System.out.print((char) buffer.get());
				}
				System.out.println();
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void write(FileChannel channel, String str) throws IOException {
		ByteBuffer src = ByteBuffer.wrap(str.getBytes());
		channel.write(src);
	}

	public static String getRootPath() {
		return Thread.currentThread().getContextClassLoader().getResource("").getPath();
	}
}
