package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.blocks.utils.FARProperties;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;

import java.util.Random;

@SuppressWarnings("deprecation")
public class RottenMushroomCapCenterBlock extends Block
{
    private static final boolean[][] CAN_GROW_BUFFER = new boolean[5][5];

    public RottenMushroomCapCenterBlock()
    {
        super(
                Properties.of(Material.PLANT)
                        .strength(0.5f)
                        .sound(SoundType.FUNGUS)
                        .randomTicks()
             );
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        var downState = level.getBlockState(pos.below());
        if(downState.getBlock() != BlockLoader.ROTTEN_STIPE.get())
            return;

        if(downState.getValue(FARProperties.AGE) < 4)
            return;

        for(int i = 0; i < 5; i++)
            for(int k = 0; k < 5; k++)
                CAN_GROW_BUFFER[i][k] = false;

        for(int x = -1; x <= 1; x++)
        {
            for(int z = -1; z <= 1; z++)
            {
                if(level.getBlockState(pos.offset(x, 0, z)).getBlock() instanceof RottenMushroomCapBlock)
                {
                    CAN_GROW_BUFFER[x + 2][z + 2] = true;
                    for (int i = -1; i <= 1; i++)
                        for (int k = -1; k <= 1; k++)
                            if(level.getBlockState(pos.offset(x + i, 0, z + k)).isAir())
                                CAN_GROW_BUFFER[x + i + 2][z + k + 2] = true;
                }
            }
        }

        for(int i = 0; i < 5; i++)
        {
            for (int k = 0; k < 5; k++)
            {
                if((i == 0 || i == 4) && (k == 0 || k == 4))
                    continue;

                if(CAN_GROW_BUFFER[i][k])
                {
                    var newPos = pos.offset(i - 2, 0, k - 2);
                    var newState = BlockLoader.WITHERED_ROTTEN_MUSHROOM_CAP.get().defaultBlockState();
                    level.setBlock(newPos, newState, 3);
                }
            }
        }

        level.setBlock(pos, BlockLoader.WITHERED_ROTTEN_MUSHROOM_CAP.get().defaultBlockState(), 3);
    }
}
