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

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = FireAndRot.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockLoader
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FireAndRot.MODID);

    public static final RegistryObject<Block> ROTTEN_MYCELIAL_SOIL = BLOCKS.register("rotten_mycelial_soil", RottenMycelialSoilBlock::new);
    public static final RegistryObject<Block> ROTTEN_MYCELIAL_SOIL_WITHOUT_TE = BLOCKS.register("rotten_mycelial_soil_without_te", RottenMycelialSoilWithoutTEBlock::new);
    public static final RegistryObject<Block> ROTTEN_MYCELIAL_SOIL_HIDDEN = BLOCKS.register("rotten_mycelial_soil_hidden", RottenMycelialSoilHiddenBlock::new);
    public static final RegistryObject<Block> ROTTEN_STIPE = BLOCKS.register("rotten_stipe", RottenStipeBlock::new);
    public static final RegistryObject<Block> SMALL_ROTTEN_MUSHROOM_CAP = BLOCKS.register("small_rotten_mushroom_cap", SmallRottenMushroomCapBlock::new);
    public static final RegistryObject<Block> ROTTEN_MUSHROOM_CAP_CENTER = BLOCKS.register("rotten_mushroom_cap_center", RottenMushroomCapCenterBlock::new);
    public static final RegistryObject<Block> ROTTEN_MUSHROOM_CAP_SIDE = BLOCKS.register("rotten_mushroom_cap_side", RottenMushroomCapBlock::new);
    public static final RegistryObject<Block> ROTTEN_MUSHROOM_CAP_CORNER = BLOCKS.register("rotten_mushroom_cap_corner", RottenMushroomCapBlock::new);
    public static final RegistryObject<Block> WITHERED_ROTTEN_MUSHROOM_CAP = BLOCKS.register("withered_rotten_mushroom_cap", WitheredRottenMushroomCapBlock::new);
    public static final RegistryObject<Block> ROTTEN_RESIDUE = BLOCKS.register("rotten_residue", RottenResidueBlock::new);
    public static final RegistryObject<Block> ALIEN_EXPLOSIVE = BLOCKS.register("alien_explosive", AlienExplosiveBlock::new);
    public static final RegistryObject<Block> ROTTEN_LICHENS = BLOCKS.register("rotten_lichens", RottenLichensBlock::new);
    public static final RegistryObject<Block> ROTTEN_MILDEW = BLOCKS.register("rotten_mildew", RottenMildewBlock::new);
    public static final RegistryObject<Block> ROTTEN_SOIL = BLOCKS.register("rotten_soil", RottenSoilBlock::new);
    public static final RegistryObject<Block> POISON_FLOWER = BLOCKS.register("poison_flower", RottenPlantBlock::new);
    public static final RegistryObject<Block> PALE_MUSHROOM = BLOCKS.register("pale_mushroom", RottenPlantBlock::new);


    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ROTTEN_MYCELIAL_SOIL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ROTTEN_MYCELIAL_SOIL_HIDDEN.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ROTTEN_STIPE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(SMALL_ROTTEN_MUSHROOM_CAP.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ROTTEN_MILDEW.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(POISON_FLOWER.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(PALE_MUSHROOM.get(), RenderType.cutoutMipped());
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
