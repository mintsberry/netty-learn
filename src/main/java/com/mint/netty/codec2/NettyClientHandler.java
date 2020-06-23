package com.mint.netty.codec2;

import com.mint.netty.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    int random = new Random().nextInt(3);
    MyDataInfo.MyMessage myMessage = null;
    if (random == 0) {
      myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType).setStudent(MyDataInfo.Student.newBuilder().setId(5).setName("Mint").build()).build();
    } else {
      myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType).setWorker(MyDataInfo.Worker.newBuilder().setAge(20).setName("Ling").build()).build();
    }
    System.out.println("message send");
    ctx.writeAndFlush(myMessage);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
  }
}
