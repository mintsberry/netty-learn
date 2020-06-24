package com.mint.netty.inandoutbound;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author LingXi
 */
public class Client {
  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup group = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .handler(new ClientInitialize());
    bootstrap.connect("127.0.0.1", 6666).sync().channel().closeFuture().sync();
  }
}
