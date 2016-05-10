package com.cat.zhsy.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.cat.zhsy.util.Util;

public class TimeClient {

	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 8888;

		Socket client = null;
		BufferedReader in = null;
		PrintWriter out = null;

		try {
			client = new Socket(host, port);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			out = new PrintWriter(client.getOutputStream(), true);
			out.println("query");

			System.out.println("send query order to server");

			System.out.println("read from client" + in.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.close(in);
			Util.close(out);
			Util.close(client);
		}
	}

}
