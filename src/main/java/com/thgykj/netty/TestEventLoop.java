package com.thgykj.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

/**
 * Description TODO
 * DATA 2023-12-15
 *
 * @Author ttt
 */
public class TestEventLoop {
    public static void main(String[] args) {
        EventLoopGroup eventExecutors = new NioEventLoopGroup(2); //io事件、普通任务、任务
        // 获取下一个事件循环
        System.out.println(eventExecutors.next());
        System.out.println(eventExecutors.next());
        System.out.println(eventExecutors.next());

        // 执行定时任务  -- 可以使用在心跳响应
        eventExecutors.next().scheduleAtFixedRate(()->{
            System.out.println("ok");
        },0,1, TimeUnit.SECONDS);
    }
}
