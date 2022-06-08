package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@SuppressWarnings("deprecation")
public class SmallRottenMushroomCapBlock extends Block
{
    public SmallRottenMushroomCapBlock()
    {
        super(
                Properties.of(Material.PLANT)
                        .strength(0.5f)
                        .noOcclusion()
                        .sound(SoundType.FUNGUS)
                        .randomTicks()
             );
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        super.randomTick(state, level, pos, random);
    }
}
