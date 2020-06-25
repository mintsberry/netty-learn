package com.mint.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.UUID;

/**
 * @author MintsBerry
 * @date 2020/6/24
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
  private int count = 0;
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
    byte[] bytes = new byte[msg.readableBytes()];
    msg.readBytes(bytes);
    String s = new String(bytes, CharsetUtil.UTF_8);
    System.out.println("read string：" + s);
    System.out.println("count:" + ++count);

    ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8);
    ctx.writeAndFlush(byteBuf);
  }
}
