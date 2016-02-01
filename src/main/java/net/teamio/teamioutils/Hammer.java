package net.teamio.teamioutils;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class Hammer extends ItemTool {

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.dirt,Blocks.grass, Blocks.gravel, Blocks.coal_ore, Blocks.cobblestone, Blocks.diamond_block, Blocks.diamond_ore, Blocks.double_stone_slab, Blocks.gold_block, Blocks.gold_ore, Blocks.ice, Blocks.iron_block, Blocks.iron_ore, Blocks.lapis_block, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.mossy_cobblestone, Blocks.netherrack, Blocks.redstone_ore, Blocks.sandstone, Blocks.red_sandstone, Blocks.stone, Blocks.stone_slab, Blocks.nether_brick, Blocks.nether_brick_fence, Blocks.nether_brick_stairs});
	private final float efficiencyOnProperMaterial = Float.MAX_VALUE;
	
	public Hammer(){
		super(2f, Item.ToolMaterial.EMERALD, EFFECTIVE_ON);
		this.setMaxStackSize(1);
		this.setMaxDamage(Integer.MAX_VALUE);
		this.setNoRepair();
		this.setHarvestLevel("Diamond", Integer.MAX_VALUE);
		this.isDamageable();
		}
	
	@Override
	public boolean canHarvestBlock(Block block)
	{
		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block)
    {
        return block.hasTileEntity(block.getDefaultState()) ? 0F : this.efficiencyOnProperMaterial;
    }
}
