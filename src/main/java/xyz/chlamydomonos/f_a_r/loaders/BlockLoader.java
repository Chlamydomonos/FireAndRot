package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.blocks.*;

@Mod.EventBusSubscriber(modid = FireAndRot.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockLoader
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FireAndRot.MODID);

    public static final RegistryObject<Block> ROTTEN_MYCELIAL_SOIL = BLOCKS.register("rotten_mycelial_soil", RottenMycelialSoilBlock::new);
    public static final RegistryObject<Block> ROTTEN_MYCELIAL_SOIL_WITHOUT_TE = BLOCKS.register("rotten_mycelial_soil_without_te", RottenMycelialSoilWithoutTEBlock::new);
    public static final RegistryObject<Block> ROTTEN_MYCELIAL_SOIL_HIDDEN = BLOCKS.register("rotten_mycelial_soil_hidden", RottenMycelialSoilHiddenBlock::new);

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ROTTEN_MYCELIAL_SOIL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ROTTEN_MYCELIAL_SOIL_HIDDEN.get(), RenderType.translucent());
        });
    }

    @SubscribeEvent
    public static void onColorHandler(ColorHandlerEvent.Block event)
    {
        event.getBlockColors().register(
                (a, b, c, d) -> b != null && c != null ?
                                BiomeColors.getAverageGrassColor(b, c) :
                                GrassColor.get(0.5D, 1.0D),
                ROTTEN_MYCELIAL_SOIL.get(),
                ROTTEN_MYCELIAL_SOIL_HIDDEN.get()
                                       );
    }
}
