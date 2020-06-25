package com.mint.netty.tcpprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author MintsBerry
 * @date 2020/6/24
 */
public class MessageDecoder extends ReplayingDecoder<Void> {

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    int length = in.readInt();
    byte[] content = new byte[length];
    in.readBytes(content);

    MessageProtocol messageProtocol = new MessageProtocol();
    messageProtocol.setLength(length);
    messageProtocol.setContent(content);
    out.add(messageProtocol);
  }
}
