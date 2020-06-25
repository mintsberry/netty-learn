package com.mint.netty.tcpprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author MintsBerry
 * @date 2020/6/24
 */
public class MessageEncoder extends MessageToByteEncoder<MessageProtocol> {
  @Override
  protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
    out.writeInt(msg.getLength());
    out.writeBytes(msg.getContent());
  }
}
