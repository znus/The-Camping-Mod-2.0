package rikmuld.camping.entity.tileentity;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import rikmuld.camping.core.register.ModBlocks;

public class TileEntityBerry extends TileEntityMain {

	public boolean berries = false;

	int maxTime = 30;
	int time = 0;
	int update = 0;

	public Random rand = new Random();

	public boolean noDecay;
	private boolean startDecay;
	private int decay;

	public void getBerries()
	{
		if(!worldObj.isRemote)
		{
			berries = false;
			time = 0;
			sendTileData(0, true, 0);
		}
	}

	public boolean getIsPlacedByHand()
	{
		return noDecay;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		time = tag.getInteger("time");
		decay = tag.getInteger("decay");
		berries = tag.getBoolean("berries");
		noDecay = tag.getBoolean("noDecay");
		startDecay = tag.getBoolean("startDecay");
	}

	@Override
	public void setTileData(int id, int[] data)
	{
		if(id == 0)
		{
			berries = data[0] == 1? true:false;
		}
	}

	public void spreadDecay()
	{
		startDecay();

		if((worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord) instanceof TileEntityBerry) && !((TileEntityBerry)worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord)).startDecay && !((TileEntityBerry)worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord)).getIsPlacedByHand())
		{
			((TileEntityBerry)worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord)).spreadDecay();
		}
		if((worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord) instanceof TileEntityBerry) && !((TileEntityBerry)worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord)).startDecay && !((TileEntityBerry)worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord)).getIsPlacedByHand())
		{
			((TileEntityBerry)worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord)).spreadDecay();
		}
		if((worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1) instanceof TileEntityBerry) && !((TileEntityBerry)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1)).startDecay && !((TileEntityBerry)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1)).getIsPlacedByHand())
		{
			((TileEntityBerry)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1)).spreadDecay();
		}
		if((worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1) instanceof TileEntityBerry) && !((TileEntityBerry)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1)).startDecay && !((TileEntityBerry)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1)).getIsPlacedByHand())
		{
			((TileEntityBerry)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1)).spreadDecay();
		}
		if((worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord) instanceof TileEntityBerry) && !((TileEntityBerry)worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord)).startDecay && !((TileEntityBerry)worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord)).getIsPlacedByHand())
		{
			((TileEntityBerry)worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord)).spreadDecay();
		}
		if((worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord) instanceof TileEntityBerry) && !((TileEntityBerry)worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord)).startDecay && !((TileEntityBerry)worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord)).getIsPlacedByHand())
		{
			((TileEntityBerry)worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord)).spreadDecay();
		}
	}

	public void startDecay()
	{
		if(!noDecay)
		{
			startDecay = true;
			decay = rand.nextInt(300) + 200;
		}
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote && (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) > 0))
		{
			if((time >= maxTime) && !berries)
			{
				berries = true;
				sendTileData(0, true, 1);
				worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}

			update++;

			if(update > 20)
			{
				if((berries == false) && (rand.nextInt(10) == 0))
				{
					time++;
				}
				update = 0;
			}
		}
		if(!worldObj.isRemote)
		{
			if(startDecay && (rand.nextInt(decay) == 0))
			{
				ModBlocks.leaves.dropBlockAsItem(worldObj, xCoord, yCoord, zCoord, 0, 1);
				worldObj.setBlock(xCoord, yCoord, zCoord, 0);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("time", time);
		tag.setInteger("decay", decay);
		tag.setBoolean("berries", berries);
		tag.setBoolean("noDecay", noDecay);
		tag.setBoolean("startDecay", startDecay);
	}
}
