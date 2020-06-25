package com.mint.netty.rpc.provider;

import com.mint.netty.rpc.common.HelloService;

/**
 * @author MintsBerry
 * @date 2020/6/25
 */
public class HelloServiceImpl implements HelloService {
  @Override
  public String hello(String message) {
    System.out.println("receiver message: " + message);
    if (message != null) {
      return "Hello Client, I receiver message:" + message;
    } else {
      return "Hello Client, I receiver blank message ";
    }
  }
}
