package com.mint.netty.rpc.netty;

import com.mint.netty.rpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author MintsBerry
 * @date 2020/6/25
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

  private ChannelHandlerContext context;
  private String result;
  private String param;

  @Override
  public synchronized Object call() throws Exception {
    System.out.println(" call1 被调用  ");
    context.writeAndFlush(param);
    //进行wait
    wait(); //等待channelRead 方法获取到服务器的结果后，唤醒
    System.out.println(" call2 被调用  ");
    return  result; //服务方返回的结果
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    context = ctx;
  }

  @Override
  public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    result = msg.toString();
    notify();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    super.exceptionCaught(ctx, cause);
  }

  public void setParam(String param) {
    this.param = param;
  }

}
