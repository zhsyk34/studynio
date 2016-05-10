package com.cat.zhsy.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;

import com.cat.zhsy.util.Util;

public class TestNIO {
	RandomAccessFile file;

	@Before
	public void init() throws FileNotFoundException {
		// String path = Util.getRootPath();
		String path = "G:\\work\\workspace\\studynio\\target\\classes\\";
		file = new RandomAccessFile(path + "nio.txt", "rw");
	}

	@Test
	public void read() {
		Util.read(file.getChannel());
		Util.close(file);
	}

	@Test
	public void write() throws IOException {
		String str = "just do it!" + System.currentTimeMillis();
		Util.write(file.getChannel(), str);
	}
	
	@Test
	public void test(){
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.flip();
		System.out.println(buffer.remaining());
	}

}
