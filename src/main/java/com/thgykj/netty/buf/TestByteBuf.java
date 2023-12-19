package com.thgykj.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * Description TODO
 * DATA 2023-12-19
 *
 * @Author ttt
 */
public class TestByteBuf {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        ByteBuf buf = ByteBufAllocator.DEFAULT.heapBuffer();
        System.out.println(buffer.getClass());
        System.out.println(buf.getClass());
    }
}
