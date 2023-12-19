package com.thgykj.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Description 服务端
 * DATA 2023-12-14
 *
 * @Author ttt
 */
public class HelloServer {
    public static void main(String[] args) {
        // 1.服务器端的启动器  负责组装netty的组件，启动服务器
        new ServerBootstrap()
                // 2. 添加组件 BoosEventLoop ，WorkerEventLoop(selector，thread) 检测 ，group 组
                .group(new NioEventLoopGroup())
                // 3. 选择服务器的SocketChannel的实现
                .channel(NioServerSocketChannel.class)
                // 4. 处理事件需要分工 boss 负责处理连接 worker(child) 负责读写 ， 决定那些worker（child）能执行那些操作（handler）
                .childHandler(
                        // 5.channel 代表和客户端进行数据读写的通道Initializer 初始化 （也是一个特殊的handler）负责添加别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        // 6.添加具体的handler
                        ch.pipeline().addLast(new StringDecoder());  // 将 ByteBuf 装换成为字符串
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            // 自定义handler
                            @Override //读事件
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                // 打印上一步装换号的字符串
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8080); // 4
    }
}
