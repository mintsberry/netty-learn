package com.mint.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author LingXi
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
    System.out.println("client id = " + msg.getId());
  }
}
