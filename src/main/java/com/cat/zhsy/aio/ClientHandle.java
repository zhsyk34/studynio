package com.cat.zhsy.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class ClientHandle implements Runnable, CompletionHandler<Void, ClientHandle> {

	private String host;
	private int port;

	private AsynchronousSocketChannel client;

	CountDownLatch latch;

	public ClientHandle(String host, int port) {
		this.host = host;
		this.port = port;
		try {
			client = AsynchronousSocketChannel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		client.connect(new InetSocketAddress(host, port), this, this);
		latch = new CountDownLatch(1);
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void completed(Void result, ClientHandle attachment) {
		ByteBuffer writeBuffer = ByteBuffer.wrap("query".getBytes());

		client.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer buffer) {
				if (buffer.hasRemaining()) {
					client.write(buffer, buffer, this);
				} else {
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {

						@Override
						public void completed(Integer result, ByteBuffer buffer) {
							buffer.flip();
							byte[] bytes = new byte[buffer.remaining()];
							buffer.get(bytes);

							String body = new String(bytes);
							System.out.println("receive form server: " + body);

							latch.countDown();
						}

						@Override
						public void failed(Throwable exc, ByteBuffer attachment) {
							close(client, latch);
						}
					});
				}

			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				close(client, latch);
			}
		});

	}

	@Override
	public void failed(Throwable exc, ClientHandle attachment) {
		exc.printStackTrace();
		close(client, latch);
	}

	private static void close(AsynchronousSocketChannel client, CountDownLatch latch) {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		latch.countDown();
	}

}
