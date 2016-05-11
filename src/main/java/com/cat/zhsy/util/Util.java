package com.cat.zhsy.util;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

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
			while (channel.read(buffer) > 0) {
				buffer.flip();
				while (buffer.hasRemaining()) {
					System.out.print((char) buffer.get());
				}
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String readToStr(FileChannel channel) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		StringBuilder builder = new StringBuilder();
		try {
			while (channel.read(buffer) > 0) {
				buffer.flip();
				builder.append(byte2Str(buffer));
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(builder);
		return builder.toString();
	}

	public static String readToStr(SocketChannel channel) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		StringBuilder builder = new StringBuilder();
		try {
			while (channel.read(buffer) > 0) {
				buffer.flip();
				builder.append(byte2Str(buffer));
				buffer.clear();
			}
			channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	public static void write(FileChannel channel, String str) {
		ByteBuffer src = ByteBuffer.wrap(str.getBytes());
		try {
			channel.write(src);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void write(SocketChannel channel, String str) {
		ByteBuffer src = ByteBuffer.wrap(str.getBytes());
		try {
			channel.write(src);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getRootPath() {
		return Thread.currentThread().getContextClassLoader().getResource("").getPath();
	}

	private static String byte2Str(ByteBuffer buffer) {
		return Charset.forName("UTF-8").decode(buffer).toString();
	}
}
