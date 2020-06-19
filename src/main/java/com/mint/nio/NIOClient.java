package com.mint.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
  public static void main(String[] args) {
    try (SocketChannel open = SocketChannel.open()) {
      open.configureBlocking(false);
      InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
      if (!open.connect(inetSocketAddress)) {
        while(!open.finishConnect()) {
          System.out.println("Not Blocking");
        }
      }
      String str = "Hello, World";
      ByteBuffer wrap = ByteBuffer.wrap(str.getBytes());
      open.write(wrap);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
