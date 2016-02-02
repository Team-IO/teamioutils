package net.teamio.teamioutils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ClientProxy extends CommonProxy {
	@Override
	public void initClientOnly()
	{
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(TeamIOUtils.MOD_ID + ":" + TeamIOUtils.ITEM_HAMMER,"inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(TeamIOUtilsMain.itemHammer,DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
	}

}
