package cn.wzj.netty;

import java.io.File;

import cn.wzj.netty.codec.ProtoDecoder;
import cn.wzj.netty.codec.ProtoEncoder;
import cn.wzj.netty.handler.MsgHandler;
import cn.wzj.netty.proto.MsgLogin;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;


public class ProtoServer {
	static final EventExecutorGroup group = new DefaultEventExecutorGroup(16); 

	public void start(int port) throws Exception{
		EventLoopGroup bossGroups = new NioEventLoopGroup();
		EventLoopGroup workerGroups = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroups,workerGroups)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 100)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ProtoDecoder());
					ch.pipeline().addLast(new ProtoEncoder());
					ch.pipeline().addLast(group,"handler",new MsgHandler());
				}
			});
			
			ChannelFuture f = b.bind(port).sync();
			
			f.channel().closeFuture().sync();
		}finally{
			bossGroups.shutdownGracefully();
			workerGroups.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception{
		int port = 8008;
		new ProtoServer().start(port);
	}
}
