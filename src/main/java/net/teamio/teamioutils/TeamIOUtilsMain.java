package net.teamio.teamioutils;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = TeamIOUtils.MOD_ID, version = TeamIOUtils.VERSION)
public class TeamIOUtilsMain
{

	@SidedProxy(clientSide="net.teamio.teamioutils.ClientOnlyProxy", serverSide="net.teamio.teamioutils.CommonProxy")
	public static CommonProxy proxy;
	
	public static Hammer itemHammer;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit();
		
		itemHammer = (Hammer)(new Hammer().setUnlocalizedName(TeamIOUtils.ITEM_HAMMER));
		GameRegistry.registerItem(itemHammer, TeamIOUtils.ITEM_HAMMER);
	}
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init();
    }


    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	proxy.postInit();	
    }    

    public static String prependModID(String name) {return TeamIOUtils.MOD_ID + ":" + name;}
}
