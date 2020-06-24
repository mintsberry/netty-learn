package com.mint.netty.inandoutbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author MintsBerry
 * @date 2020/6/24
 */
public class ClientHandler extends SimpleChannelInboundHandler<Long> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
    Channel channel = ctx.channel();
    System.out.println("Server ip: " + channel.remoteAddress());
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("Client send Data");
    ctx.writeAndFlush(12345);
    Thread.sleep(1000);
    ctx.writeAndFlush(45689L);
  }
}
