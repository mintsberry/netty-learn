package com.mint.netty.rpc.customer;

import com.mint.netty.rpc.common.HelloService;
import com.mint.netty.rpc.netty.NettyClient;

/**
 * @author MintsBerry
 * @date 2020/6/25
 */
public class ClientBootstrap {
  public static final String PROVIDER_NAME = "HelloService#hello#";

  public static void main(String[] args) {
    NettyClient nettyClient = new NettyClient();
    HelloService helloService = (HelloService) nettyClient.getBean(HelloService.class, PROVIDER_NAME);
    String hi = helloService.hello("Hi");
    System.out.println(hi);
  }
}
