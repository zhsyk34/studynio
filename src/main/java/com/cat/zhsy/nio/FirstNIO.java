package com.cat.zhsy.nio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class FirstNIO {

	public static void main(String[] args) throws Exception {

		int port = 8888;

		// 1
		ServerSocketChannel acceptorSvr = ServerSocketChannel.open();
		// 2
		InetAddress addr = InetAddress.getByName("ip");
		SocketAddress sad = new InetSocketAddress(addr, port);
		acceptorSvr.socket().bind(sad);
		acceptorSvr.configureBlocking(false);
		// 3
		Selector selector = Selector.open();
		// new Thread(new ReactorTask()).start();//
		// 4
		SelectionKey key = acceptorSvr.register(selector, SelectionKey.OP_ACCEPT);// ,
																					// ioHandler);
		// 5
		int num = selector.select();
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		Iterator<SelectionKey> it = selectedKeys.iterator();
		while (it.hasNext()) {
			SelectionKey selectedKey = it.next();
		}

		// 6
		SocketChannel channel = acceptorSvr.accept();
		channel.configureBlocking(false);
		channel.socket().setReuseAddress(true);

	}

}
