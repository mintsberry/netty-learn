package com.mint.netty.tcpprotocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author MintsBerry
 * @date 2020/6/24
 */
public class Server {
  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast(new MessageDecoder())
                        .addLast(new MessageEncoder())
                        .addLast(new TcpServerHandler());
              }
            });
    serverBootstrap.bind(6666).sync().channel().closeFuture().sync();
  }
}
