package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeTabLoader
{
    public static final CreativeModeTab FIRE_AND_ROT = new CreativeModeTab("fire_and_rot")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ItemLoader.FIRE_AND_ROT_ICON.get());
        }
    };
}
