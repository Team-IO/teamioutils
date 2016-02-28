package net.teamio.teamioutils;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Hammer extends ItemTool {

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.dirt,Blocks.grass, Blocks.gravel, Blocks.coal_ore, Blocks.cobblestone, Blocks.diamond_block, Blocks.diamond_ore, Blocks.double_stone_slab, Blocks.gold_block, Blocks.gold_ore, Blocks.ice, Blocks.iron_block, Blocks.iron_ore, Blocks.lapis_block, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.mossy_cobblestone, Blocks.netherrack, Blocks.redstone_ore, Blocks.sandstone, Blocks.red_sandstone, Blocks.stone, Blocks.stone_slab, Blocks.nether_brick, Blocks.nether_brick_fence, Blocks.nether_brick_stairs});
	private final float efficiencyOnProperMaterial = Float.MAX_VALUE;
	private boolean bigTime = false;
	
	public Hammer(){
		super(2f, Item.ToolMaterial.EMERALD, EFFECTIVE_ON);
		this.setMaxStackSize(1);
		this.setMaxDamage(Integer.MAX_VALUE);
		this.setNoRepair();
		this.setHarvestLevel("Diamond", Integer.MAX_VALUE);
		this.isDamageable();
		}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		if(playerIn.isSneaking())
		{
			updateGhostBlocks(playerIn, worldIn);
		}else{
			if(worldIn.isRemote)
			{
			if(bigTime)
			{
				playerIn.addChatComponentMessage(new ChatComponentTranslation("msg.big_time.true.txt"));
				bigTime = false;
				}else{
				playerIn.addChatComponentMessage(new ChatComponentTranslation("msg.big_time.false.txt"));
				bigTime = true;
				}
			}
		}
		return itemStackIn;
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn)	
	{
		if (bigTime)
		{
		EntityPlayer player = (EntityPlayer) playerIn;
		return breakAOEBlocks(stack, pos,1, 0, player);
		}else{
			return true;
		}
			
	}
	@Override
	public boolean canHarvestBlock(Block block)
	{
		return block.getBlockState().getBlock().hasTileEntity(block.getBlockState().getBaseState())? false: true;
	}

	public boolean breakAOEBlocks(ItemStack stack, BlockPos pos, int breakRadius, int breakDepth, EntityPlayer player)
	{
		//Map<Block, Integer> blockMap =  new HashMap<Block, Integer>();
		//Block block = player.worldObj.getBlockState(pos).getBlock();
		//IBlockState meta = player.worldObj.getBlockState(pos);
		if(player.getEntityWorld().isRemote)
		{
			return false;
		}
		MovingObjectPosition mop = raytraceFromEntity(player.worldObj, player, 4.5d);
		if(mop == null)
		{
			updateGhostBlocks(player, player.worldObj);
			return true;
		}
		int sideHit = mop.sideHit.getIndex();

		int xMax = 	breakRadius;
		int xMin = 	breakRadius;
		int yMax = 	breakRadius;
		int yMin = 	breakRadius;
		int zMax = 	breakRadius;
		int zMin = 	breakRadius;
		int yOffset = 0;

		switch (sideHit) {
			case 0:
				yMax = breakDepth;
				yMin = 0;
				zMax = breakRadius;
				break;
			case 1:
				yMin = breakDepth;
				yMax = 0;
				zMax = breakRadius;
				break;
			case 2:
				xMax = breakRadius;
				zMin = 0;
				zMax = breakDepth;
				yOffset = breakRadius - 1;
				break;
			case 3:
				xMax = breakRadius;
				zMax = 0;
				zMin = breakDepth;
				yOffset = breakRadius - 1;
				break;
			case 4:
				xMax = breakDepth;
				xMin = 0;
				zMax = breakRadius;
				yOffset = breakRadius - 1;
				break;
			case 5:
				xMin = breakDepth;
				xMax = 0;
				zMax = breakRadius;
				yOffset = breakRadius - 1;
				break;
		}

		for (int xPos = pos.getX() - xMin; xPos <= pos.getX() + xMax; xPos++)
		{
			for (int yPos = pos.getY() + yOffset - yMin; yPos <= pos.getY() + yOffset + yMax; yPos++)
			{
				for (int zPos = pos.getZ() - zMin; zPos <= pos.getZ() + zMax; zPos++)
				{
					BlockPos currentBlock = new BlockPos(xPos, yPos, zPos);
					if (player.worldObj.getBlockState(currentBlock).getBlock().hasTileEntity(player.worldObj.getBlockState(currentBlock))) {
						if (player.worldObj.isRemote) {
							player.addChatComponentMessage(new ChatComponentTranslation("msg.baseSafeAOW.txt"));
						}
						else ((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(player.worldObj,currentBlock));
						return true;
					}
				}
			}
		}


		for (int xPos = pos.getX() - xMin; xPos <= pos.getX() + xMax; xPos++)
		{
			for (int yPos = pos.getY() + yOffset - yMin; yPos <= pos.getY() + yOffset + yMax; yPos++)
			{
				for (int zPos = pos.getZ() - zMin; zPos <= pos.getZ() + zMax; zPos++)
				{
					BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
					player.getEntityWorld().destroyBlock(blockPos, true);
				}
			}
		}

		return true;
	}
	
	public static MovingObjectPosition raytraceFromEntity(World world, Entity player, double range) 
	{
		float f = 1.0F;
		float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f;
		if (!world.isRemote && player instanceof EntityPlayer) d1 += 1.62D;
		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
		Vec3 vec3 = new Vec3(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = range;
		if (player instanceof EntityPlayerMP && range < 10) {
			d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
		}
		Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
		return world.rayTraceBlocks(vec3, vec31);
	}

	public static void updateGhostBlocks(EntityPlayer player, World world) {
		if (world.isRemote) return;
		int xPos = (int) player.posX;
		int yPos = (int) player.posY;
		int zPos = (int) player.posZ;

		for (int x = xPos - 6; x < xPos + 6; x++) {
			for (int y = yPos - 6; y < yPos + 6; y++) {
				for (int z = zPos - 6; z < zPos + 6; z++) {
					((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(world, new BlockPos(x, y, z)));
					//world.markBlockForUpdate(x, y, z);
				}
			}
		}
	}
	
//	protected void breakExtraBlock(ItemStack stack, World world, int x, int y, int z, int totalSize, EntityPlayer player, float refStrength, boolean breakSound, Map<Block, Integer> blockMap)
//	{
//		BlockPos pos = new BlockPos(x,y,z);
//		if (world.getBlockState(pos).getBlock().isAir(world, pos)) return;
//
//		Block block = world.getBlockState(pos).getBlock();
//		if (block.getMaterial() instanceof MaterialLiquid || (block.getBlockState().getBlock().getBlockHardness(world, pos)== -1 && !player.capabilities.isCreativeMode)) return;
//
//		if (!world.isRemote) {
//
//			block.onBlockHarvested(world, pos, block., player);
//
//			if(block.removedByPlayer(world, player, x,y,z, true))
//			{
//				block.onBlockDestroyedByPlayer(world, x,y,z, meta);
//				block.harvestBlock(world, player, x,y,z, meta);
//				player.addExhaustion(-0.025F);
//				if (block.getExpDrop(world, meta, EnchantmentHelper.getFortuneModifier(player)) > 0) player.addExperience(block.getExpDrop(world, meta, EnchantmentHelper.getFortuneModifier(player)));
//			}
//
//			EntityPlayerMP mpPlayer = (EntityPlayerMP) player;
//			mpPlayer.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
//		}
//		else
//		{
//			if (breakSound) world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
//			if(block.removedByPlayer(world, player, x,y,z, true))
//			{
//				block.onBlockDestroyedByPlayer(world, x,y,z, meta);
//			}
//
//			Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(, pos, Minecraft.getMinecraft().objectMouseOver.sideHit));
//		}
//	}
	@Override
	public float getStrVsBlock(ItemStack stack, Block block)
    {
        return block.hasTileEntity(block.getDefaultState()) ? 0F : this.efficiencyOnProperMaterial;
    }
}
