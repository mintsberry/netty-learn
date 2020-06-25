package com.mint.netty.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author MintsBerry
 * @date 2020/6/25
 */
public class NettyClient {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler client;

    private static void initClient() throws InterruptedException {
      client = new NettyClientHandler();
      NioEventLoopGroup group = new NioEventLoopGroup();
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(group)
              .channel(NioSocketChannel.class)
              .option(ChannelOption.TCP_NODELAY, true)
              .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ChannelPipeline pipeline = ch.pipeline();
                  pipeline.addLast(new StringDecoder())
                          .addLast(new StringEncoder())
                          .addLast(client);
                }
              });
      bootstrap.connect("127.0.0.1", 6666).sync();
    }

    public Object getBean(final Class<?> serviceClass, final String providerName) {
      return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
              new Class<?>[] {serviceClass}, (proxy, method, args) -> {
                if (client == null){
                  initClient();
                }
                client.setParam(providerName + args[0]);
                return executorService.submit(client).get();
              });
    }
}
