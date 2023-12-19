package com.thgykj.netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

/**
 * Description TODO
 * DATA 2023-12-19
 *
 * @Author ttt
 */
@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        EventLoop next = eventExecutors.next();
        Future<Integer> future = next.submit(() -> {
            log.info("开始计算");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 70;
        });
        future.addListener((result)->{
            log.info("接收计算结果",result);
        });
        log.info("代码结束");
    }
}
