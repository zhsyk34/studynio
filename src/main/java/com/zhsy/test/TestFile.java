package com.zhsy.test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class TestFile {

	public static void main(String[] args) throws IOException {
		FileVisitor<Path> myFileVisitor = new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				System.out.println("=============当前目录=============== " + dir);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				System.out.println("子文件 " + file + " 大小:" + attrs.size());
				return FileVisitResult.CONTINUE;
			}

		};

		Path headDir = Paths.get("E:\\vedio\\MPlayer");
		Files.walkFileTree(headDir, myFileVisitor);
	}

}
