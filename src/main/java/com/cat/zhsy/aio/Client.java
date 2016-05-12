package com.cat.zhsy.aio;

import com.cat.zhsy.config.Config;

public class Client {

	public static void main(String[] args) {
		new Thread(new ClientHandle(Config.HOST, Config.PORT)).start();
	}

}
