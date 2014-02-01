package rikmuld.camping.item.food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import rikmuld.camping.core.lib.ConfigInfo;
import rikmuld.camping.core.lib.ConfigInfo.ConfigInfoInteger;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFoodBerry extends ItemFoodMain {

	public static final int BERRY_BLACK = 0;
	public static final int BERRY_RED = 1;

	public static final String[] metadataIGNames = new String[]{"Blackberry", "Raspberry"};
	public static final String[] metadataNames = new String[]{"blackberry", "raspberry"};

	public ItemFoodBerry(String name)
	{
		super(name, metadataIGNames, metadataNames, true, ConfigInfoInteger.value(ConfigInfo.HEAL_BERRY), 0.4F, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativetabs, List list)
	{
		for(int meta = 0; meta < metadataNames.length; ++meta)
		{
			list.add(new ItemStack(id, 1, meta));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return metadataNames[itemstack.getItemDamage()];
	}
}
