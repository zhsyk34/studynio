package com.cat.zhsy.bio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerHandlerExecutePool {

	private ExecutorService executorService;

	public TimeServerHandlerExecutePool(int maximumPoolSize, int queueSize) {
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		System.out.println("available :" + corePoolSize);
		long keepAliveTime = 120L;
		TimeUnit unit = TimeUnit.SECONDS;
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(queueSize);
		executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	public void execute(Runnable task) {
		executorService.execute(task);
	}

}
