package com.cat.zhsy.base;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class SimpleMain {

	@Test
	public void testServer() throws IOException, InterruptedException {
		SimpleServer server = new SimpleServer(9021);
		Thread.sleep(10000);// 由于是异步操作，所以睡眠一定时间，以免程序很快结束
	}

	@Test
	public void testClient() throws IOException, InterruptedException, ExecutionException {
		SimpleClient client = new SimpleClient("localhost", 9021);
		client.write((byte) 11);
	}

	public static void main(String[] args) {
		SimpleMain demoTest = new SimpleMain();
		try {
			demoTest.testServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			demoTest.testClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}