package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.blocks.utils.FARProperties;
import xyz.chlamydomonos.f_a_r.blocks.utils.RottenStipeUtil;

import java.util.Random;

public class RottenStipeBlock extends PipeBlock
{
    public RottenStipeBlock()
    {
        super(
                0.5f,
                Properties.of(Material.PLANT)
                        .color(MaterialColor.COLOR_BLUE)
                        .strength(0.5f)
                        .sound(SoundType.FUNGUS)
                        .randomTicks()
                        .noOcclusion()
             );

        registerDefaultState(getStateDefinition().any()
                                     .setValue(FARProperties.AGE, 0)
                                     .setValue(FARProperties.HEIGHT, 0)
                                     .setValue(NORTH, false)
                                     .setValue(SOUTH, false)
                                     .setValue(EAST, false)
                                     .setValue(WEST, false)
                                     .setValue(UP, false)
                                     .setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder)
    {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN, FARProperties.AGE, FARProperties.HEIGHT);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos)
    {
        if(state.getValue(FARProperties.HEIGHT) == 0 && direction == Direction.DOWN)
            return state.setValue(PROPERTY_BY_DIRECTION.get(direction), !neighborState.isAir());

        boolean flag = neighborState.getBlock() == this;
        return state.setValue(PROPERTY_BY_DIRECTION.get(direction), flag);
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        RottenStipeUtil.grow(state, level, pos, random);
    }
}
