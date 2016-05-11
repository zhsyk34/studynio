package com.cat.zhsy.nio;

import com.cat.zhsy.config.Config;

public class TimeServer {

	public static void main(String[] args) {
		new Thread(new TimeServerHandle(Config.PORT)).start();
	}

}
