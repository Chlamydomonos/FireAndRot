package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.blocks.utils.FARProperties;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;

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

        registerDefaultState(getStateDefinition().any().setValue(FARProperties.AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder)
    {
        builder.add(FARProperties.AGE);
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        if(random.nextInt(10 ) != 0)
            return;

        if(state.getValue(FARProperties.AGE) < 4)
        {
            level.setBlock(pos, state.cycle(FARProperties.AGE), 3);
            return;
        }

        if(level.getBlockState(pos.below()).isAir())
            level.setBlock(pos.below(), BlockLoader.ROTTEN_RESIDUE.get().defaultBlockState(), 3);
        level.setBlock(pos, BlockLoader.ROTTEN_RESIDUE.get().defaultBlockState(), 3);
    }
}
