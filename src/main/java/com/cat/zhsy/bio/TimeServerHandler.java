package com.cat.zhsy.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import com.cat.zhsy.util.Util;

public class TimeServerHandler implements Runnable {

	private Socket socket;

	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;

		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(), true);

			String body = null;
			while (true) {
				body = in.readLine();
				if (body == null) {
					break;
				}
				System.out.println("receive order: " + body);

				String response = body.equalsIgnoreCase("query") ? new Date(System.currentTimeMillis()).toString() : "error order";
				out.println(response);
				// out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.close(in);
			Util.close(out);
			Util.close(socket);
		}
	}

}
