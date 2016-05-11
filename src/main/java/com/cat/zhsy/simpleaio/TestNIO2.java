package com.cat.zhsy.simpleaio;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class TestNIO2 {

	public static void main(String[] args) throws Exception {
		AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(null);
		Future<AsynchronousSocketChannel> result = server.accept();

		
		AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
		client.connect(server.getLocalAddress()).get();
	}

}
