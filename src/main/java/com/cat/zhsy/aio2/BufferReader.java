package com.cat.zhsy.aio2;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public abstract class BufferReader implements CompletionHandler<Integer, ByteBuffer> {

	private AsynchronousSocketChannel channel;

	private boolean doAfter;

	public BufferReader(AsynchronousSocketChannel channel) {
		this.channel = channel;
	}

	public BufferReader(AsynchronousSocketChannel channel, boolean doAfter) {
		this.channel = channel;
		this.doAfter = doAfter;
	}

	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		buffer.flip();
		byte[] readBytes = new byte[buffer.remaining()];
		buffer.get(readBytes);

		String read = new String(readBytes);
		System.out.println("read data: " + read);

		if (doAfter) {
			doAfter(read);
		}

	}

	@Override
	public void failed(Throwable exc, ByteBuffer buffer) {
		Util.close(channel);
	}

	protected abstract void doAfter(String read);

}
