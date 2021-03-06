package rikmuld.camping.entity.tileentity;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import rikmuld.camping.core.lib.ConfigInfo;
import rikmuld.camping.core.lib.ConfigInfo.ConfigInfoBoolean;
import rikmuld.camping.core.register.ModAchievements;
import rikmuld.camping.core.register.ModDamageSources;
import rikmuld.camping.core.register.ModItems;
import rikmuld.camping.core.register.ModPotions;
import rikmuld.camping.core.util.PacketUtil;
import rikmuld.camping.network.packets.PacketItems;

public class TileEntityBearTrap extends TileEntityInventory {

	public EntityLivingBase trappedEntity;
	public boolean open = true;
	Random random = new Random();
	public int cooldown;
	public boolean captureFlag;

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public void onInventoryChanged()
	{
		super.onInventoryChanged();
		if(!worldObj.isRemote)
		{
			PacketUtil.sendToAllPlayers(new PacketItems(0, xCoord, yCoord, zCoord, getStackInSlot(0)));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		cooldown = tag.getInteger("cooldown");
		open = tag.getBoolean("open");
		captureFlag = tag.getBoolean("captureFlag");
	}

	@Override
	public void setTileData(int id, int[] data)
	{
		if(id == 0)
		{
			open = data[0] == 1;
		}
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(trappedEntity != null)
			{
				List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(xCoord + 0.21875F, yCoord, zCoord + 0.21875, xCoord + 0.78125, yCoord + 0.1875, zCoord + 0.78125));
				if(!entities.contains(trappedEntity))
				{
					trappedEntity = null;
				}
			}

			if(cooldown > 0)
			{
				cooldown = 0;
			}

			if((trappedEntity != null) && open)
			{
				open = false;
				sendTileData(0, true, open? 1:0);
			}
			if((open || captureFlag) && (cooldown <= 0))
			{
				captureFlag = false;

				List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(xCoord + 0.21875F, yCoord, zCoord + 0.21875, xCoord + 0.78125, yCoord + 0.1875, zCoord + 0.78125));
				if(entities.size() > 0)
				{
					if((entities.get(0) instanceof EntityPlayer) && ConfigInfoBoolean.value(ConfigInfo.ENABLE_PLAYER_TRAP))
					{
						trappedEntity = entities.get(0);
					}
					else if(!(entities.get(0) instanceof EntityPlayer))
					{
						trappedEntity = entities.get(0);

						if(getStackInSlot(0) != null)
						{
							getStackInSlot(0).stackSize--;
						}
						if((getStackInSlot(0) != null) && (trappedEntity instanceof EntityAnimal) && ((EntityAnimal)trappedEntity).isBreedingItem(getStackInSlot(0)))
						{
							ModAchievements.trapBait.addStatToPlayer(worldObj.getClosestPlayer(xCoord, yCoord, zCoord, -1));
						}
						else
						{
							ModAchievements.trapLuckey.addStatToPlayer(worldObj.getClosestPlayer(xCoord, yCoord, zCoord, -1));
						}
					}
				}
				if(trappedEntity != null)
				{
					open = false;
					sendTileData(0, true, open? 1:0);
				}
			}
			if(trappedEntity != null)
			{
				if(!(trappedEntity instanceof EntityPlayer))
				{
					trappedEntity.setPositionAndUpdate(xCoord + 0.5F, yCoord, zCoord + 0.5F);
				}
				trappedEntity.setInWeb();
				if(trappedEntity instanceof EntityPlayer)
				{
					((EntityPlayer)trappedEntity).capabilities.setPlayerWalkSpeed(0.001F);
				}
				if(random.nextInt(50) == 0)
				{
					if(!(trappedEntity instanceof EntityPlayer) || !((EntityPlayer)trappedEntity).capabilities.isCreativeMode)
					{
						trappedEntity.attackEntityFrom(ModDamageSources.bearTrap, trappedEntity.getMaxHealth() / 20F);
					}

					PotionEffect effect = new PotionEffect(ModPotions.bleeding.id, 200, 1);
					effect.getCurativeItems().clear();
					trappedEntity.addPotionEffect(effect);
				}
				if(trappedEntity.isDead)
				{
					trappedEntity = null;
				}
			}
			if((getStackInSlot(0) != null) && (trappedEntity == null) && open)
			{
				List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(xCoord - 20, yCoord - 10, zCoord - 20, xCoord + 20, yCoord + 10, zCoord + 20));
				for(EntityLivingBase entity: entities)
				{
					if(entity instanceof EntityAnimal)
					{
						if(((EntityAnimal)entity).isBreedingItem(getStackInSlot(0)))
						{
							((EntityAnimal)entity).getMoveHelper().setMoveTo(xCoord + 0.5F, yCoord, zCoord + 0.5F, 1);
						}
					}
					if(entity instanceof EntityZombie)
					{
						if(getStackInSlot(0).itemID == Item.rottenFlesh.itemID)
						{
							((EntityZombie)entity).getMoveHelper().setMoveTo(xCoord + 0.5F, yCoord, zCoord + 0.5F, 1);
						}
						if(getStackInSlot(0).itemID == Item.beefRaw.itemID)
						{
							((EntityZombie)entity).getMoveHelper().setMoveTo(xCoord + 0.5F, yCoord, zCoord + 0.5F, 1);
						}
						if(getStackInSlot(0).itemID == Item.chickenRaw.itemID)
						{
							((EntityZombie)entity).getMoveHelper().setMoveTo(xCoord + 0.5F, yCoord, zCoord + 0.5F, 1);
						}
						if(getStackInSlot(0).itemID == Item.porkRaw.itemID)
						{
							((EntityZombie)entity).getMoveHelper().setMoveTo(xCoord + 0.5F, yCoord, zCoord + 0.5F, 1);
						}
						if(getStackInSlot(0).itemID == ModItems.venisonRaw.itemID)
						{
							((EntityZombie)entity).getMoveHelper().setMoveTo(xCoord + 0.5F, yCoord, zCoord + 0.5F, 1);
						}
						if(getStackInSlot(0).itemID == ModItems.hareRaw.itemID)
						{
							((EntityZombie)entity).getMoveHelper().setMoveTo(xCoord + 0.5F, yCoord, zCoord + 0.5F, 1);
						}
					}
					if(entity instanceof EntityCreeper)
					{
						if(getStackInSlot(0).itemID == Item.gunpowder.itemID)
						{
							((EntityCreeper)entity).getMoveHelper().setMoveTo(xCoord + 0.5F, yCoord, zCoord + 0.5F, 1);
						}
					}
					if(entity instanceof EntitySkeleton)
					{
						if(getStackInSlot(0).itemID == Item.bone.itemID)
						{
							((EntitySkeleton)entity).getMoveHelper().setMoveTo(xCoord + 0.5F, yCoord, zCoord + 0.5F, 1);
						}
					}
					if(entity instanceof EntityEnderman)
					{
						if(getStackInSlot(0).itemID == Item.enderPearl.itemID)
						{
							((EntityEnderman)entity).getMoveHelper().setMoveTo(xCoord + 0.5F, yCoord, zCoord + 0.5F, 1);
						}
					}
					if(entity instanceof EntitySpider)
					{
						if(getStackInSlot(0).itemID == Item.spiderEye.itemID)
						{
							((EntitySpider)entity).getMoveHelper().setMoveTo(xCoord + 0.5F, yCoord, zCoord + 0.5F, 1);
						}
					}
				}
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("cooldown", cooldown);
		tag.setBoolean("open", open);
		tag.setBoolean("captureFlag", trappedEntity != null);
	}
}
