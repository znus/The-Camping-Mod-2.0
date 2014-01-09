package rikmuld.camping.core.register;

import rikmuld.api.core.register.ClassRegistry;
import rikmuld.camping.network.packets.PacketBounds;
import rikmuld.camping.network.packets.PacketItems;
import rikmuld.camping.network.packets.PacketMap;
import rikmuld.camping.network.packets.PacketOpenGui;
import rikmuld.camping.network.packets.PacketPlayerData;
import rikmuld.camping.network.packets.PacketPlayerSleepIntent;

public class ModPackets {
	
	public static void init()
	{
		ClassRegistry.registerNewPacketClass(PacketBounds.class);
		ClassRegistry.registerNewPacketClass(PacketItems.class);
		ClassRegistry.registerNewPacketClass(PacketMap.class);
		ClassRegistry.registerNewPacketClass(PacketOpenGui.class);
		ClassRegistry.registerNewPacketClass(PacketPlayerSleepIntent.class);
		ClassRegistry.registerNewPacketClass(PacketPlayerData.class);
	}
}
