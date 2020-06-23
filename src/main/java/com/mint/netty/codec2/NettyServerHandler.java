package com.mint.netty.codec2;

import com.mint.netty.codec.StudentPOJO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author LingXi
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {


  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
    MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
    if (dataType == MyDataInfo.MyMessage.DataType.StudentType) {
      MyDataInfo.Student student = msg.getStudent();
      System.out.println("Student: " + student.getName());
    } else if (dataType == MyDataInfo.MyMessage.DataType.WorkerType) {
      MyDataInfo.Worker worker = msg.getWorker();
      System.out.println("Worker: " + worker.getName());
    }else {
      System.out.println("Type error");
    }
  }
}
