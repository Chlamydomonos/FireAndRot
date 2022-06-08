package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


@SuppressWarnings("deprecation")
public class WitheredRottenMushroomCapBlock extends Block
{
    public WitheredRottenMushroomCapBlock()
    {
        super(
                Properties.of(Material.WOOD)
                        .randomTicks()
                        .strength(0.5f)
             );
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        super.randomTick(state, level, pos, random);
    }
}
