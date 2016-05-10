package com.cat.zhsy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import com.cat.zhsy.util.Util;

public class TimeClientHandle implements Runnable {

	private String host;
	private int port;
	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean stop;

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

		while (!stop) {
			try {
				selector.select(1000);

				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> it = keys.iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
					it.remove();
					handleInput(key);

					if (key != null) {
						key.channel();
						Util.close(key.channel());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Util.close(selector);
	}

	private void handleInput(SelectionKey key) throws IOException {
		if (!key.isValid()) {
			return;
		}

		SocketChannel sc = (SocketChannel) key.channel();

		if (key.isConnectable()) {
			if (sc.finishConnect()) {
				sc.register(selector, SelectionKey.OP_READ);
				System.out.println("已连接...");
				doWrite(sc);
			} else {
				System.exit(1);
			}
		}

		if (key.isReadable()) {
			ByteBuffer readBuffer = ByteBuffer.allocate(1024);
			int readBytes = sc.read(readBuffer);

			if (readBytes > 0) {
				readBuffer.flip();

				byte[] bytes = new byte[readBuffer.remaining()];
				readBuffer.get(bytes);

				String body = new String(bytes, "UTF-8");
				System.out.println("now is" + body);
				this.stop = true;
			} else if (readBytes < 0) {
				key.cancel();
				sc.close();
			} else {
			}
		}

	}

	private void doConnect() throws IOException {
		// 如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
		if (socketChannel.connect(new InetSocketAddress(host, port))) {
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		} else {
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}

	}

	private void doWrite(SocketChannel sc) throws IOException {
		ByteBuffer writeBuffer = ByteBuffer.wrap("query".getBytes());
		writeBuffer.flip();
		sc.write(writeBuffer);
		if (!writeBuffer.hasRemaining()) {
			System.out.println("Send order to server succeed.");
		}
	}

}
