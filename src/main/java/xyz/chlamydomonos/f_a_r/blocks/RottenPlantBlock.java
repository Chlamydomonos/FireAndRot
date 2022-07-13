package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import java.util.ArrayList;
import java.util.List;

public class RottenPlantBlock extends FlowerBlock
{
    public static final List<RottenPlantBlock> ROTTEN_PLANT_BLOCKS = new ArrayList<>();

    public RottenPlantBlock()
    {
        super(
                MobEffects.POISON,
                100000,
                Properties.of(Material.PLANT)
                        .instabreak()
                        .strength(0.1f)
                        .noCollission()
                        .noOcclusion()
                        .randomTicks()
                        .dynamicShape()
                        .sound(SoundType.GRASS)
             );

        ROTTEN_PLANT_BLOCKS.add(this);
    }
}
