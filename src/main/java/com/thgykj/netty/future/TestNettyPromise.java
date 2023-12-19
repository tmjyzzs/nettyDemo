package com.thgykj.netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * Description TODO
 * DATA 2023-12-19
 *
 * @Author ttt
 */
@Slf4j
public class TestNettyPromise {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop next = new NioEventLoopGroup().next();
        DefaultPromise<Object> promise = new DefaultPromise<>(next);

        new Thread(()->{
            log.info("开始计算");
            promise.setSuccess(50);
        }).start();

        log.info("计算结果",promise.get());

        log.info("代码结束");
    }
}
