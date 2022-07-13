package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;

import java.util.Random;

@SuppressWarnings("deprecation")
public class RottenResidueBlock extends SandBlock
{
    public RottenResidueBlock()
    {
        super(
                0x303080,
                Properties.of(Material.DIRT)
                        .strength(0.5f)
                        .sound(SoundType.FUNGUS)
                        .randomTicks()
             );
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        if (level.isClientSide)
            return;
        if (!level.getBlockState(pos.above()).isAir())
            return;

        if(random.nextInt(5) == 0)
        {
            boolean genLichens = random.nextInt(10) < 7;
            if(genLichens)
                level.setBlock(pos, BlockLoader.ROTTEN_LICHENS.get().defaultBlockState(), 3);
            else
                level.setBlock(pos.above(), BlockLoader.ROTTEN_MILDEW.get().defaultBlockState(), 3);
        }
    }
}
