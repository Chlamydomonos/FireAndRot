package xyz.chlamydomonos.f_a_r.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chlamydomonos.f_a_r.FireAndRot;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;
import xyz.chlamydomonos.f_a_r.tags.FARBlockTags;

public class BlockTagProvider extends BlockTagsProvider
{
    public BlockTagProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(generator, FireAndRot.MODID, existingFileHelper);
    }


    @Override
    protected void addTags()
    {
        tag(FARBlockTags.ROTTEN_PHASE_1).add(
                BlockLoader.ROTTEN_MYCELIAL_SOIL.get(),
                BlockLoader.ROTTEN_MYCELIAL_SOIL_WITHOUT_TE.get(),
                BlockLoader.ROTTEN_STIPE.get(),
                BlockLoader.SMALL_ROTTEN_MUSHROOM_CAP.get(),
                BlockLoader.ROTTEN_MUSHROOM_CAP_CENTER.get(),
                BlockLoader.ROTTEN_MUSHROOM_CAP_CORNER.get(),
                BlockLoader.ROTTEN_MUSHROOM_CAP_SIDE.get()
                                            );

        tag(FARBlockTags.ROTTEN_PHASE_2).add(
                BlockLoader.WITHERED_ROTTEN_MUSHROOM_CAP.get(),
                BlockLoader.ROTTEN_RESIDUE.get(),
                BlockLoader.ROTTEN_LICHENS.get(),
                BlockLoader.ROTTEN_MILDEW.get()
                                            );

        tag(FARBlockTags.ROTTEN_PHASE_3).add(
                BlockLoader.ROTTEN_SOIL.get()
                                            );
    }

    @Override
    public @NotNull String getName()
    {
        return "Fire and Rot Blocks";
    }
}
