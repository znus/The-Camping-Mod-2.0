package rikmuld.camping.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemParts extends ItemMain {

	public static final int CANVAS = 0;
	public static final int STICK = 1;
	public static final int PAN = 2;
	public static final int ASH = 3;
	public static final int MARSHMALLOW = 4;
	public static final int MARSHMALLOW_STICK = 5;

	public static final String[] metadataIGNames = new String[]{"Canvas", "Iron Stick", "Pan", "Ash", "Marshmallows", "Marshmallow on a Stick"};
	public static final String[] metadataNames = new String[]{"canvas", "stickIron", "pan", "ash", "marshmallows", "marshmallowStickRaw"};

	public ItemParts(String name)
	{
		super(name, metadataIGNames, metadataNames, true);
		setHasSubtypes(true);
	}

	@Override
	public MovingObjectPosition getMovingObjectPositionFromPlayer(World world, EntityPlayer player, boolean par3)
	{
		float f = 1.0F;
		float f1 = player.prevRotationPitch + ((player.rotationPitch - player.prevRotationPitch) * f);
		float f2 = player.prevRotationYaw + ((player.rotationYaw - player.prevRotationYaw) * f);
		double d0 = player.prevPosX + ((player.posX - player.prevPosX) * f);
		double d1 = player.prevPosY + ((player.posY - player.prevPosY) * f) + (world.isRemote? player.getEyeHeight() - player.getDefaultEyeHeight():player.getEyeHeight());
		double d2 = player.prevPosZ + ((player.posZ - player.prevPosZ) * f);
		Vec3 vec3 = world.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
		float f3 = MathHelper.cos((-f2 * 0.017453292F) - (float)Math.PI);
		float f4 = MathHelper.sin((-f2 * 0.017453292F) - (float)Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = 5.0D;
		if(player instanceof EntityPlayerMP)
		{
			d3 = ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance();
		}
		Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
		return world.rayTraceBlocks_do_do(vec3, vec31, par3, !par3);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return metadataNames[itemstack.getItemDamage()];
	}
}
