package xyz.chlamydomonos.f_a_r;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;
import xyz.chlamydomonos.f_a_r.loaders.ItemLoader;
import xyz.chlamydomonos.f_a_r.loaders.TileEntityLoader;

@Mod(FireAndRot.MODID)
public class FireAndRot
{
    public static final String MODID = "f_a_r";

    public FireAndRot()
    {
        BlockLoader.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityLoader.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemLoader.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
