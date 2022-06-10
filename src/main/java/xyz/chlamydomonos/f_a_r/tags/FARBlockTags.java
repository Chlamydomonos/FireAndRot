package xyz.chlamydomonos.f_a_r.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import xyz.chlamydomonos.f_a_r.FireAndRot;

public class FARBlockTags
{
    public static final TagKey<Block> ROTTEN_PHASE_1 = tag("rotten/phase1");
    public static final TagKey<Block> ROTTEN_PHASE_2 = tag("rotten/phase2");

    private static TagKey<Block> tag(String name)
    {
        return BlockTags.create(new ResourceLocation(FireAndRot.MODID, name));
    }
}
