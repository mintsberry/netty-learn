package com.mint.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
  public static void main(String[] args) {
    try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
         Selector selector = Selector.open();
    ) {
      serverSocketChannel.socket().bind(new InetSocketAddress(6666));
      serverSocketChannel.configureBlocking(false);

      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

      while (true) {
        if (selector.select(1000) == 0) {
          System.out.println("Server wait 1s, no connection");
          continue;
        }
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
          SelectionKey next = iterator.next();
          if(next.isAcceptable()) {
            SocketChannel accept = serverSocketChannel.accept();
            accept.configureBlocking(false);
            accept.register(selector, SelectionKey.OP_READ);
          }
          if (next.isReadable()) {
            SocketChannel channel = (SocketChannel) next.channel();
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            int read = channel.read(allocate);
            if (read == -1) {
              channel.close();
              continue;
            }
            System.out.println("From client " + new String(allocate.array()) +  " "  + read);
          }
          iterator.remove();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
