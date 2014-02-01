package rikmuld.camping.client.gui.screen;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import rikmuld.camping.CampingMod;
import rikmuld.camping.core.lib.GuiInfo;
import rikmuld.camping.core.lib.TextureInfo;
import rikmuld.camping.core.util.PacketUtil;
import rikmuld.camping.entity.tileentity.TileEntityTent;
import rikmuld.camping.network.packets.PacketOpenGui;

public class GuiScreenTent extends GuiScreen {

	TileEntityTent tent;
	boolean[] canClick = new boolean[]{false, false, false};

	public GuiScreenTent(TileEntity tile)
	{
		tent = (TileEntityTent)tile;
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		switch(button.id)
		{
			case 0:
				tent.removeAll();
				break;
			case 1:
				tent.removeBed();
				break;
			case 2:
				tent.removeLantern();
				break;
			case 3:
				tent.removeChest();
				break;
		}
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	public void drawCenteredString(FontRenderer fontRender, String text, int x, int y, int color)
	{
		fontRender.drawString(text, x - (fontRender.getStringWidth(text) / 2), y, color);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partitialTicks)
	{
		drawDefaultBackground();

		int guiLeft = (width - 255) / 2;
		int guiTop = (height - 160) / 2;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation(TextureInfo.GUI_TENT));
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 255, 160);

		if(tent.lanterns == 0)
		{
			drawTexturedModalRect(guiLeft + 32, guiTop + 78, 0, 160, 51, 53);
		}
		if(tent.chests == 0)
		{
			drawTexturedModalRect(guiLeft + 102, guiTop + 78, 51, 160, 51, 53);
		}
		if(tent.beds == 0)
		{
			drawTexturedModalRect(guiLeft + 172, guiTop + 78, 102, 160, 51, 53);
		}

		GL11.glPushMatrix();
		drawCenteredString(fontRenderer, "Space Left: " + tent.contends + "/" + tent.maxContends, (width / 2) - 45, guiTop + 10, 0);
		drawCenteredString(fontRenderer, "Beds: " + tent.beds + "/" + tent.maxBeds, (width / 2) - 45, guiTop + 30, 0);
		drawCenteredString(fontRenderer, "Lanterns: " + tent.lanterns + "/" + tent.maxLanterns, (width / 2) - 45, guiTop + 40, 0);
		drawCenteredString(fontRenderer, "Chests: " + tent.chests + "/" + tent.maxChests, (width / 2) - 45, guiTop + 50, 0);

		GL11.glScalef(0.8F, 0.8F, 0.8F);
		drawCenteredString(fontRenderer, "Manage Inventory", (int)((width / 2) * 1.25F), (int)(guiTop * 1.25F) + (int)(142 * 1.25F), 0);
		drawCenteredString(fontRenderer, "Manage Lantern", (int)((width / 2) * 1.25F) - (int)(80 * 1.25F), (int)(guiTop * 1.25F) + (int)(142 * 1.25F), 0);
		drawCenteredString(fontRenderer, "Manage Sleeping", (int)((width / 2) * 1.25F) + (int)(80 * 1.25F), (int)(guiTop * 1.25F) + (int)(142 * 1.25F), 0);
		GL11.glPopMatrix();

		if(isPointInRegion(172, 78, 51, 53, mouseX, mouseY, guiLeft, guiTop) && (tent.beds > 0))
		{
			if(Mouse.isButtonDown(0) && canClick[0])
			{
				mc.thePlayer.openGui(CampingMod.instance, GuiInfo.GUI_TENT_SLEEP, tent.worldObj, tent.xCoord, tent.yCoord, tent.zCoord);
			}
			if(!Mouse.isButtonDown(0))
			{
				canClick[0] = true;
			}
		}
		else
		{
			canClick[0] = false;
		}

		if(isPointInRegion(102, 78, 51, 53, mouseX, mouseY, guiLeft, guiTop) && (tent.chests > 0))
		{
			if(Mouse.isButtonDown(0) && canClick[1])
			{
				PacketUtil.sendToSever(new PacketOpenGui(GuiInfo.GUI_TENT_CHESTS, tent.xCoord, tent.yCoord, tent.zCoord));
			}
			if(!Mouse.isButtonDown(0))
			{
				canClick[1] = true;
			}
		}
		else
		{
			canClick[1] = false;
		}

		if(isPointInRegion(32, 78, 51, 53, mouseX, mouseY, guiLeft, guiTop) && (tent.lanterns > 0))
		{
			if(Mouse.isButtonDown(0) && canClick[2])
			{
				PacketUtil.sendToSever(new PacketOpenGui(GuiInfo.GUI_TENT_LANTERN, tent.xCoord, tent.yCoord, tent.zCoord));
			}
			if(!Mouse.isButtonDown(0))
			{
				canClick[2] = true;
			}
		}
		else
		{
			canClick[2] = false;
		}

		super.drawScreen(mouseX, mouseY, partitialTicks);
	}

	@Override
	public void initGui()
	{
		super.initGui();

		int guiTop = (height - 160) / 2;

		buttonList.add(new GuiButton(0, (width / 2) + 4, (guiTop + 10) - 2, 85, 10, "Clear All"));
		buttonList.add(new GuiButton(1, (width / 2) + 4, (guiTop + 30) - 2, 85, 10, "Remove Bed"));
		buttonList.add(new GuiButton(2, (width / 2) + 4, (guiTop + 40) - 2, 85, 10, "Remove Lantern"));
		buttonList.add(new GuiButton(3, (width / 2) + 4, (guiTop + 50) - 2, 85, 10, "Remove Chest"));
	}

	private boolean isPointInRegion(int x, int y, int width, int height, int pointX, int pointY, int guiLeft, int guiTop)
	{
		pointX -= guiLeft;
		pointY -= guiTop;
		return (pointX >= (x - 1)) && (pointX < (x + width + 1)) && (pointY >= (y - 1)) && (pointY < (y + height + 1));
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		if(!mc.thePlayer.isEntityAlive() || mc.thePlayer.isDead)
		{
			mc.thePlayer.closeScreen();
		}
	}
}
