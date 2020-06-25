package com.mint.netty.tcpprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author MintsBerry
 * @date 2020/6/24
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    String str = "可我真的喜欢你";
    for (int i = 0; i < 10; i++) {
      byte[] bytes = str.getBytes(CharsetUtil.UTF_8);
      int length = bytes.length;
      MessageProtocol messageProtocol = new MessageProtocol();
      messageProtocol.setContent(bytes);
      messageProtocol.setLength(length);
      ctx.writeAndFlush(messageProtocol);
    }
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
    String s = new String(msg.getContent(), CharsetUtil.UTF_8);
    System.out.println("read string：" + s);
    System.out.println("length :" + msg.getLength());
  }


}
