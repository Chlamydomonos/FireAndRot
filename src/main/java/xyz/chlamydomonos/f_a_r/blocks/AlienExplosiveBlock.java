package xyz.chlamydomonos.f_a_r.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.tags.FARBlockTags;

import java.util.Random;

@SuppressWarnings("deprecation")
public class AlienExplosiveBlock extends Block
{
    public AlienExplosiveBlock()
    {
        super(
                Properties.of(Material.EXPLOSIVE)
                        .randomTicks()
                        .strength(-1f)
                        .noOcclusion()
             );
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean isMoving)
    {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide)
            level.scheduleTick(pos, state.getBlock(), 5);
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion)
    {
        super.onBlockExploded(state, level, pos, explosion);
        if (level.isClientSide)
            for (int i = 0; i < 1000; i++)
                level.addParticle(
                        ParticleTypes.FLAME,
                        pos.getX() + Math.random() * 8 - 4,
                        pos.getY() + Math.random() * 8 - 4,
                        pos.getZ() + Math.random() * 8 - 4,
                        0, 0, 0
                                 );
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random)
    {
        if (!level.isAreaLoaded(pos, 10))
            return;
        for (int i = -7; i <= 7; i++)
        {
            for (int j = -7; j <= 7; j++)
            {
                for (int k = -7; k <= 7; k++)
                {
                    var stateToTransfer = level.getBlockState(pos.offset(i, j, k));
                    if (stateToTransfer.getTags().anyMatch(tag -> tag == FARBlockTags.ROTTEN_PHASE_1))
                        level.setBlock(pos.offset(i, j, k), defaultBlockState(), 3);
                }
            }
        }

        level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 4f, Explosion.BlockInteraction.DESTROY);
    }
}
