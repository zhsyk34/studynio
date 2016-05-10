package com.cat.zhsy.nio;

public class TimeClient {

	public static void main(String[] args) {
		new Thread(new TimeClientHandle(Config.host, Config.port)).start();
	}

}
