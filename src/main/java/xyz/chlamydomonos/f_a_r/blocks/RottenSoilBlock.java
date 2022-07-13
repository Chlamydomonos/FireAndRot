package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@SuppressWarnings("deprecation")
public class RottenSoilBlock extends Block
{
    public RottenSoilBlock()
    {
        super(
                Properties.of(Material.DIRT)
                        .strength(0.5f)
                        .sound(SoundType.GRAVEL)
                        .randomTicks()
             );
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        var upState = level.getBlockState(pos.above());
        if(upState.getBlock() instanceof BonemealableBlock)
        {
            int bonemealTimes = random.nextInt(4) + 1;
            for (int i = 0; i < bonemealTimes; i++)
            {
                ((BonemealableBlock) upState.getBlock()).performBonemeal(level, random, pos.above(), upState);
                upState = level.getBlockState(pos.above());
                if(!(upState.getBlock() instanceof BonemealableBlock))
                    break;
            }
        }

        if(upState.getBlock() instanceof BonemealableBlock || upState.isAir())
        {
            if(random.nextInt(20) == 0)
            {
                level.setBlock(
                        pos.above(),
                        RottenPlantBlock.ROTTEN_PLANT_BLOCKS
                                .get(
                                        random.nextInt(RottenPlantBlock.ROTTEN_PLANT_BLOCKS.size())
                                    )
                                .defaultBlockState(),
                        3
                              );
            }
        }
    }

    @Override
    public boolean canSustainPlant(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Direction facing, @NotNull IPlantable plantable)
    {
        return true;
    }
}
