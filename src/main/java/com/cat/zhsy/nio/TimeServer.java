package com.cat.zhsy.nio;

public class TimeServer {

	public static void main(String[] args) {
		int port = 8888;

		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);

		new Thread(timeServer, "MultiplexerTimeServer").start();
	}

}
