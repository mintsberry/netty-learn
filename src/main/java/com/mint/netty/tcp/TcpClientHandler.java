package com.mint.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author MintsBerry
 * @date 2020/6/24
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    for (int i = 0; i < 10; i++) {
      ByteBuf byteBuf = Unpooled.copiedBuffer("Hello Server", CharsetUtil.UTF_8);
      ctx.writeAndFlush(byteBuf);
    }
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
    byte[] buffer = new byte[msg.readableBytes()];
     msg.readBytes(buffer);
    String string = new String(buffer, CharsetUtil.UTF_8);
    System.out.println("client receiver: " + string);
  }
}
