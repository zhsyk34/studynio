package com.cat.zhsy.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class ServerHandle implements Runnable {

	private AsynchronousServerSocketChannel server;

	private CountDownLatch latch;

	public ServerHandle(int port) {
		try {
			server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));

			System.out.println("server is start in port: " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		latch = new CountDownLatch(1);
		doAccept();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void doAccept() {
		server.accept(this, new CompletionHandler<AsynchronousSocketChannel, ServerHandle>() {

			@Override
			public void completed(AsynchronousSocketChannel channel, ServerHandle attachment) {
				attachment.server.accept(attachment, this);
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				channel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {

					@Override
					public void completed(Integer result, ByteBuffer attachment) {
						attachment.flip();
						byte[] bytes = new byte[attachment.remaining()];
						attachment.get(bytes);
						String request = new String(bytes);

						System.out.println("receive data from client: " + request);

						ByteBuffer writeBuffer = ByteBuffer.wrap("zhsy".getBytes());// response
						channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

							@Override
							public void completed(Integer result, ByteBuffer attachment) {
								if (attachment.hasRemaining()) {
									channel.write(attachment, attachment, this);
								}
							}

							@Override
							public void failed(Throwable exc, ByteBuffer attachment) {
								try {
									channel.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						});

					}

					@Override
					public void failed(Throwable exc, ByteBuffer attachment) {
						try {
							channel.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}

			@Override
			public void failed(Throwable exc, ServerHandle attachment) {
				exc.printStackTrace();
				latch.countDown();
			}
		});
	}

}
