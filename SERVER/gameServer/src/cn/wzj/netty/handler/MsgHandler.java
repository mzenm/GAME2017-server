package cn.wzj.netty.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

import cn.wzj.netty.pojo.MsgEntity;
import cn.wzj.netty.pojo.MsgType;
import cn.wzj.netty.proto.DatGeneral;
import cn.wzj.netty.proto.DatUserData;
import cn.wzj.netty.proto.MsgLogin;
import cn.wzj.netty.proto.MsgRoleInit;
import cn.wzj.netty.proto.MsgUserData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MsgHandler extends ChannelInboundHandlerAdapter  {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        try {
        	MsgEntity en = (MsgEntity) msg;
        	int type = en.getType();
        	int newLength = 4;
        	MsgEntity out = null;
        	byte[] bt;
        	switch (type){
        		case MsgType.C2S_Login:
            		MsgLogin.C2S_Login login = MsgLogin.C2S_Login.parseFrom(en.getData());
            		MsgLogin.S2C_Login.Builder builder = MsgLogin.S2C_Login.newBuilder();
            		builder.setRet(0);
            		builder.setUid("10086");
            		builder.setCode("asdf2");
            		bt = builder.build().toByteArray();
            		newLength += bt.length;
            		out =  new MsgEntity(newLength,MsgType.S2C_Login,bt);
            		break;
        		case MsgType.C2S_UserData:
        			MsgUserData.C2S_UserData userData = MsgUserData.C2S_UserData.parseFrom(en.getData());
        			MsgUserData.S2C_UserData.Builder uBuilder = MsgUserData.S2C_UserData.newBuilder();
        			DatUserData.DAT_UserData.Builder dBuilder = DatUserData.DAT_UserData.newBuilder();
        			DatGeneral.DAT_ElementProperty.Builder eBuilder = DatGeneral.DAT_ElementProperty.newBuilder();
        			eBuilder.setAir(66);
        			eBuilder.setEarth(77);
        			eBuilder.setFire(88);
        			eBuilder.setWater(99);
        			dBuilder.setElementProperty(eBuilder);
        			dBuilder.setAgility(88);
        			dBuilder.setExperience(880);
        			dBuilder.setGem(0);
        			dBuilder.setGold(100);
        			dBuilder.setLv(1);
        			dBuilder.setMagic(100);
        			dBuilder.setNickname("ABC");
        			dBuilder.setStrength(100);
        			dBuilder.setRoleId("12");
        			dBuilder.setUid("11");
        			dBuilder.setKeys(eBuilder);
        			uBuilder.setNewUser(false);
        			uBuilder.setRet(0);
        			
        			uBuilder.setUserData(dBuilder);
        			bt = uBuilder.build().toByteArray();
            		newLength += bt.length;
            		out =  new MsgEntity(newLength,MsgType.S2C_UserData,bt);
            		break;
        		case MsgType.C2S_RoleInit:
        			File heroJson = new File("C:/Users/vic7or/Documents/game/GAME2017-server/SERVER/lib/hero.json");
        			Scanner in = new Scanner(new FileInputStream(heroJson));
        			StringBuffer jsonString = new StringBuffer();
        			while(in.hasNext()){
        				jsonString.append(in.next());
        			}
        			System.out.println(jsonString);
        			
           			MsgRoleInit.S2C_RoleInit.Builder u2Builder = MsgRoleInit.S2C_RoleInit.newBuilder();
        			DatUserData.DAT_UserData.Builder d2Builder = DatUserData.DAT_UserData.newBuilder();
        			DatGeneral.DAT_ElementProperty.Builder e2Builder = DatGeneral.DAT_ElementProperty.newBuilder();
        			e2Builder.setAir(66);
        			e2Builder.setEarth(77);
        			e2Builder.setFire(88);
        			e2Builder.setWater(99);
        			d2Builder.setElementProperty(e2Builder);
        			d2Builder.setAgility(88);
        			d2Builder.setExperience(880);
        			d2Builder.setGem(0);
        			d2Builder.setGold(100);
        			d2Builder.setLv(1);
        			d2Builder.setMagic(100);
        			d2Builder.setNickname("ABC");
        			d2Builder.setStrength(100);
        			d2Builder.setRoleId("12");
        			d2Builder.setUid("11");
        			d2Builder.setKeys(e2Builder);
        			u2Builder.setRet(0);
        			
        			u2Builder.setUserData(d2Builder);
        			bt = u2Builder.build().toByteArray();
            		newLength += bt.length;
            		out =  new MsgEntity(newLength,MsgType.S2C_RoleInit,bt);
            		break;
        			
        	}
        	ctx.write(out);
        	
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}
