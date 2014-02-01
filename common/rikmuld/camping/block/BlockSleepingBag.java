package rikmuld.camping.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rikmuld.camping.core.register.ModAchievements;
import rikmuld.camping.core.util.ItemStackUtil;
import rikmuld.camping.entity.tileentity.TileEntityRotation;
import rikmuld.camping.entity.tileentity.TileEntitySleepingBag;
import rikmuld.camping.item.itemblock.ItemBlockSleepingBag;

public class BlockSleepingBag extends BlockRotationMain {

	public BlockSleepingBag(String name)
	{
		super(name, Material.cloth, ItemBlockSleepingBag.metadataNames, ItemBlockSleepingBag.class, false);
		setStepSound(soundClothFootstep);
		setHardness(0.5F);
		setBlockBounds(0, 0, 0, 1, 0.0625F, 1);
	}

	public static boolean isBlockHeadOfBed(int meta)
	{
		return meta == 0;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)
	{
		if(world.getBlockMetadata(x, y, z) == 1)
		{
			((TileEntitySleepingBag)world.getBlockTileEntity(x, y, z)).breakHead();
		}
		else
		{
			ItemStackUtil.dropItemsInWorld(new ItemStack[]{new ItemStack(blockID, 1, 0)}, world, x, y, z);
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		int l = world.getBlockId(x, y, z);
		Block block = Block.blocksList[l];
		return ((block == null) || block.isBlockReplaceable(world, x, y, z)) && world.doesBlockHaveSolidTopSurface(x, y - 1, z);
	}

	@Override
	public TileEntityRotation createNewTileEntity(World world)
	{
		return new TileEntitySleepingBag();
	}

	public void dropIfCantStay(World world, int x, int y, int z)
	{
		if(!world.doesBlockHaveSolidTopSurface(x, y - 1, z))
		{
			world.setBlock(x, y, z, 0);
		}
	}

	@Override
	public int getBedDirection(IBlockAccess world, int x, int y, int z)
	{
		int rotation = ((TileEntityRotation)world.getBlockTileEntity(x, y, z)).rotation;
		return (rotation + 2) < 4? rotation + 2:((rotation + 2) < 5? 0:1);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public Icon getIcon(int side, int metadata)
	{
		return Block.blocksList[Block.cloth.blockID].getIcon(0, 0);
	}

	@Override
	public final int getRenderType()
	{
		return -1;
	}

	@Override
	public boolean isBed(World world, int x, int y, int z, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public boolean isBedFoot(IBlockAccess world, int x, int y, int z)
	{
		return !isBlockHeadOfBed(world.getBlockMetadata(x, y, z));
	}

	@Override
	public final boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9)
	{
		if(!world.isRemote)
		{
			int rotation = ((TileEntityRotation)world.getBlockTileEntity(x, y, z)).rotation;

			int xPos = rotation == 1? x + 1:rotation == 3? x - 1:x;
			int zPos = rotation == 0? z - 1:rotation == 2? z + 1:z;

			if(world.getBlockMetadata(x, y, z) == 1) return onBlockActivated(world, xPos, y, zPos, player, side, par7, par8, par9);
			else if(((TileEntitySleepingBag)world.getBlockTileEntity(x, y, z)).sleepingPlayer == null)
			{
				EnumStatus state = player.sleepInBedAt(x, y, z);

				if(state == EnumStatus.OK)
				{
					ModAchievements.bagSleeping.addStatToPlayer(player);
					((TileEntitySleepingBag)world.getBlockTileEntity(x, y, z)).sleepingPlayer = player;
					return true;
				}
				else
				{
					if(state == EnumStatus.NOT_POSSIBLE_NOW)
					{
						player.addChatMessage("tile.bed.noSleep");
					}
					else if(state == EnumStatus.NOT_SAFE)
					{
						player.addChatMessage("tile.bed.notSafe");
					}
					return true;
				}
			}
			else
			{
				player.addChatMessage("This sleeping bag is occupied!");
			}
		}
		return true;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		world.scheduleBlockUpdate(x, y, z, blockID, 10);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id)
	{
		world.scheduleBlockUpdate(x, y, z, blockID, 10);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public final boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if(!world.isRemote)
		{
			dropIfCantStay(world, x, y, z);
		}
	}
}
