package com.cat.zhsy.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cat.zhsy.util.Util;

public class TestNIO {
	RandomAccessFile file;

	@Before
	public void init() throws FileNotFoundException {
		// String path = Util.getRootPath();
		String path = "E:\\study\\";
		file = new RandomAccessFile(path + "nio.txt", "rw");
	}

	@After
	public void destory() {
		Util.close(file);
	}

	@Test
	public void read() {
		Util.read(file.getChannel());
	}

	@Test
	public void read2() {
		Util.readToStr(file.getChannel());
	}

	@Test
	public void write() throws IOException {
		String str = "just do it!" + System.currentTimeMillis();
		Util.write(file.getChannel(), str);
	}

	@Test
	public void test() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.flip();
		System.out.println(buffer.remaining());
	}

	@Test
	public void test2() {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		for (int i = 0; i < buffer.capacity(); i++) {
			buffer.put((byte) (2 * i + 1));
		}

		buffer.flip();

		while (buffer.hasRemaining()) {
			System.out.print(buffer.get() + "  ");
		}
	}

	@Test
	public void test3() {
		String str = "Hello world";
		ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
		System.out.println(buffer.capacity());
		System.out.println(buffer.position());

		while (buffer.hasRemaining()) {
			System.out.print((char) buffer.get() + " ");
		}

	}

}
