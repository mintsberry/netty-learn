package com.mint.netty.inandoutbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author LingXi
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new ByteToLongDecoder())
            .addLast(new LongToByteEncode())
            .addLast(new ServerHandler());
  }
}
