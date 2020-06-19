package com.mint.zerocopy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author LingXi
 */
public class CopyServer {
  public static void main(String[] args) throws FileNotFoundException {
    ServerSocketChannel open = null;
    FileOutputStream fileOutputStream = new FileOutputStream("out.exe");
    FileChannel oChannel = fileOutputStream.getChannel();
    try {
      open = ServerSocketChannel.open();
      open.bind(new InetSocketAddress(6666));
      ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 8);
      while (true) {
        SocketChannel accept = open.accept();
        int read = 0;
        while ((read = accept.read(byteBuffer)) > 0) {
          byteBuffer.flip();
          oChannel.write(byteBuffer);
          byteBuffer.rewind();
        }
        oChannel.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
