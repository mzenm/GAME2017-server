package cn.wzj.netty.codec;

import java.nio.ByteOrder;

import cn.wzj.netty.pojo.MsgEntity;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtoEncoder extends MessageToByteEncoder<MsgEntity>{

	@Override
	protected void encode(ChannelHandlerContext ctx, MsgEntity in, ByteBuf out) throws Exception {
		out.writeInt(in.getLength());
		out.writeInt(in.getType());
		out.writeBytes(in.getData());
	}

}
