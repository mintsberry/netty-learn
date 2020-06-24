package com.mint.netty.inandoutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author MintsBerry
 * @date 2020/6/24
 */
public class LongToByteEncode extends MessageToByteEncoder<Long> {
  @Override
  protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
    System.out.println("encode");
    out.writeLong(msg);
  }
}
