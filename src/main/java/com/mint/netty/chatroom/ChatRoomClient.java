package com.mint.netty.chatroom;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import javax.net.ssl.SSLContext;
import java.util.Scanner;

/**
 * @author LingXi
 */
public class ChatRoomClient {
  private final String host;
  private final int port ;

  public ChatRoomClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void run(){
    NioEventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(group)
              .channel(NioSocketChannel.class)
              .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ChannelPipeline pipeline = ch.pipeline();
                  pipeline.addLast("decoder", new StringDecoder())
                          .addLast("encoder", new StringEncoder())
                          .addLast(new ChatRoomClientHandler());
                }
              });
      ChannelFuture sync = bootstrap.connect(host, port).sync();
      System.out.println(sync.channel().localAddress() + " ready");
      Scanner scanner = new Scanner(System.in);
      while (scanner.hasNextLine()) {
        String s = scanner.nextLine();
        sync.channel().writeAndFlush(s + "\r\n");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      group.shutdownGracefully();
    }
  }

  public static void main(String[] args) {
    new ChatRoomClient("127.0.0.1", 6666).run();
  }
}
