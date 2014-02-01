package rikmuld.camping.core.register;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import rikmuld.camping.core.lib.BlockInfo;
import rikmuld.camping.core.lib.ConfigInfo;
import rikmuld.camping.core.lib.ConfigInfo.ConfigInfoInteger;
import rikmuld.camping.core.lib.ItemInfo;
import rikmuld.camping.item.ItemAnimalStuff;
import rikmuld.camping.item.ItemBackpack;
import rikmuld.camping.item.ItemHemp;
import rikmuld.camping.item.ItemKit;
import rikmuld.camping.item.ItemKnife;
import rikmuld.camping.item.ItemParts;
import rikmuld.camping.item.armor.ItemArmorFur;
import rikmuld.camping.item.food.ItemFoodBerry;
import rikmuld.camping.item.food.ItemFoodMain;
import rikmuld.camping.item.food.ItemFoodMarshmallow;
import rikmuld.camping.item.food.ItemFoodStew;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModItems {

	public static Item knife;
	public static Item backpack;
	public static Item kit;
	public static Item parts;
	public static Item hemp;
	public static Item marshmallowCooked;
	public static Item berries;
	public static Item animalStuff;
	public static Item venisonRaw;
	public static Item venisonCooked;
	public static Item hareRaw;
	public static Item hareCooked;
	public static Item armorFurHelmet;
	public static Item armorFurChest;
	public static Item armorFurLeg;
	public static Item armorFurBoots;
	public static Item stew;

	public static void init()
	{
		knife = new ItemKnife(ItemInfo.KNIFE);
		backpack = new ItemBackpack(ItemInfo.BACKPACK);
		kit = new ItemKit(ItemInfo.KIT);
		hemp = new ItemHemp(ItemInfo.HEMP);
		parts = new ItemParts(ItemInfo.PARTS);
		marshmallowCooked = new ItemFoodMarshmallow(ItemInfo.MARSHSTICK_COOKED);
		berries = new ItemFoodBerry(ItemInfo.BERRIES);
		stew = new ItemFoodStew(ItemInfo.STEW);
		animalStuff = new ItemAnimalStuff(ItemInfo.ANIMAL_STUFF);
		venisonRaw = new ItemFoodMain(ItemInfo.VANISON_RAW, ConfigInfoInteger.value(ConfigInfo.HEAL_VANISON_RAW), 1.8F, true);
		venisonCooked = new ItemFoodMain(ItemInfo.VANISON_COOKED, ConfigInfoInteger.value(ConfigInfo.HEAL_VANISON_COOKED), 12.8F, true);
		hareRaw = new ItemFoodMain(ItemInfo.HARE_RAW, ConfigInfoInteger.value(ConfigInfo.HEAL_HARE_RAW), 1.2F, false);
		hareCooked = new ItemFoodMain(ItemInfo.HARE_COOKED, ConfigInfoInteger.value(ConfigInfo.HEAL_HARE_COOKED), 7.2F, false);
		armorFurHelmet = new ItemArmorFur(ItemInfo.ARMOR_FUR_HELM, 0);
		armorFurChest = new ItemArmorFur(ItemInfo.ARMOR_FUR_CHEST, 1);
		armorFurLeg = new ItemArmorFur(ItemInfo.ARMOR_FUR_LEG, 2);
		armorFurBoots = new ItemArmorFur(ItemInfo.ARMOR_FUR_BOOTS, 3);
	}

	public static void register(Item item, String name)
	{
		GameRegistry.registerItem(item, name);
		LanguageRegistry.addName(item, ItemInfo.name(name));
	}

	public static void registerItemBlock(ItemBlock item, String name)
	{
		LanguageRegistry.addName(item, BlockInfo.name(name));
	}

	public static void registerItemBlockWithMeta(ItemBlock item, String[] names)
	{
		for(int i = 0; i < names.length; i++)
		{
			LanguageRegistry.addName(new ItemStack(item.itemID, 1, i), names[i]);
		}
	}

	public static void registerWithMeta(Item item, String name, String[] names)
	{
		GameRegistry.registerItem(item, name);
		for(int i = 0; i < names.length; i++)
		{
			LanguageRegistry.addName(new ItemStack(item.itemID, 1, i), names[i]);
		}
	}
}
