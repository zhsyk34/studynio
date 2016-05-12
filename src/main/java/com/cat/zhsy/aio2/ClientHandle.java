package com.cat.zhsy.aio2;

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

	private String send;

	public ClientHandle(String host, int port, String send) {
		this.host = host;
		this.port = port;
		this.send = send;
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
		Util.close(client, null);
	}

	@Override
	public void completed(Void result, ClientHandle attachment) {
		ByteBuffer writeBuffer = ByteBuffer.wrap(send.getBytes());
		client.write(writeBuffer, writeBuffer, new BufferWriter(client, true) {
			@Override
			protected void doAfter() {
				new BufferReader(client, true) {
					@Override
					protected void doAfter(String read) {
						System.out.println(read);
					}
				};
			}
		});
	}

	@Override
	public void failed(Throwable exc, ClientHandle attachment) {
		exc.printStackTrace();
		Util.close(client, latch);
	}

}
