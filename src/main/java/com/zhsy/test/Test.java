package com.zhsy.test;

import java.nio.channels.AsynchronousServerSocketChannel;

public class Test {

	public static void main(String[] args) throws Exception {

		AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(null);
		new Thread(new Server(null, 8888)).start();
		new Thread(new Client(server)).start();

	}

}
