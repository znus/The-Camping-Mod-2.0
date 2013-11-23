package rikmuld.camping.client.render.block;

import java.nio.FloatBuffer;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.model.techne.TechneModel;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import rikmuld.camping.client.render.model.AbstractBox;
import rikmuld.camping.core.lib.TextureInfo;
import rikmuld.camping.core.register.ModBlocks;
import rikmuld.camping.core.register.ModLogger;
import rikmuld.camping.core.register.ModModels;
import rikmuld.camping.core.util.BlockUtil;
import rikmuld.camping.entity.tileentity.TileEntityBerry;
import rikmuld.camping.entity.tileentity.TileEntityLog;
import rikmuld.camping.entity.tileentity.TileEntitySleepingBag;

public class TileEntityBerryRenderer extends TileEntitySpecialRenderer{
	  
	AbstractBox	block = new AbstractBox(64, 32, false, 0, 0, 0, 0, 0, 16, 16, 16, 0.0625F, 0, 0, 0);

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float fl)
	{
		TileEntityBerry tile = (TileEntityBerry) tileentity;
		
		GL11.glPushMatrix();
	    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	    
		GL11.glTranslatef((float) x, (float) y+1, (float) z+1F);
		GL11.glScalef(1.0F, -1F, -1F);
	    
		if(tile.berries)
		{
			if(tile.worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)==1)bindTexture(new ResourceLocation(TextureInfo.MODEL_BERRY_BLACK));
			else bindTexture(new ResourceLocation(TextureInfo.MODEL_BERRY_RED));
		}
		else bindTexture(new ResourceLocation(TextureInfo.MODEL_BERRY_EMPTY));
				
		block.render(Tessellator.instance);	
		GL11.glPopMatrix();
	}
}
