package com.cat.zhsy.base;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class TestSelector {

	public static void main(String[] args) throws IOException {
		SelectionKey selectionKey = null;
		System.out.println(selectionKey.interestOps());

		// SocketChannel channel = SocketChannel.open();
		// Selector sel = Selector.open();
		// channel.configureBlocking(false);
		// SelectionKey selectionKey = channel.register(sel,
		// SelectionKey.OP_ACCEPT | SelectionKey.OP_READ);

	}

}
