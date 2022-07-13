package xyz.chlamydomonos.f_a_r.blocks.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.blocks.RottenMycelialSoilBlock;
import xyz.chlamydomonos.f_a_r.blocks.RottenMycelialSoilWithoutTEBlock;
import xyz.chlamydomonos.f_a_r.blocks.RottenResidueBlock;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;
import xyz.chlamydomonos.f_a_r.loaders.ConfigLoader;
import xyz.chlamydomonos.f_a_r.tags.FARBlockTags;
import xyz.chlamydomonos.f_a_r.tileentities.RottenMycelialSoilTileEntity;

import java.util.Random;

public class RottenMycelialSoilUtil
{
    private static final boolean[][][] isAirBuffer = new boolean[5][5][5];

    public static boolean canRot(BlockState state)
    {
        if(state.is(Blocks.BEDROCK))
            return ConfigLoader.ROTTEN_BEDROCK.get();

        if(state.is(FARBlockTags.ROTTEN_PHASE_1)) return false;
        if(state.is(FARBlockTags.ROTTEN_PHASE_2)) return false;
        if(state.is(FARBlockTags.ROTTEN_PHASE_3)) return false;
        if (state.getMaterial().getColor() == MaterialColor.DIRT) return true;
        if (state.getMaterial().getColor() == MaterialColor.GRASS) return true;
        if (state.getMaterial().getColor() == MaterialColor.STONE) return true;
        if (state.getMaterial().getColor() == MaterialColor.SAND) return true;
        return false;
    }

    public static boolean inAir(int x, int y, int z)
    {
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                for (int k = -1; k <= 1; k++)
                    if (isAirBuffer[x + i + 2][y + j + 2][z + k + 2])
                        return true;
        return false;
    }

    public static void rot(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        //noinspection deprecation
        if (!level.isAreaLoaded(pos, 5))
            return;

        int phase = state.getValue(FARProperties.PHASE);

        boolean canUpgrade = true;

        for (int i = -2; i <= 2; i++)
            for (int j = -2; j <= 2; j++)
                for (int k = -2; k <= 2; k++)
                    isAirBuffer[i + 2][j + 2][k + 2] = level.getBlockState(pos.offset(i, j, k)).isAir();

        if (ConfigLoader.ROT_EXPAND.get())
        {
            for (int i = -1; i <= 1; i++)
            {
                for (int j = -1; j <= 1; j++)
                {
                    for (int k = -1; k <= 1; k++)
                    {
                        var newPos = pos.offset(i, j, k);
                        var oldState = level.getBlockState(newPos);

                        boolean posInAir = inAir(i, j, k);

                        if (oldState.getBlock() instanceof RottenMycelialSoilBlock || oldState.getBlock() instanceof RottenMycelialSoilWithoutTEBlock)
                        {
                            int otherPhase = oldState.getValue(FARProperties.PHASE);
                            if (phase > otherPhase && posInAir)
                                canUpgrade = false;
                        }

                        if (posInAir && canRot(oldState))
                        {
                            if (random.nextInt(3) == 0)
                            {
                                var block = level.getBlockState(newPos).getBlock();
                                var newState = BlockLoader.ROTTEN_MYCELIAL_SOIL.get().defaultBlockState();
                                level.setBlock(pos.offset(i, j, k), newState, 0);
                                var te = level.getBlockEntity(newPos);

                                if (!(te instanceof RottenMycelialSoilTileEntity))
                                    continue;

                                ((RottenMycelialSoilTileEntity) te).setPreviousBlock(block);

                                level.sendBlockUpdated(newPos, oldState, newState, 3);
                            }
                        }
                    }
                }
            }
        }

        if (canUpgrade)
        {
            if (phase == 17)
                level.setBlock(pos, BlockLoader.ROTTEN_MYCELIAL_SOIL_WITHOUT_TE.get().defaultBlockState(), 3);
            else if (phase < 17)
            {
                var te = level.getBlockEntity(pos);
                if (!(te instanceof RottenMycelialSoilTileEntity))
                    return;
                var block = ((RottenMycelialSoilTileEntity) te).getPreviousBlock();

                var newState = state.cycle(FARProperties.PHASE);

                level.setBlock(pos, newState, 0);

                te = level.getBlockEntity(pos);

                if (!(te instanceof RottenMycelialSoilTileEntity))
                    return;

                ((RottenMycelialSoilTileEntity) te).setPreviousBlock(block);

                level.sendBlockUpdated(pos, state, newState, 3);
            }
            else if (phase < 31)
                level.setBlock(pos, state.cycle(FARProperties.PHASE), 3);
        }
    }

    public static void genStipe(@NotNull BlockState ignoredState, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        if (random.nextInt(10) != 0)
            return;

        for (int i = -2; i <= 2; i++)
        {
            for (int j = 0; j <= 2; j++)
            {
                for (int k = -2; k <= 2; k++)
                {
                    if (level.getBlockState(pos.offset(i, j, k)).is(BlockLoader.ROTTEN_STIPE.get()))
                        return;
                }
            }
        }

        level.setBlock(
                pos.above(),
                BlockLoader.ROTTEN_STIPE.get().defaultBlockState().setValue(PipeBlock.DOWN, true),
                3
                      );
    }

    public static void becomeResidue(@NotNull BlockState ignoredState, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        var upState = level.getBlockState(pos.above());

        boolean canBecomeResidue = false;

        if(upState.is(BlockLoader.ROTTEN_STIPE.get()))
            canBecomeResidue = upState.getValue(FARProperties.AGE) == 4;
        if(upState.is(BlockLoader.ROTTEN_RESIDUE.get()))
            canBecomeResidue = true;

        if(canBecomeResidue && random.nextBoolean())
            level.setBlock(pos, BlockLoader.ROTTEN_RESIDUE.get().defaultBlockState(), 3);
    }
}
