package com.cat.zhsy.base;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadData {

	public static void main(String[] args) throws IOException {
		RandomAccessFile aFile = new RandomAccessFile("E:/study/nio.txt", "rw");
		FileChannel inChannel = aFile.getChannel();
		read(inChannel);
		aFile.close();

	}

	public static void read(FileChannel channel) throws IOException {
		int capacity = 10;
		ByteBuffer buffer = ByteBuffer.allocate(capacity);

		int hasRead = channel.read(buffer);

		while (hasRead != -1) {
			buffer.flip();
			while (buffer.hasRemaining()) {
				System.out.print((char) buffer.get());
			}
			System.out.println();
			buffer.clear();

			hasRead = channel.read(buffer);
			System.out.println(channel.position());
		}
	}

}
