package com.mint.netty.inandoutbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author MintsBerry
 * @date 2020/6/24
 */
public class  ClientInitialize extends ChannelInitializer<SocketChannel> {
  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new LongToByteEncode())
            .addLast(new ByteToLongDecoder())
            .addLast(new ClientHandler());
  }
}
