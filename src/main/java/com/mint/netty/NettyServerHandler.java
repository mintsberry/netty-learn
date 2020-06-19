package com.mint.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author LingXi
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
  /**
   * read data from client
   * @param ctx pipeline
   * @param msg client data
   * @throws Exception
   */
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    System.out.println("server ctx = " + ctx);
    //msg trans ByteBuf
    ByteBuf byteBuf = (ByteBuf) msg;
    System.out.println("Client message: " + byteBuf.toString(CharsetUtil.UTF_8));
    System.out.println("Client address: " + ctx.channel().remoteAddress());
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, Client", CharsetUtil.UTF_8));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}
