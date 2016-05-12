package com.zhsy.test;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class Server implements Runnable {

	private String hostname;
	private int port;

	public Server(String hostname, int port) {
		this.hostname = hostname == null ? "127.0.0.1" : hostname;
		this.port = port;
	}

	@Override
	public void run() {

		try {
			InetSocketAddress address = new InetSocketAddress(hostname, port);
			AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(address, 1024);
			Future<AsynchronousSocketChannel> acceptFuture = server.accept();

			if (acceptFuture.isDone()) {
				AsynchronousSocketChannel client = acceptFuture.get();
				System.out.println(client.getLocalAddress());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
