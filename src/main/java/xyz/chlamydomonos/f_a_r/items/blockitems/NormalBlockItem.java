package xyz.chlamydomonos.f_a_r.items.blockitems;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import xyz.chlamydomonos.f_a_r.loaders.CreativeTabLoader;

public class NormalBlockItem extends BlockItem
{
    public NormalBlockItem(Block block)
    {
        super(block, new Properties().tab(CreativeTabLoader.FIRE_AND_ROT));
    }
}
