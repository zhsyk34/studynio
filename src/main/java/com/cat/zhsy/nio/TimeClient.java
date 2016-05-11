package com.cat.zhsy.nio;

import com.cat.zhsy.config.Config;

public class TimeClient {

	public static void main(String[] args) {
		new Thread(new TimeClientHandle(Config.HOST, Config.PORT)).start();
	}

}
