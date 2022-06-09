package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.blocks.utils.FARProperties;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;

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

        registerDefaultState(getStateDefinition().any().setValue(FARProperties.CAN_GROW, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FARProperties.CAN_GROW);
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        boolean canGrow = state.getValue(FARProperties.CAN_GROW);
        if(!canGrow)
            return;
        var downState = level.getBlockState(pos.below());
        if(downState.getBlock() != BlockLoader.ROTTEN_STIPE.get())
            return;

        if(downState.getValue(FARProperties.AGE) < 2)
            return;

        var centerState = BlockLoader.ROTTEN_MUSHROOM_CAP_CENTER.get().defaultBlockState();
        var northState = BlockLoader.ROTTEN_MUSHROOM_CAP_SIDE.get().defaultBlockState();
        var northWestState = BlockLoader.ROTTEN_MUSHROOM_CAP_CORNER.get().defaultBlockState();

        var facing = HorizontalDirectionalBlock.FACING;

        level.setBlock(pos, centerState, 3);
        if(level.getBlockState(pos.offset(0, 0, -1)).isAir())
            level.setBlock(pos.offset(0, 0, -1), northState, 3);
        if(level.getBlockState(pos.offset(-1, 0, -1)).isAir())
        level.setBlock(pos.offset(-1, 0, -1), northWestState, 3);
        if(level.getBlockState(pos.offset(-1, 0, 0)).isAir())
        level.setBlock(pos.offset(-1, 0, 0), northState.setValue(facing, Direction.WEST), 3);
        if(level.getBlockState(pos.offset(-1, 0, 1)).isAir())
        level.setBlock(pos.offset(-1, 0, 1), northWestState.setValue(facing, Direction.WEST), 3);
        if(level.getBlockState(pos.offset(0, 0, 1)).isAir())
        level.setBlock(pos.offset(0, 0, 1), northState.setValue(facing, Direction.SOUTH), 3);
        if(level.getBlockState(pos.offset(1, 0, 1)).isAir())
        level.setBlock(pos.offset(1, 0, 1), northWestState.setValue(facing, Direction.SOUTH), 3);
        if(level.getBlockState(pos.offset(1, 0, 0)).isAir())
        level.setBlock(pos.offset(1, 0, 0), northState.setValue(facing, Direction.EAST), 3);
        if(level.getBlockState(pos.offset(1, 0, -1)).isAir())
        level.setBlock(pos.offset(1, 0, -1), northWestState.setValue(facing, Direction.EAST), 3);

    }
}
