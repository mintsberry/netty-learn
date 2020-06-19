package com.mint.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author LingXi
 */
public class ChatClient {
  public static final String HOST = "127.0.0.1";
  public static final int PORT = 6666;
  private Selector selector;
  private SocketChannel socketChannel;
  private String clientName;

  public ChatClient() {
    try {
      selector = Selector.open();
      socketChannel= SocketChannel.open(new InetSocketAddress(HOST, PORT));
      socketChannel.configureBlocking(false);
      socketChannel.register(selector, SelectionKey.OP_READ);
      clientName = socketChannel.getLocalAddress().toString().substring(1);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendMessage(String mes) {
    String message = clientName + ": " + mes;
    try {
      socketChannel.write(ByteBuffer.wrap(message.getBytes()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void readMessage() {
    try {
      int select = selector.select();
      if (select > 0) {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
          SelectionKey next = iterator.next();
          if (next.isReadable()) {
            SocketChannel channel = (SocketChannel) next.channel();
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            channel.read(allocate);
            String str = new String(allocate.array());
            System.out.println(str.trim());
          }
          iterator.remove();
        }
      } else {
        System.out.println("Not Channel");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ChatClient chatClient = new ChatClient();
    new Thread(() -> {
      while (true) {
        chatClient.readMessage();
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNextLine()) {
      String s = scanner.nextLine();
      chatClient.sendMessage(s);
    }
  }
}
