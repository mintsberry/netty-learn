package com.mint.netty.rpc.common;

/**
 * @author MintsBerry
 * @date 2020/6/25
 */
public interface HelloService {
  /**
   * 公共接口
   * @param message 消息
   * @return 结果
   */
  String hello(String message);
}
