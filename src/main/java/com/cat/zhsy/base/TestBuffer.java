package com.cat.zhsy.base;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestBuffer {

	public static void main(String[] args) throws IOException {
		test();
	}

	public static void test() throws IOException {
		RandomAccessFile aFile = new RandomAccessFile("E:/study/nio.txt", "rw");
		FileChannel channel = aFile.getChannel();

		String newData = "New String to write to file:" + System.currentTimeMillis();

		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(newData.getBytes());

		buf.flip();

		while (buf.hasRemaining()) {
			channel.write(buf);
		}
		aFile.close();
	}
	
	
}
