package com.mint.netty.chatroom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author LingXi
 */
public class ChatRoomServer {
  private int port;

  public ChatRoomServer(int port) {
    this.port = port;
  }

  public void run() throws InterruptedException {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    ServerBootstrap bootstrap = new ServerBootstrap();
    bootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 128)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childHandler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("decoder", new StringDecoder())
                        .addLast("encode", new StringEncoder())
                        .addLast(new ChatRoomServerHandler());
              }
            });
    System.out.println("server start");
    ChannelFuture channelFuture = bootstrap.bind(port).sync();
    channelFuture.channel().closeFuture().sync();
  }

  public static void main(String[] args) throws InterruptedException {
    ChatRoomServer chatRoomServer = new ChatRoomServer(6666);
    chatRoomServer.run();
  }
}
