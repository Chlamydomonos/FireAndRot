package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class RottenResidueBlock extends SandBlock
{
    public RottenResidueBlock()
    {
        super(
                0x303080,
                Properties.of(Material.DIRT)
                        .strength(0.5f)
                        .sound(SoundType.FUNGUS)
                        .randomTicks()
             );
    }
}
