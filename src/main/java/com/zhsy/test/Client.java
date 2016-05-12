package com.zhsy.test;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class Client implements Runnable {

	private AsynchronousServerSocketChannel server;

	public Client(AsynchronousServerSocketChannel server) {
		this.server = server;
	}

	@Override
	public void run() {
		try {
			AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
			Future<Void> future = client.connect(server.getLocalAddress());
			future.isDone();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
