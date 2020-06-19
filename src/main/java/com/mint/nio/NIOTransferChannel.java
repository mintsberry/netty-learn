package com.mint.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class NIOTransferChannel {
  public static void main(String[] args) {
    try(FileInputStream fileInputStream = new FileInputStream("D:\\a.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\b.jpg");
    ) {
      FileChannel channel = fileInputStream.getChannel();
      FileChannel channel1 = fileOutputStream.getChannel();
      channel.transferTo(0, channel.size(), channel1);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
