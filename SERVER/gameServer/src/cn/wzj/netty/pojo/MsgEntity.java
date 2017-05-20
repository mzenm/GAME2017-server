package cn.wzj.netty.pojo;

import io.netty.buffer.ByteBuf;

public class MsgEntity {
	private int length;
	private int type;
	private byte[] data;
	
	public MsgEntity(int length,int type,byte[] data){
		this.length=length;
		this.type=type;
		this.data=data;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	
}
