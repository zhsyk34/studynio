package com.cat.zhsy.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadTimeServer {

	public static void main(String[] args) throws IOException {
		int port = 8888;

		ServerSocket server = null;
		TimeServerHandlerExecutePool executePool = new TimeServerHandlerExecutePool(50, 10000);
		try {
			server = new ServerSocket(port);
			System.out.println("server start in port: " + port);

			Socket socket = null;
			while (true) {
				socket = server.accept();
				executePool.execute(new TimeServerHandler(socket));
			}
		} finally {
			if (server != null) {
				System.out.println("server close");
				server.close();
				server = null;
			}
		}
	}

}
