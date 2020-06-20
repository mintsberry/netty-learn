package com.mint.netty.http;

import com.sun.jndi.toolkit.url.Uri;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author LingXi
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
    if (msg instanceof HttpRequest) {
      System.out.println("msg type = " + msg.getClass());
      System.out.println("client address =" + ctx.channel().remoteAddress());
      HttpRequest request = (HttpRequest) msg;
      URI uri = new URI(request.uri());
      if ("/favicon.ico".equals(uri.getPath())) {
        return ;
      }
      ByteBuf byteBuf = Unpooled.copiedBuffer("Hello, My Service", CharsetUtil.UTF_8);
      DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
      response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain:charset=utf-8");
      response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
      ctx.writeAndFlush(response);
    }
  }
}
