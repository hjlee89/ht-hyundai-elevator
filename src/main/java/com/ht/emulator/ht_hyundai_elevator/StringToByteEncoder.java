package com.ht.emulator.ht_hyundai_elevator;

import java.nio.CharBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

public class StringToByteEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String frame, ByteBuf out) throws Exception {
        ByteBuf outbound = ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(frame),
                CharsetUtil.US_ASCII);
        out.writeBytes(outbound);
        outbound.release();
    }

}
