package com.cat.zhsy.aio2;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CountDownLatch;

public class Util {

	public static void close(AsynchronousSocketChannel channel, CountDownLatch latch) {
		if (channel != null) {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (latch != null) {
			latch.countDown();
		}
	}

	public static void close(AsynchronousSocketChannel channel) {
		close(channel, null);
	}

	public static void close(CountDownLatch latch) {
		close(null, latch);
	}
}
