package com.mint.netty.rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author MintsBerry
 * @date 2020/6/25
 */
public class NettyServer {

  public static void startServer(String hostName, int port) {
    startServer0(hostName, port);
  }

  private static void startServer0(String hostname, int port) {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    NioEventLoopGroup workGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(bossGroup, workGroup)
              .channel(NioServerSocketChannel.class)
              .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ChannelPipeline pipeline = ch.pipeline();
                  pipeline.addLast(new StringDecoder())
                          .addLast(new StringEncoder())
                          .addLast(new NettyServerHandler());
                }
              });
      System.out.println("server start");
      ChannelFuture sync = serverBootstrap.bind(hostname, port).sync().channel().closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      bossGroup.shutdownGracefully();
      workGroup.shutdownGracefully();
    }
  }
}
