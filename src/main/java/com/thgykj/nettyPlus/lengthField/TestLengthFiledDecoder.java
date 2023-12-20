package com.thgykj.nettyPlus.lengthField;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * Description TODO
 * DATA 2023-12-20
 *
 * @Author ttt
 */
public class TestLengthFiledDecoder {
    public static void main(String[] args) {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 0), new LoggingHandler());
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer,"hello world");
        send(buffer, "Hi!");
        embeddedChannel.writeInbound(buffer);
    }

    private static void send(ByteBuf buffer , String content){
        byte[] bytes = content.getBytes();
        int length = bytes.length;
        buffer.writeByte(length);
        buffer.writeBytes(bytes);
    }
}
