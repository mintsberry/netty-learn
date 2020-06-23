package com.mint.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @author LingXi
 */
public class NettyServer {
  public static void main(String[] args) throws InterruptedException {
    //create bossGroup workerGroup
    //handle connect
    EventLoopGroup boosGroup = new NioEventLoopGroup();
    //handle worker
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    //create launcher server
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(boosGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 128)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childHandler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline()
                        .addLast("decoder", new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()))
                        .addLast(new NettyServerHandler());
              }
            });
    System.out.println("Server is ready");
    ChannelFuture channelFuture = serverBootstrap.bind(6666).sync();
    channelFuture.addListener(future -> {
      if (channelFuture.isSuccess()) {
        System.out.println("listening 6666 success");
      } else {
        System.out.println("listening 6666 failed");
      }
    });
    channelFuture.channel().closeFuture().sync();
  }
}
