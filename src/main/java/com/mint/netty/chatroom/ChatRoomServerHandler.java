package com.mint.netty.chatroom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LingXi
 */
public class ChatRoomServerHandler extends SimpleChannelInboundHandler<String> {

  private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    channels.writeAndFlush("[Client] " + channel.remoteAddress() + " enter chatroom "
    + simpleDateFormat.format(new Date()) + "\n");
    channels.add(channel);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println(ctx.channel().remoteAddress() + " online\n");
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    System.out.println(ctx.channel().remoteAddress() + " offline\n");
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    channels.writeAndFlush("[Client] " + channel.remoteAddress() + " exit chatroom\n");
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    for (Channel channel : channels) {
      if (channel != ctx.channel()) {
        channel.writeAndFlush("[" + ctx.channel().remoteAddress() + "]: " + msg + "\n" );
      } else {
        channel.writeAndFlush("[My]: " + msg + "\n" );
      }
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}
