package net.itsrelizc.hooks;

import java.lang.reflect.Field;

import org.bukkit.entity.Player;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class SimplePacketHandler extends ChannelDuplexHandler {
	private Player p;

	public SimplePacketHandler(final Player p) {
		this.p = p;
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		super.write(ctx, msg, promise);
	}

	@Override
	public void channelRead(ChannelHandlerContext c, Object m) throws Exception {
		System.out.print(p.getName() + " " + m.getClass().getName());
		
		super.channelRead(c, m);
	}
}
