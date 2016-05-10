package com.cat.zhsy.nio;

public class TimeServer {

	public static void main(String[] args) {

		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(Config.port);

		new Thread(timeServer).start();
	}

}
