package xyz.chlamydomonos.f_a_r.blocks.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;

import java.util.*;

@SuppressWarnings("deprecation")
public class RottenStipeUtil
{
    private static final boolean[][][] isAirBuffer = new boolean[5][5][5];
    private static final boolean[][][] isThisBuffer = new boolean[5][5][5];

    public static boolean canGrowTo(int xOffset, int zOffset)
    {
        if (xOffset == 0 && zOffset == 0)
        {
            for (int i = 1; i <= 3; i++)
            {
                for (int j = 3; j <= 4; j++)
                {
                    for (int k = 1; k <= 3; k++)
                    {
                        if (isThisBuffer[i][j][k])
                            return false;
                    }
                }
            }
            return isAirBuffer[2][3][2] && isAirBuffer[2][4][2];
        }

        return isAirBuffer[xOffset + 2][2][zOffset + 2]
               && isAirBuffer[xOffset + 2][3][zOffset + 2]
               && !isThisBuffer[xOffset * 2 + 2][2][zOffset * 2 + 2]
               && !isThisBuffer[xOffset * 2 + 2][3][zOffset * 2 + 2]
               && !isThisBuffer[xOffset + 2][1][zOffset + 2];
    }

    public static boolean canGenerateCap()
    {
        for(int i = 1; i <= 3; i++)
            for (int k = 1; k <= 3; k++)
                if (!isAirBuffer[i][3][k])
                    return false;
        return true;
    }

    public static BlockState tryIncreaseHeight(BlockState origin, Random random)
    {
        if(origin.getValue(FARProperties.HEIGHT) == 0)
            return origin.cycle(FARProperties.HEIGHT);
        return random.nextInt(10) == 0 ? origin.cycle(FARProperties.HEIGHT) : origin;
    }

    public static boolean growUp(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        for (int i = -2; i <= 2; i++)
        {
            for (int j = -2; j <= 2; j++)
            {
                for (int k = -2; k <= 2; k++)
                {
                    var tempState = level.getBlockState(pos.offset(i, j, k));
                    isAirBuffer[i + 2][j + 2][k + 2] = tempState.isAir();
                    isThisBuffer[i + 2][j + 2][k + 2] = tempState.is(state.getBlock());
                }
            }
        }

        boolean addHeight = random.nextInt(5) == 0;

        Set<Vec3i> availableDirections = new HashSet<>();

        if (canGrowTo(-1, 0))
            availableDirections.add(new Vec3i(-1, 0, 0));
        if (canGrowTo(1, 0))
            availableDirections.add(new Vec3i(1, 0, 0));
        if (canGrowTo(0, -1))
            availableDirections.add(new Vec3i(0, 0, -1));
        if (canGrowTo(0, 1))
            availableDirections.add(new Vec3i(0, 0, 1));

        boolean canSplitX = availableDirections.contains(new Vec3i(-1, 0, 0))
                && availableDirections.contains(new Vec3i(1, 0, 0));

        boolean canSplitZ = availableDirections.contains(new Vec3i(0, 0, -1))
                            && availableDirections.contains(new Vec3i(0, 0, 1));

        boolean canGrowUp = canGrowTo(0, 0);

        if (!level.getBlockState(pos.below()).is(state.getBlock()))
        {
            availableDirections.clear();
            canSplitX = false;
            canSplitZ = false;
        }

        boolean canGrowSide = !availableDirections.isEmpty();

        BlockState newState = state
                .getBlock()
                .defaultBlockState()
                .setValue(FARProperties.HEIGHT, state.getValue(FARProperties.HEIGHT));

        if (canSplitX || canSplitZ)
        {
            boolean temp = random.nextInt(4) == 0;
            if (temp)
            {
                if (canSplitX && canSplitZ)
                {
                    boolean temp2 = random.nextBoolean();
                    if (temp2)
                        canSplitX = false;
                }

                if (canSplitX)
                {
                    level.setBlock(pos.offset(-1, 0, 0), tryIncreaseHeight(newState, random), 3);
                    level.setBlock(pos.offset(1, 0, 0), tryIncreaseHeight(newState, random), 3);
                    return true;
                }

                level.setBlock(pos.offset(0, 0, -1), tryIncreaseHeight(newState, random), 3);
                level.setBlock(pos.offset(0, 0, 1), tryIncreaseHeight(newState, random), 3);
                return true;
            }
        }

        if (random.nextInt(10) == 0)
            return false;

        if (canGrowUp && (random.nextBoolean() || !canGrowSide))
        {
            level.setBlock(pos.above(), tryIncreaseHeight(newState, random), 3);
            return true;
        }

        if (canGrowSide)
        {
            var dir = availableDirections.toArray()[random.nextInt(availableDirections.size())];
            level.setBlock(pos.offset((Vec3i) dir), tryIncreaseHeight(newState, random), 3);
        }

        return false;
    }

    public static boolean canGrowOld(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        int age = state.getValue(FARProperties.AGE);
        var stateBelow = level.getBlockState(pos.below());
        var stateNorth = level.getBlockState(pos.north());
        var stateEast = level.getBlockState(pos.east());
        var stateSouth = level.getBlockState(pos.south());
        var stateWest = level.getBlockState(pos.west());

        if(stateBelow.is(state.getBlock()) && stateBelow.getValue(FARProperties.AGE) > age)
            return true;
        if(stateNorth.is(state.getBlock()) && stateNorth.getValue(FARProperties.AGE) > age)
            return true;
        if(stateEast.is(state.getBlock()) && stateEast.getValue(FARProperties.AGE) > age)
            return true;
        if(stateSouth.is(state.getBlock()) && stateSouth.getValue(FARProperties.AGE) > age)
            return true;
        if(stateWest.is(state.getBlock()) && stateWest.getValue(FARProperties.AGE) > age)
            return true;
        return false;
    }

    public static void grow(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        int height = state.getValue(FARProperties.HEIGHT);
        int age = state.getValue(FARProperties.AGE);
        int maxBuildHeight = level.getMaxBuildHeight() - 4;

        if (age == 0 && height < 4 && pos.getY() < maxBuildHeight)
        {
            boolean growSuccess = growUp(state, level, pos, random);
            var newState = state.cycle(FARProperties.AGE);
            level.setBlock(pos, newState, 3);

            if(growSuccess)
            {
                var block = BlockLoader.ROTTEN_STIPE.get();
                for(var i : Direction.values())
                {
                    newState = block.updateShape(
                            newState,
                            i,
                            level.getBlockState(pos.relative(i)),
                            level,
                            pos,
                            pos.relative(i)
                                                );

                    level.setBlock(pos, newState, 3);
                }
            }
            return;
        }

        if (height == 0 && age > 0 && age < 4 && random.nextInt(5) == 0)
        {
            level.setBlock(pos, state.cycle(FARProperties.AGE), 3);
            return;
        }

        if (canGrowOld(state, level, pos, random) && random.nextInt(8) == 0)
        {
            level.setBlock(pos, state.cycle(FARProperties.AGE), 3);
        }
    }
}
