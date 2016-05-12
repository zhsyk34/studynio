package com.cat.zhsy.aio2;

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

				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				channel.read(readBuffer, readBuffer, new BufferReader(channel, true) {
					@Override
					protected void doAfter(String read) {
						// server response client
						ByteBuffer writeBuffer = ByteBuffer.wrap("responseData".getBytes());
						channel.write(writeBuffer, writeBuffer, new BufferWriter(channel, true) {
							@Override
							protected void doAfter() {

							}
						});
					}
				});

			}

			@Override
			public void failed(Throwable exc, ServerHandle attachment) {
				exc.printStackTrace();
				Util.close(latch);
			}
		});
	}

}
