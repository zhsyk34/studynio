package com.cat.zhsy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Random;

import com.cat.zhsy.config.Config;
import com.cat.zhsy.util.SelectHandle;
import com.cat.zhsy.util.Util;

public class TimeClientHandle implements Runnable {

	private String host;
	private int port;

	private Selector selector;
	private SocketChannel socketChannel;

	// test to send
	private final String sendStr = Integer.toString(new Random().nextInt(100));

	public TimeClientHandle(String host, int port) {
		this.host = host;
		this.port = port;
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		try {
			doConnect();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		try {
			new SelectHandle(selector, Config.TIMEOUT) {
				@Override
				protected void read(SelectionKey key) throws IOException {
					SocketChannel sc = (SocketChannel) key.channel();
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					int readBytes = sc.read(readBuffer);

					if (readBytes > 0) {
						readBuffer.flip();

						byte[] bytes = new byte[readBuffer.remaining()];
						readBuffer.get(bytes);

						String body = new String(bytes, "UTF-8");
						System.out.println("receive response from server: " + body);
						stop();
					} else if (readBytes < 0) {
						key.cancel();
						sc.close();
					} else {
					}
				}

				@Override
				protected void connect(SelectionKey key) throws IOException {
					SocketChannel sc = (SocketChannel) key.channel();
					if (sc.finishConnect()) {
						sc.register(selector, SelectionKey.OP_READ);
						System.out.print("client is connect");
						Util.write(socketChannel, sendStr);
						System.out.println(", and send data: " + sendStr);
					} else {
						System.exit(1);
					}
				}

				@Override
				protected void accept(SelectionKey key) throws IOException {
					// TODO Auto-generated method stub

				}

				@Override
				protected void write(SelectionKey key) throws IOException {
					// TODO Auto-generated method stub

				}
			}.handle();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	// 连接处理
	private void doConnect() throws IOException {
		boolean isConnected = socketChannel.connect(new InetSocketAddress(host, port));
		if (isConnected) {
			socketChannel.register(selector, SelectionKey.OP_READ);// 连接成功后发送数据并监听读事件
			Util.write(socketChannel, sendStr);
			System.out.println("client is already connect server,send data: " + sendStr);
		} else {
			socketChannel.register(selector, SelectionKey.OP_CONNECT);// 注册连接,成功后进行监听
			System.out.println("listen connect...");
		}
	}

}
