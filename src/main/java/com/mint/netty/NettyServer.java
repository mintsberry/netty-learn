package com.mint.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
                socketChannel.pipeline().addLast(new NettyServerHandler());
              }
            });
    System.out.println("Server is ready");
    ChannelFuture channelFuture = serverBootstrap.bind(6666).sync();
    channelFuture.channel().closeFuture().sync();
  }
}
