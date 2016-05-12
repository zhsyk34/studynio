package com.zhsy.test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class TsetFileSecond {

	public static void main(String[] args) throws Exception {
		WatchService watchService = FileSystems.getDefault().newWatchService();

		Path watchDirPath = Paths.get("E:\\study");
		watchDirPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

		while (true) {
			WatchKey watchKey = watchService.take();
			for (WatchEvent<?> event : watchKey.pollEvents()) {
				System.out.println("kind: " + event.kind() + "occurred on: " + event.context());
			}

			if (!watchKey.reset()) {
				break;
			}
		}
	}

}
