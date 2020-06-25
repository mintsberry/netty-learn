package com.mint.netty.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author MintsBerry
 * @date 2020/6/24
 */
public class Client {
  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup group = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new TcpClientHandler());
              }
            });
    bootstrap.connect("127.0.0.1", 6666).sync().channel().closeFuture().sync();
  }
}
