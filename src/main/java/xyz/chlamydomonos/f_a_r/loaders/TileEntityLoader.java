package xyz.chlamydomonos.f_a_r.loaders;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.tileentities.RottenMycelialSoilTileEntity;

public class TileEntityLoader
{
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, FireAndRot.MODID);

    public static final RegistryObject<BlockEntityType<?>> ROTTEN_MYCELIAL_SOIL = TILE_ENTITIES.register("rotten_mycelial_soil", () -> BlockEntityType.Builder.of(RottenMycelialSoilTileEntity::new, BlockLoader.ROTTEN_MYCELIAL_SOIL.get()).build(null));
}
