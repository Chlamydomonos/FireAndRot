package xyz.chlamydomonos.f_a_r.items.blockitems;

import net.minecraft.world.item.BlockItem;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;
import xyz.chlamydomonos.f_a_r.loaders.CreativeTabLoader;

public class RottenSoilItem extends BlockItem
{
    public RottenSoilItem()
    {
        super(
                BlockLoader.ROTTEN_SOIL.get(),
                new Properties().tab(CreativeTabLoader.FIRE_AND_ROT)
             );
    }
}
