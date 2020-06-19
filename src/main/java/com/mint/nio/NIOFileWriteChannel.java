package com.mint.nio;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileWriteChannel {
  public static void main(String[] args) throws IOException {
    //当前项目下路径
    File file = new File("");
    String filePath = file.getCanonicalPath();
    System.out.println(filePath);

    //获取类加载的根路径
    File file3 = new File(file.getClass().getResource("/").getPath());
    System.out.println(file3);


    try (FileInputStream fileInputStream = new FileInputStream("Hello.txt");
         FileChannel iChannel = fileInputStream.getChannel();
         FileOutputStream fileOutputStream = new FileOutputStream("World.txt");
         FileChannel oChannel = fileOutputStream.getChannel();

    ) {
      ByteBuffer allocate = ByteBuffer.allocate(1024);
      int length = 0;
      while((length = iChannel.read(allocate)) != -1) {
        allocate.flip();
        oChannel.write(allocate);
        allocate.clear();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
