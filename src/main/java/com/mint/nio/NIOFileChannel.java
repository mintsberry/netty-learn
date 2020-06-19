package com.mint.nio;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel {
  public static void main(String[] args) {
    String str = "Hello Mint";

    try (FileOutputStream fileOutputStream = new FileOutputStream("D:\\file.txt");
         FileChannel channel = fileOutputStream.getChannel();
    ) {
      ByteBuffer allocate = ByteBuffer.allocate(1024);
      allocate.put(str.getBytes());
      allocate.flip();
      channel.write(allocate);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
