package rikmuld.camping.core.register;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import rikmuld.camping.core.util.ItemStackUtil;
import rikmuld.camping.core.util.RecipeUtil;
import rikmuld.camping.item.ItemAnimalStuff;
import rikmuld.camping.item.ItemParts;
import rikmuld.camping.item.itemblock.ItemBlockLantern;

public class ModRecipes {

	static ItemStack[] dye = ItemStackUtil.getMetaCycle(Item.dyePowder, 16);
	static ItemStack[] parts = ItemStackUtil.getMetaCycle(ModItems.parts, ItemParts.metadataNames.length);
	static ItemStack[] lantern = ItemStackUtil.getMetaCycle(ModBlocks.lantern, ItemBlockLantern.metadataNames.length);
	static ItemStack[] stuffAnimal = ItemStackUtil.getMetaCycle(ModItems.animalStuff, ItemAnimalStuff.metadataNames.length);

	public static void init()
	{
		RecipeUtil.addRecipe(ModBlocks.campfireDeco, 1, " 0 ", "010", "020", '0', Item.stick, '1', Item.flint, '2', ModBlocks.campfireBase);
		RecipeUtil.addRecipe(ModBlocks.campfireBase, 1, " 0 ", "0 0", " 0 ", '0', Block.cobblestone);
		RecipeUtil.addRecipe(ModItems.knife, 1, "101", "101", "101", '1', dye[1], '0', Item.ingotIron);
		RecipeUtil.addRecipe(lantern[ItemBlockLantern.LANTERN_ON], 1, "000", "010", "222", '1', Item.glowstone, '0', Block.thinGlass, '2', Item.ingotGold);
		RecipeUtil.addRecipe(lantern[ItemBlockLantern.LANTERN_OFF], 1, "000", "0 0", "111", '1', Item.ingotGold, '0', Block.thinGlass);
		RecipeUtil.addRecipe(parts[ItemParts.STICK], 1, " 0 ", " 0 ", '0', Item.ingotIron);
		RecipeUtil.addRecipe(ModItems.backpack, 1, "000", "0 0", "000", '0', parts[ItemParts.CANVAS]);
		RecipeUtil.addRecipe(parts[ItemParts.PAN], 1, " 0 ", "121", " 1 ", '0', dye[1], '1', Item.ingotIron, '2', Item.bowlEmpty);
		RecipeUtil.addRecipe(parts[ItemParts.MARSHMALLOW], 3, "010", "020", "030", '0', Item.sugar, '1', Item.potion, '2', Item.egg, '3', Item.bowlEmpty);
		RecipeUtil.addRecipe(ModItems.armorFurHelmet, 1, "000", "010", '0', stuffAnimal[ItemAnimalStuff.FUR_WHITE], '1', Item.helmetLeather);
		RecipeUtil.addRecipe(ModItems.armorFurChest, 1, "0 0", "010", "222", '0', stuffAnimal[ItemAnimalStuff.FUR_WHITE], '1', Item.plateLeather, '2', stuffAnimal[ItemAnimalStuff.FUR_BROWN]);
		RecipeUtil.addRecipe(ModItems.armorFurLeg, 1, "000", "010", "0 0", '0', stuffAnimal[ItemAnimalStuff.FUR_BROWN], '1', Item.legsLeather);
		RecipeUtil.addRecipe(ModItems.armorFurBoots, 1, "0 0", "010", '0', stuffAnimal[ItemAnimalStuff.FUR_BROWN], '1', Item.bootsLeather);
		RecipeUtil.addRecipe(ModItems.kit, 1, "000", "111", "000", '0', Item.ingotIron, '1', dye[11]);
		RecipeUtil.addRecipe(ModBlocks.tent, 1, "000", "0 0", "1 1", '0', parts[ItemParts.CANVAS], '1', parts[ItemParts.STICK]);
		RecipeUtil.addRecipe(ModBlocks.sleepingbag, 1, "1  ", "000", '0', ItemStackUtil.getWildValue(new ItemStack(Block.cloth, 1, 0)), '1', ItemStackUtil.getWildValue(new ItemStack(ModItems.knife)));
		RecipeUtil.addRecipe(ModBlocks.sleepingbag, 1, " 1 ", "000", '0', ItemStackUtil.getWildValue(new ItemStack(Block.cloth, 1, 0)), '1', ItemStackUtil.getWildValue(new ItemStack(ModItems.knife)));
		RecipeUtil.addRecipe(ModBlocks.sleepingbag, 1, "  1", "000", '0', ItemStackUtil.getWildValue(new ItemStack(Block.cloth, 1, 0)), '1', ItemStackUtil.getWildValue(new ItemStack(ModItems.knife)));
		RecipeUtil.addRecipe(ModBlocks.bearTrap, 1, " 1 ", "101", " 1 ", '0', Item.ingotIron, '1', parts[ItemParts.STICK]);
		RecipeUtil.addRecipe(ModBlocks.throphy, 1, "0 0", " 1 ", '0', stuffAnimal[ItemAnimalStuff.ANTLER], '1', Item.itemFrame);

		RecipeUtil.addShapelessRecipe(lantern[ItemBlockLantern.LANTERN_ON], 1, lantern[ItemBlockLantern.LANTERN_OFF], Item.glowstone);
		RecipeUtil.addShapelessRecipe(parts[ItemParts.CANVAS], 1, ModItems.hemp, ItemStackUtil.getWildValue(new ItemStack(ModItems.knife)));
		RecipeUtil.addShapelessRecipe(lantern[ItemBlockLantern.LANTERN_ON], 1, lantern[ItemBlockLantern.LANTERN_ON], Item.glowstone);
		RecipeUtil.addShapelessRecipe(parts[ItemParts.MARSHMALLOW_STICK], 3, parts[ItemParts.MARSHMALLOW], parts[ItemParts.STICK], parts[ItemParts.STICK], parts[ItemParts.STICK]);
		RecipeUtil.addShapelessRecipe(ModBlocks.tent, 1, ModBlocks.tent, ItemStackUtil.getWildValue(new ItemStack(Item.dyePowder)));
		RecipeUtil.addShapelessRecipe(ModBlocks.log, 1, ItemStackUtil.getWildValue(new ItemStack(Block.wood, 1, 0)), ItemStackUtil.getWildValue(new ItemStack(ModItems.knife)));
		RecipeUtil.addShapelessRecipe(ModBlocks.wireBarbed, 4, Item.silk, Item.ingotIron);

		RecipeUtil.addSmeltingRecipe(ModItems.venisonCooked, 1, 3, ModItems.venisonRaw.itemID, 0);
		RecipeUtil.addSmeltingRecipe(ModItems.hareCooked, 1, 3, ModItems.hareRaw.itemID, 0);
	}
}
