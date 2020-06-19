package com.mint.chatroom;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author LingXi
 */
public class ChatServer {
  private Selector selector;
  private ServerSocketChannel listenChannel;
  public static final int PORT = 6666;

  public ChatServer() {
    try {
      listenChannel = ServerSocketChannel.open();
      selector = Selector.open();
      listenChannel.socket().bind(new InetSocketAddress(PORT));
      listenChannel.configureBlocking(false);
      listenChannel.register(selector, SelectionKey.OP_ACCEPT);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void listen() {
    System.out.println("Server listening");
    try {
      for (;;) {
        int count = selector.select(1000);
        if (count > 0) {
          Set<SelectionKey> selectionKeys = selector.selectedKeys();
          Iterator<SelectionKey> iterator = selectionKeys.iterator();
          while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            if (key.isAcceptable()) {
              SocketChannel channel = listenChannel.accept();
              channel.configureBlocking(false);
              channel.register(selector, SelectionKey.OP_READ);
              System.out.println(channel.getRemoteAddress() + " connect");
            } else if (key.isReadable()) {
              this.readClient(key);
            }
            iterator.remove();
          }
        } else {
//          System.out.println("Server wait connect");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void readClient(SelectionKey key) {
    SocketChannel channel = null;
    try{
      channel = (SocketChannel) key.channel();
      ByteBuffer allocate = ByteBuffer.allocate(1024);
      int count = channel.read(allocate);
      if (count > 0) {
        String str = new String(allocate.array());
        System.out.println("form client " + str);
        this.sendMessageClient(str, channel);
      }
      else {
        System.out.println("client offline");
        key.cancel();
        channel.close();
      }
    } catch (IOException e) {
      try {
        System.out.println(channel.getRemoteAddress() + " client offline");
        channel.close();
        key.cancel();
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }

  private void sendMessageClient(String msg, SocketChannel self) throws IOException {
    System.out.println("Message forwarding");
    for (SelectionKey key : selector.keys()) {
      SelectableChannel channel = key.channel();
      if (channel instanceof  SocketChannel && channel != self) {
        SocketChannel sc = (SocketChannel) channel;
        ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
        sc.write(wrap);
      }
    }
  }

  public static void main(String[] args) {
    ChatServer chatServer = new ChatServer();
    chatServer.listen();
  }
}
