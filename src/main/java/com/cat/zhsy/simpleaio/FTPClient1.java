package com.cat.zhsy.simpleaio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class FTPClient1 {
	public static void main(String[] args) throws IOException {
		// 第一个，创建异步网络通道
		AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
		// 连接到服务器，以 ftp.gnu.org 为目标
		channel.connect(new InetSocketAddress("ftp.gnu.org", 21), channel,
				// 使用连接完成的回调
				new CompletionHandler<Void, AsynchronousSocketChannel>() {
					@Override
					public void completed(Void result, AsynchronousSocketChannel attachment) {
						// 完成连接后，启动 FTP 的控制逻辑
						FTPClient1 client = new FTPClient1();
						client.start(attachment);
					}

					@Override
					public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
						exc.printStackTrace();
					}
				});
		// connect 的调用异步执行，马上完成，阻止 JVM 退出
		System.in.read();
	}

	AsynchronousSocketChannel channel;

	public void start(AsynchronousSocketChannel channel) {
		this.channel = channel;
		// 准备读缓冲区
		ByteBuffer buffer = ByteBuffer.allocateDirect(128);
		// 发出读操作请求，
		channel.read(buffer, buffer,
				// 读操作完成后通知
				new CompletionHandler<Integer, ByteBuffer>() {
					@Override
					public void completed(Integer result, ByteBuffer attachment) {
						if (result > 0) {
							// 简单处理读到的响应结果
							int position = attachment.position() - 1;
							if (attachment.get(position - 1) == 13 && attachment.get(position) == 10) {
								if (isValidReply(attachment)) {
									attachment.flip();
									showReply(attachment);
									if (getReplyCode(attachment) == 220)
										login();
								}
							} else {
								// 继续读
								FTPClient1.this.channel.read(attachment, attachment, this);
							}
						} else {
							System.out.println("remote server closed");
						}
					}

					@Override
					public void failed(Throwable exc, ByteBuffer attachment) {
						exc.printStackTrace();
					}
				});
	}

	protected void login() {
		// 准备写缓冲区
		String user = "user anonymous\r\n";
		ByteBuffer buffer = ByteBuffer.wrap(user.getBytes());
		// 发出写操作请求
		channel.write(buffer, buffer,
				// 写操作完成通知
				new CompletionHandler<Integer, ByteBuffer>() {
					@Override
					public void completed(Integer result, ByteBuffer attachment) {
						if (attachment.hasRemaining())
							channel.write(attachment, attachment, this);
						else {
							// channel.read(dst, attachment, handler);
							// readReply();
							// 此处有问题
						}
					}

					@Override
					public void failed(Throwable exc, ByteBuffer attachment) {
						exc.printStackTrace();
					}
				});
	}

	protected void showReply(ByteBuffer attachment) {
		while (attachment.hasRemaining())
			System.out.print((char) attachment.get());
	}

	public static int getReplyCode(ByteBuffer buffer) {
		return Character.digit(buffer.get(0), 10) * 100 + Character.digit(buffer.get(1), 10) * 10 + Character.digit(buffer.get(2), 10);
	}

	public static boolean isValidReply(ByteBuffer buffer) {
		return buffer.get(3) == 32 && Character.isDigit(buffer.get(0)) && Character.isDigit(buffer.get(1)) && Character.isDigit(buffer.get(2));
	}
}
