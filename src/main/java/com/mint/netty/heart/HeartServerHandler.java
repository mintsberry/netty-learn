package com.mint.netty.heart;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.EventExecutorGroup;

public class HeartServerHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      IdleStateEvent event = (IdleStateEvent) evt;
      String eventType = null;
      switch (event.state()) {
        case READER_IDLE:
          eventType = "reader_idle";
          break;
        case WRITER_IDLE:
          eventType = "write_idle";
          break;
        case ALL_IDLE:
          eventType = "all_idle";
          break;
        default:
          break;
      }
      System.out.println(ctx.channel().remoteAddress() + " over time " + eventType) ;
    }
  }
}
