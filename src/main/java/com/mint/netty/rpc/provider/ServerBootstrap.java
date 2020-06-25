package com.mint.netty.rpc.provider;

import com.mint.netty.rpc.netty.NettyServer;

/**
 * @author MintsBerry
 * @date 2020/6/25
 */
public class ServerBootstrap {


  public static void main(String[] args) {
    NettyServer.startServer("127.0.0.1", 6666);
  }
}
