package com.mint.netty.tcpprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @author MintsBerry
 * @date 2020/6/24
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
    String s = new String(msg.getContent(), CharsetUtil.UTF_8);
    System.out.println("read string：" + s);
    System.out.println("length :" + msg.getLength());

    byte[] responseContent = UUID.randomUUID().toString().getBytes("utf-8");
    int responseLength = responseContent.length;
    MessageProtocol messageProtocol = new MessageProtocol();
    messageProtocol.setContent(responseContent);
    messageProtocol.setLength(responseLength);
    ctx.writeAndFlush(messageProtocol);
  }
}
