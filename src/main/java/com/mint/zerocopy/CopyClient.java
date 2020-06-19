package com.mint.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author LingXi
 */
public class CopyClient {
  public static final String FILE = "TeamViewerQS.exe";
  public static final long SIZE = 8 * 1024 * 1024;
  public static void main(String[] args) throws Exception {
    SocketChannel open = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));
    FileInputStream fileInputStream = new FileInputStream(FILE);
    FileChannel channel = fileInputStream.getChannel();
    long start = System.currentTimeMillis();
    double count = Math.ceil(channel.size() / Double.valueOf(SIZE)) ;
    for (int i = 0; i < count; i++) {
      if (i < count - 1) {
        channel.transferTo(i * SIZE, SIZE, open);
      } else {
        channel.transferTo(i * SIZE, channel.size() - i * SIZE, open);
      }
    }
    long end = System.currentTimeMillis();
    long time = end - start;
    System.out.println("count:" + count + "  Time:" + time);
    channel.close();
    open.close();
  }
}
