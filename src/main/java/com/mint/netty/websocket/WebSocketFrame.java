package com.mint.netty.websocket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;

import java.time.LocalDate;

/**
 * @author LingXi
 */
public class WebSocketFrame extends SimpleChannelInboundHandler<TextWebSocketFrame> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
    System.out.println("server receive " + msg.text());
    LocalDate now = LocalDate.now();
    ctx.channel().writeAndFlush(new TextWebSocketFrame("server time " +  now.toString() + " " + msg.text()) );
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    System.out.println("client connect" + ctx.channel().id().asLongText());
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    System.out.println("link removed " + ctx.channel().id().asLongText());
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.println("exception " + cause.getMessage());
    ctx.close();
  }
}
