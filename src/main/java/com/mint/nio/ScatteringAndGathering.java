package com.mint.nio;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGathering {
  public static void main(String[] args) throws Exception {
    ServerSocketChannel open = ServerSocketChannel.open();
    InetSocketAddress inetSocketAddress = new InetSocketAddress(9999);

    open.socket().bind(inetSocketAddress);

    ByteBuffer[] byteBuffers = new ByteBuffer[2];
    byteBuffers[0] = ByteBuffer.allocate(3);
    byteBuffers[1] = ByteBuffer.allocate(5);

    SocketChannel accept = open.accept();

    int message = 8;
    while (true) {
      int byteRead = 0;
      while(byteRead < message) {
        long read = accept.read(byteBuffers);
        byteRead += read;
        System.out.println("byteRead = " + byteRead);
        Arrays.asList(byteBuffers).stream().map(buffer -> "Postion=" +
                buffer.position() + ", limit=" + buffer.limit()).forEach(System.out::println);
      }
      Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());

      int byteWrite = 0;
      while(byteWrite  < message) {
        long write = accept.write(byteBuffers);
        byteRead += write;

      }
      Arrays.asList(byteBuffers).forEach(Buffer::clear);
    }
  }
}
