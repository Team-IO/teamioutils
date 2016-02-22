package net.teamio.teamioutils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class ClientOnlyProxy extends CommonProxy {
	
	public void preInit()
	{
		super.preInit();
	}
	
	public void init()
	{
		super.init();
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(TeamIOUtils.MOD_ID + ":" + TeamIOUtils.ITEM_HAMMER,"inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(TeamIOUtilsMain.itemHammer,DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
	}
	
	public void postInit()
	{
		super.postInit();
	}
	

	  @Override
	  public boolean playerIsInCreativeMode(EntityPlayer player) {
	    if (player instanceof EntityPlayerMP) {
	      EntityPlayerMP entityPlayerMP = (EntityPlayerMP)player;
	      return entityPlayerMP.theItemInWorldManager.isCreative();
	    } else if (player instanceof EntityPlayerSP) {
	      return Minecraft.getMinecraft().playerController.isInCreativeMode();
	    }
	    return false;
	  }

}
