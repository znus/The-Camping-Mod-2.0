package rikmuld.camping.core.register;

import rikmuld.api.core.register.ClassRegistry;
import rikmuld.camping.entity.tileentity.TileEntityAntlerThrophy;
import rikmuld.camping.entity.tileentity.TileEntityBearTrap;
import rikmuld.camping.entity.tileentity.TileEntityBerry;
import rikmuld.camping.entity.tileentity.TileEntityBounds;
import rikmuld.camping.entity.tileentity.TileEntityCampfireCook;
import rikmuld.camping.entity.tileentity.TileEntityCampfireDeco;
import rikmuld.camping.entity.tileentity.TileEntityLantern;
import rikmuld.camping.entity.tileentity.TileEntityLight;
import rikmuld.camping.entity.tileentity.TileEntityLog;
import rikmuld.camping.entity.tileentity.TileEntityRotation;
import rikmuld.camping.entity.tileentity.TileEntitySleepingBag;
import rikmuld.camping.entity.tileentity.TileEntityTent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileentitys {

	public static void init()
	{
		ClassRegistry.registerNewTileEntityClass(TileEntityCampfireCook.class);
		ClassRegistry.registerNewTileEntityClass(TileEntityCampfireDeco.class);
		ClassRegistry.registerNewTileEntityClass(TileEntityLight.class);
		ClassRegistry.registerNewTileEntityClass(TileEntityLog.class);
		ClassRegistry.registerNewTileEntityClass(TileEntityLantern.class);
		ClassRegistry.registerNewTileEntityClass(TileEntityRotation.class, "rotation");
		ClassRegistry.registerNewTileEntityClass(TileEntitySleepingBag.class);
		ClassRegistry.registerNewTileEntityClass(TileEntityBerry.class);
		ClassRegistry.registerNewTileEntityClass(TileEntityTent.class);
		ClassRegistry.registerNewTileEntityClass(TileEntityBounds.class);
		ClassRegistry.registerNewTileEntityClass(TileEntityBearTrap.class);
		ClassRegistry.registerNewTileEntityClass(TileEntityAntlerThrophy.class);
	}
}
