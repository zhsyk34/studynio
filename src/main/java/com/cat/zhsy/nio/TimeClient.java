package com.cat.zhsy.nio;

public class TimeClient {

	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 8888;

		new Thread(new TimeClientHandle(host, port)).start();
	}

}
