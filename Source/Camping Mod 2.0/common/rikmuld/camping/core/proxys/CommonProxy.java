package rikmuld.camping.core.proxys;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rikmuld.camping.client.gui.container.GuiContainerBackpack;
import rikmuld.camping.client.gui.container.GuiContainerCampfireCook;
import rikmuld.camping.client.gui.container.GuiContainerCampfireDeco;
import rikmuld.camping.client.gui.container.GuiContainerCampingInvBack;
import rikmuld.camping.client.gui.container.GuiContainerCampingInvTool;
import rikmuld.camping.client.gui.container.GuiContainerKit;
import rikmuld.camping.client.gui.container.GuiContainerPlayerInv;
import rikmuld.camping.client.gui.container.GuiContainerPlayerInvCreative;
import rikmuld.camping.client.gui.container.GuiContainerTentChests;
import rikmuld.camping.client.gui.container.GuiContainerTentLanterns;
import rikmuld.camping.client.gui.container.GuiContainerTrap;
import rikmuld.camping.client.gui.screen.GuiScreenGuide;
import rikmuld.camping.client.gui.screen.GuiScreenTent;
import rikmuld.camping.client.gui.screen.GuiScreenTentSleeping;
import rikmuld.camping.core.handler.TickHandler;
import rikmuld.camping.core.lib.GuiInfo;
import rikmuld.camping.inventory.container.ContainerBackpack;
import rikmuld.camping.inventory.container.ContainerCampfireCook;
import rikmuld.camping.inventory.container.ContainerCampfireDeco;
import rikmuld.camping.inventory.container.ContainerCampinvBack;
import rikmuld.camping.inventory.container.ContainerCampinvTool;
import rikmuld.camping.inventory.container.ContainerKit;
import rikmuld.camping.inventory.container.ContainerTentChests;
import rikmuld.camping.inventory.container.ContainerTentLanterns;
import rikmuld.camping.inventory.container.ContainerTrap;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy implements IGuiHandler {

	public void checkVersion()
	{}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(ID == GuiInfo.GUI_TENT) return new GuiScreenTent(tile);
		if(ID == GuiInfo.GUI_TENT_SLEEP) return new GuiScreenTentSleeping(tile);
		if(ID == GuiInfo.GUI_TENT_LANTERN) return new GuiContainerTentLanterns(player.inventory, (IInventory)tile);
		if(ID == GuiInfo.GUI_TENT_CHESTS) return new GuiContainerTentChests(player.inventory, (IInventory)tile);
		if(ID == GuiInfo.GUI_CAMPFIRE_DECO) return new GuiContainerCampfireDeco(player.inventory, (IInventory)tile);
		if(ID == GuiInfo.GUI_BACKPACK) return new GuiContainerBackpack(player.inventory, player.getCurrentEquippedItem());
		if(ID == GuiInfo.GUI_CAMPINV_BACK) return new GuiContainerCampingInvBack(player);
		if(ID == GuiInfo.GUI_INV_PLAYER) return new GuiContainerPlayerInv(player);
		if(ID == GuiInfo.GUI_INV_CREATIVE) return new GuiContainerPlayerInvCreative(player);
		if(ID == GuiInfo.GUI_GUIDE) return new GuiScreenGuide();
		if(ID == GuiInfo.GUI_CAMPINV_TOOL) return new GuiContainerCampingInvTool(player);
		if(ID == GuiInfo.GUI_COOK) return new GuiContainerCampfireCook(player.inventory, (IInventory)tile);
		if(ID == GuiInfo.GUI_KIT) return new GuiContainerKit(player.inventory, player.getCurrentEquippedItem());
		if(ID == GuiInfo.GUI_TRAP) return new GuiContainerTrap(player.inventory, (IInventory)tile);
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(ID == GuiInfo.GUI_CAMPFIRE_DECO) return new ContainerCampfireDeco(player.inventory, (IInventory)tile);
		if(ID == GuiInfo.GUI_BACKPACK) return new ContainerBackpack(player.inventory, player.getCurrentEquippedItem());
		if(ID == GuiInfo.GUI_CAMPINV_BACK) return new ContainerCampinvBack(player);
		if(ID == GuiInfo.GUI_CAMPINV_TOOL) return new ContainerCampinvTool(player);
		if(ID == GuiInfo.GUI_INV_PLAYER) return player.inventoryContainer;
		if(ID == GuiInfo.GUI_INV_CREATIVE) return player.inventoryContainer;
		if(ID == GuiInfo.GUI_COOK) return new ContainerCampfireCook(player.inventory, (IInventory)tile);
		if(ID == GuiInfo.GUI_KIT) return new ContainerKit(player.inventory, player.getCurrentEquippedItem());
		if(ID == GuiInfo.GUI_TENT_LANTERN) return new ContainerTentLanterns(player.inventory, (IInventory)tile);
		if(ID == GuiInfo.GUI_TENT_CHESTS) return new ContainerTentChests(player.inventory, (IInventory)tile);
		if(ID == GuiInfo.GUI_TRAP) return new ContainerTrap(player.inventory, (IInventory)tile);
		return null;
	}

	public void registerGuide()
	{}

	public void registerRenderers()
	{}

	public void registerTickHandler()
	{
		TickRegistry.registerTickHandler(new TickHandler(), Side.SERVER);
	}

	public void spawnFlame(World world, double x, double y, double z, double motionX, double motionY, double motionZ, int color)
	{}
}
