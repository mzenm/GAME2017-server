package cn.wzj.netty.codec;

import java.nio.ByteOrder;
import java.util.List;

import cn.wzj.netty.pojo.MsgEntity;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ProtoDecoder extends ByteToMessageDecoder  {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes()<=4){
			return;
		}
		int length = in.readInt();
		if(in.readableBytes()<length){
			return;
		}
		int type = in.readInt();
		System.out.println(length);
		System.out.println(type);
		byte[] bd = new byte[length-4];
		in.readBytes(bd);
		out.add(new MsgEntity(length,type,bd));
	}

}
