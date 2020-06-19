package com.mint.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileReadChannel {
  public static void main(String[] args) {
    try (FileInputStream fileInputStream = new FileInputStream("D:\\file.txt");
         FileChannel channel = fileInputStream.getChannel();
    ) {
      ByteBuffer allocate = ByteBuffer.allocate(1024);
      channel.read(allocate);
//      allocate.flip();
      System.out.println(new String(allocate.array()));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
