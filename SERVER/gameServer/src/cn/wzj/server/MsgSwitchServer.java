package cn.wzj.server;

import cn.wzj.netty.pojo.MsgEntity;

public interface MsgSwitchServer {
	public MsgEntity getMsgEntity(int type);
}
