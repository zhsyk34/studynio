package com.cat.zhsy.aio2;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public abstract class BufferWriter implements CompletionHandler<Integer, ByteBuffer> {

	private AsynchronousSocketChannel channel;

	private boolean doAfter;

	public BufferWriter(AsynchronousSocketChannel channel) {
		this.channel = channel;
	}

	public BufferWriter(AsynchronousSocketChannel channel, boolean doAfter) {
		this.channel = channel;
		this.doAfter = doAfter;
	}

	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		if (buffer.hasRemaining()) {
			System.out.println("writting data...");
			channel.write(buffer, buffer, this);
		} else {
			doAfter();
		}

		if (doAfter) {
			doAfter();
		}

	}

	@Override
	public void failed(Throwable exc, ByteBuffer buffer) {
		exc.printStackTrace();
		Util.close(channel);
	}

	protected abstract void doAfter();

}
