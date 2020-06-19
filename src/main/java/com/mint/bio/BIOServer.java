package com.mint.bio;

import sun.nio.ch.ThreadPool;
import sun.security.util.Length;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.concurrent.*;

public class BIOServer {
  public static void main(String[] args) throws Exception {
//    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, Runtime.getRuntime().availableProcessors()
//            , 3, TimeUnit.SECONDS,
//            new LinkedBlockingDeque<>(4), Executors.defaultThreadFactory(),
//            new ThreadPoolExecutor.DiscardOldestPolicy());
    System.out.println(Runtime.getRuntime().availableProcessors());
    ExecutorService threadPoolExecutor = new ThreadPoolExecutor(
            0, Runtime.getRuntime().availableProcessors(),
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());
//    ExecutorService threadPoolExecutor = Executors.newCachedThreadPool();
    System.out.println("Service start");
    ServerSocket serverSocket = new ServerSocket(6666);
    for (;;) {
      final Socket socket = serverSocket.accept();
      System.out.println("Connection Client");
      threadPoolExecutor.execute(()->{
        handler(socket);
      });
    }

  }

  public static void handler(Socket socket) {
    byte[] bytes = new byte[1024];
    try (
         InputStream inputStream = socket.getInputStream();
      ) {
      int length = 0;
      while ((length = inputStream.read(bytes)) != -1) {
        System.out.println(Thread.currentThread() + new String(bytes, 0, length));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
