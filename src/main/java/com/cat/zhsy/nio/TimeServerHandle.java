package com.cat.zhsy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

import com.cat.zhsy.config.Config;
import com.cat.zhsy.util.SelectHandle;
import com.cat.zhsy.util.Util;

public class TimeServerHandle implements Runnable {

	private Selector selector;

	private ServerSocketChannel serverSocketChannel;

	// 初始化多路复用器,绑定监听端口
	public TimeServerHandle(int port) {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			System.out.println("server start in port: " + port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		try {
			new SelectHandle(selector, Config.TIMEOUT) {
				@Override
				protected void accept(SelectionKey key) throws IOException {
					ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
					SocketChannel sc = ssc.accept();
					sc.configureBlocking(false);
					sc.register(selector, SelectionKey.OP_READ);
				}

				@Override
				protected void read(SelectionKey key) throws IOException {
					SocketChannel sc = (SocketChannel) key.channel();
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					int readBytes = sc.read(readBuffer);
					if (readBytes > 0) {
						readBuffer.flip();
						byte[] bytes = new byte[readBuffer.remaining()];
						readBuffer.get(bytes);
						String request = new String(bytes, "UTF-8");
						System.out.printf("recieve data from client : %s\n", request);
						String response = new Date(System.currentTimeMillis()).toString();
						Util.write(sc, response);
						System.out.println("send data to client: " + response);
					} else if (readBytes < 0) {
						key.cancel();
						sc.close();
					} else {
					}
				}

				@Override
				protected void connect(SelectionKey key) throws IOException {
					// TODO Auto-generated method stub

				}

				@Override
				protected void write(SelectionKey key) throws IOException {
					// TODO Auto-generated method stub

				}
			}.handle();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
