package net.teamio.teamioutils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = TeamIOUtils.MOD_ID, version = TeamIOUtils.VERSION)
public class TeamIOUtilsMain
{

	@SidedProxy(clientSide="net.teamio.teamioutils.ClientProxy", serverSide="net.teamio.teamioutils.CommonProxy")
	public static CommonProxy proxy;
	
	public static Hammer itemHammer;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
		CreativeTabs creativeTab = new CreativeTabs(TeamIOUtils.MOD_ID) {

			@Override
			@SideOnly(Side.CLIENT)
			public ItemStack getIconItemStack() {
				return null; // new ItemStack(itemHammer);
			}
			
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return null;
			}
		};
		
		
		
		Hammer itemHammer = new Hammer();
		itemHammer.setCreativeTab(creativeTab);
		itemHammer.setUnlocalizedName(TeamIOUtils.ITEM_HAMMER);
		GameRegistry.registerItem(itemHammer, TeamIOUtils.ITEM_HAMMER);
	}
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
	
    }


    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    }    








}
