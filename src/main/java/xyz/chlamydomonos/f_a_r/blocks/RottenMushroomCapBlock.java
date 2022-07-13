package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.blocks.utils.RottenMushroomCapUtil;

import java.util.Random;

public class RottenMushroomCapBlock extends HorizontalDirectionalBlock
{
    public RottenMushroomCapBlock()
    {
        super(
                Properties.of(Material.PLANT)
                      .noOcclusion()
                      .strength(0.5f)
                      .sound(SoundType.FUNGUS)
             );
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Random random)
    {
        RottenMushroomCapUtil.genSporeParticles(state, level, pos, random);
    }
}
