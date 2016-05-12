package com.cat.zhsy.aio2;

import com.cat.zhsy.config.Config;

public class Client {

	public static void main(String[] args) {
		new Thread(new ClientHandle(Config.HOST, Config.PORT, "test")).start();
	}

}
