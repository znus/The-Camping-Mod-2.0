package rikmuld.camping;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import rikmuld.api.core.loader.RikMuldAPIMod;
import rikmuld.api.core.loader.RikMuldAPIMod.RikMuldAPIPostStartupInitalation;
import rikmuld.api.core.loader.RikMuldAPIMod.RikMuldAPIPreStartupInitalation;
import rikmuld.api.core.register.MainRegistry;
import rikmuld.camping.core.handler.CraftHandler;
import rikmuld.camping.core.handler.EventsHandler;
import rikmuld.camping.core.handler.PlayerHandler;
import rikmuld.camping.core.lib.ModInfo;
import rikmuld.camping.core.proxys.CommonProxy;
import rikmuld.camping.core.register.ModAchievements;
import rikmuld.camping.core.register.ModBlocks;
import rikmuld.camping.core.register.ModConfig;
import rikmuld.camping.core.register.ModCookingEquipment;
import rikmuld.camping.core.register.ModDamageSources;
import rikmuld.camping.core.register.ModEntitys;
import rikmuld.camping.core.register.ModItems;
import rikmuld.camping.core.register.ModLogger;
import rikmuld.camping.core.register.ModModels;
import rikmuld.camping.core.register.ModPackets;
import rikmuld.camping.core.register.ModPotions;
import rikmuld.camping.core.register.ModRecipes;
import rikmuld.camping.core.register.ModStructures;
import rikmuld.camping.core.register.ModTileentitys;
import rikmuld.camping.misc.creativeTab.TabMain;
import rikmuld.camping.world.WorldGen;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@RikMuldAPIMod
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION, dependencies = ModInfo.MOD_DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class CampingMod {
	
	@Instance(ModInfo.MOD_ID)
	public static CampingMod instance;
	@SidedProxy(clientSide = ModInfo.MOD_CLIENT_PROXY, serverSide = ModInfo.MOD_SERVER_PROXY)
	public static CommonProxy proxy;
	
	@RikMuldAPIPreStartupInitalation
	public void librarySetup()
	{
		
	}
	
	@RikMuldAPIPostStartupInitalation
	public void registry()
	{
		ModTileentitys.init();
		ModPackets.init();
		MainRegistry.registerGlobalModCreativeTab(new TabMain(ModInfo.MOD_NAME), ModInfo.MOD_NAME);
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModLogger.init();
		ModConfig.init(new File(event.getModConfigurationDirectory().getAbsolutePath()+"/The Camping Mod/"+ModInfo.MOD_ID+".cfg"));
		ModBlocks.init();
		ModItems.init();
		ModAchievements.init();
		AchievementPage.registerAchievementPage(new AchievementPage("Camping Millestones", ModAchievements.getAll()));
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{		
		proxy.registerRenderers();
		proxy.registerTickHandler();
		proxy.checkVersion();
		
		if(event.getSide()==Side.CLIENT)ModModels.init();
		
		NetworkRegistry.instance().registerGuiHandler(this, CampingMod.proxy);
		GameRegistry.registerPlayerTracker(new PlayerHandler());
		MinecraftForge.EVENT_BUS.register(new EventsHandler());
		GameRegistry.registerCraftingHandler(new CraftHandler());
		GameRegistry.registerWorldGenerator(new WorldGen());
		
		ModEntitys.init();
		ModRecipes.init();
		ModCookingEquipment.init();
		ModStructures.init();
		ModDamageSources.init();
		ModPotions.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.registerGuide();
		ModLogger.log(Level.INFO, ModInfo.MOD_NAME+" is loaded successfully.");
	}
	
	/*
	
	BUGS
	{
		ON SERVER SIDE YOU SLEEP ABOVE SLEEPINGBAG/TENT -- STANDARD BED HEIGHT, ON CLIENT YOU FALL TO THE GOUND N SERVER NOT
		ON SERVER SIDE NO SPEED BONUS FUR ARMOR -- TICKHANDLER PROBLEM
		BERRY TREE LEAF DECAY NOT RIGHT -- HALF OF THE LEAVS WONT DISAPPEAR
		ENTITY NBT CRASH -- MAYBE?
	}
	UNFINISHED
	{		
		CAMPINGGUIDE -- ADD NEW STUFF UPDATE LANTERNS
	}
	NEXT UP
	{		
		BARBED WIRE
		ANTLER THROPHY_ANTLER
	}
	
	CAMPER -- INTELEGENCE	
	ANIMAL -- SOUNDS
						
	 */
}
