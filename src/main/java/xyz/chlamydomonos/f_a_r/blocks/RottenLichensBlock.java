package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.blocks.utils.FARProperties;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;

import java.util.Random;

@SuppressWarnings("deprecation")
public class RottenLichensBlock extends Block
{
    public RottenLichensBlock()
    {
        super(
                Properties.of(Material.DIRT)
                        .randomTicks()
                        .sound(SoundType.FUNGUS)
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
        int age = state.getValue(FARProperties.AGE);
        if(age < 4 && random.nextInt(5) == 0)
            level.setBlock(pos, state.cycle(FARProperties.AGE), 3);

        if(age == 4 && random.nextInt(10) == 0)
            level.setBlock(pos, BlockLoader.ROTTEN_SOIL.get().defaultBlockState(), 3);
    }
}
