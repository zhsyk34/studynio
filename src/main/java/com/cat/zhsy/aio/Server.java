package com.cat.zhsy.aio;

import com.cat.zhsy.config.Config;

public class Server {

	public static void main(String[] args) {
		new Thread(new ServerHandle(Config.PORT)).start();
	}

}
