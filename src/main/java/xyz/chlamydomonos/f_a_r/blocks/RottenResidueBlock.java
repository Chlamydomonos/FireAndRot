package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class RottenResidueBlock extends Block
{
    public RottenResidueBlock()
    {
        super(
                Properties.of(Material.DIRT)
                        .strength(0.5f)
                        .sound(SoundType.FUNGUS)
                        .randomTicks()
             );
    }
}
