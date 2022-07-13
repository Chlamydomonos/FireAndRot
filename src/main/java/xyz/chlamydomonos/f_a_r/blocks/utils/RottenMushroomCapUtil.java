package xyz.chlamydomonos.f_a_r.blocks.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import xyz.chlamydomonos.f_a_r.blocks.RottenMushroomCapBlock;
import xyz.chlamydomonos.f_a_r.blocks.WitheredRottenMushroomCapBlock;
import xyz.chlamydomonos.f_a_r.loaders.BlockLoader;

import java.util.Random;

public class RottenMushroomCapUtil
{
    public static final BlockParticleOption SPORE = new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.WHITE_CONCRETE.defaultBlockState());

    public static boolean canGenSpore(@NotNull BlockState state)
    {
        if(state.getBlock() instanceof RottenMushroomCapBlock)
            return true;
        if(state.getBlock() instanceof WitheredRottenMushroomCapBlock)
        {
            int age = state.getValue(FARProperties.AGE);
            return age <= 3;
        }

        return false;
    }

    public static void genSporeParticles(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Random random)
    {
        if(!canGenSpore(state))
            return;

        int particleAmount = random.nextInt(5);

        for(int i = 0; i < particleAmount; i++)
        {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() - 0.05;
            double z = pos.getZ() + random.nextDouble();

            level.addParticle(SPORE, x, y, z, 0, 0, 0);
        }
    }
}
