package net.teamio.teamioutils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = TeamIOUtils.MOD_ID, version = TeamIOUtils.VERSION)
public class TeamIOUtilsMain
{

	@SidedProxy(clientSide="net.teamio.teamioutils.ClientOnlyProxy", serverSide="net.teamio.teamioutils.DedicatedServerProxy")
	public static CommonProxy proxy;
	
	public static Hammer itemHammer;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit();
		
		itemHammer = (Hammer)(new Hammer().setUnlocalizedName(TeamIOUtils.ITEM_HAMMER));
		GameRegistry.registerItem(itemHammer, TeamIOUtils.ITEM_HAMMER);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemHammer, 1, 0),
				"DID", "DSD", "ESE",
				'D', Blocks.diamond_block,
				'I', Blocks.iron_block,
				'S', Items.stick));
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
