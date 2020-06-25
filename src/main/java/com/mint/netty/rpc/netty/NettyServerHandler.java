package com.mint.netty.rpc.netty;

import com.mint.netty.rpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author MintsBerry
 * @date 2020/6/25
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    super.exceptionCaught(ctx, cause);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if(msg.toString().startsWith("HelloService#hello")) {
      String hello = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
      ctx.writeAndFlush(hello);
    }
  }
}
