package com.thgykj.agreement;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * Description 测试redis的协议
 * DATA 2023-12-21
 *
 * @Author ttt
 */
public class TestRedisNetty {
    private static final Logger logger = LoggerFactory.getLogger(TestRedisNetty.class);
    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup worker = new NioEventLoopGroup();

        byte[] LINE = {13, 10};
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(worker);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                logger.info("11111");
                ch.pipeline().addLast(new LoggingHandler());
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        set(ctx);
                        get(ctx);
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ByteBuf buf = (ByteBuf) msg;
                        logger.info("redis返回的数据{}",buf.toString(Charset.defaultCharset()));
                    }

                    private void get(ChannelHandlerContext ctx) {
                        ByteBuf buf = ctx.alloc().buffer();
                        buf.writeBytes("*2".getBytes());
                        logger.info("byte的数据{}","*2".getBytes());
                        buf.writeBytes(LINE);
                        buf.writeBytes("$3".getBytes());
                        buf.writeBytes(LINE);
                        buf.writeBytes("get".getBytes());
                        buf.writeBytes(LINE);
                        buf.writeBytes("$3".getBytes());
                        buf.writeBytes(LINE);
                        buf.writeBytes("aaa".getBytes());
                        buf.writeBytes(LINE);
                        ctx.writeAndFlush(buf);
                    }
                    private void set(ChannelHandlerContext ctx) {
                        ByteBuf buf = ctx.alloc().buffer();
                        buf.writeBytes("*3".getBytes());
                        buf.writeBytes(LINE);
                        buf.writeBytes("$3".getBytes());
                        buf.writeBytes(LINE);
                        buf.writeBytes("set".getBytes());
                        buf.writeBytes(LINE);
                        buf.writeBytes("$3".getBytes());
                        buf.writeBytes(LINE);
                        buf.writeBytes("aaa".getBytes());
                        buf.writeBytes(LINE);
                        buf.writeBytes("$3".getBytes());
                        buf.writeBytes(LINE);
                        buf.writeBytes("bbb".getBytes());
                        buf.writeBytes(LINE);
                        ctx.writeAndFlush(buf);
                    }
                });

            }
        });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6379).sync();
        channelFuture.channel().closeFuture().sync();
    }
}
